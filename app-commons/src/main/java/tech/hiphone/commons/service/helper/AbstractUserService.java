package tech.hiphone.commons.service.helper;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import tech.hiphone.commons.config.AccountProperties;
import tech.hiphone.commons.config.AccountProperties.CodeInfo;
import tech.hiphone.commons.constants.CodeKeyType;
import tech.hiphone.commons.constants.CommonsConstants;
import tech.hiphone.commons.constants.ErrorCodeContants;
import tech.hiphone.commons.domain.common.AbstractUser;
import tech.hiphone.commons.exceptioin.ServiceException;
import tech.hiphone.commons.repository.AbstractUserRepository;
import tech.hiphone.commons.security.SecurityUtils;
import tech.hiphone.commons.service.ICodeHandler;
import tech.hiphone.commons.service.dto.AbstractUserDTO;
import tech.hiphone.commons.service.dto.AbstractUserDetailDTO;
import tech.hiphone.commons.utils.GenericTypeUtil;
import tech.hiphone.framework.security.RandomUtil;

@Transactional
public abstract class AbstractUserService<U extends AbstractUser, R extends AbstractUserRepository<U>> {

    private static final Logger log = LoggerFactory.getLogger(AbstractUserService.class);

    private final AccountProperties accountProperties;

    private final PasswordEncoder passwordEncoder;

    private final R userRepository;

    private final ICodeHandler codeHandler;

    public AbstractUserService(AccountProperties accountProperties, PasswordEncoder passwordEncoder, R userRepository,
            ICodeHandler codeHandler) {
        this.accountProperties = accountProperties;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.codeHandler = codeHandler;
    }

    public U registerUser(AbstractUserDTO userDTO, String password) {
        return registerUser(userDTO, password, true);
    }

    /**
     *  注册
     * @param userDTO
     * @param password
     * @param sendCode 是否发送验证码
     * @return
     */

    public U registerUser(AbstractUserDTO userDTO, String password, boolean sendCode) {
        String login = userDTO.getLogin().toLowerCase();
        String principal = userDTO.getPrincipal().toLowerCase();

        userRepository.findOneByLogin(login).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new ServiceException(ErrorCodeContants.LOGIN_ALREADY_USED, "Login name already used!");
            }
        });
        userRepository.findOneByPrincipal(principal).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new ServiceException(ErrorCodeContants.LOGIN_ALREADY_USED, "Principal name already used!");
            }
        });

        U abstractUser = newEmptyUser();

        if (StringUtils.isEmpty(password)) {
            password = RandomUtil.generatePassword();
        } else {
            abstractUser.setHasPassword(true);
        }
        abstractUser.setPassword(passwordEncoder.encode(password));

        CodeInfo codeInfo = accountProperties.getRegister();
        String key = this.sendCode(principal, codeInfo, sendCode);
        abstractUser.setLogin(login);
        abstractUser.setPrincipal(principal);
        abstractUser.setValidateKey(key);
        abstractUser.setValidateExpirationDate(Instant.now().plus(codeInfo.getValidityInSeconds(), ChronoUnit.SECONDS));

        userRepository.save(abstractUser);
        return abstractUser;
    }

    public Optional<U> activateRegistration(String login, String key) {
        log.debug("Activating user {} for activation key {}", login, key);

        Optional<U> optionalUser = Optional.empty();
        if (StringUtils.isEmpty(login)) {
            optionalUser = userRepository.findOneByValidateKey(key);
        } else {
            optionalUser = userRepository.findOneByLogin(login);
        }

        return optionalUser.filter(user -> user.getValidateKey().equals(key))
                .filter(user -> user.getValidateExpirationDate().isAfter(Instant.now())).map(user -> {
                    // activate given user for the registration key.
                    user.setActivated(true);
                    user.setValidateKey(null);
                    user.setValidateExpirationDate(null);
                    log.debug("Activated user: {}", user);
                    return user;
                });
    }

    @Transactional
    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin).ifPresent(user -> {
            String currentEncryptedPassword = user.getPassword();
            if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                throw new ServiceException(ErrorCodeContants.BAD_PARAMETERS, "Old password not matched");
            }
            String encryptedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encryptedPassword);
            log.debug("Changed password for User: {}", user);
        });
    }

    public void requestPasswordReset(String principal) {
        CodeInfo codeInfo = accountProperties.getResetPassword();
        Optional<U> appUser = userRepository.findOneByPrincipal(principal).filter(AbstractUser::isActivated)
                .map(user -> {
                    String key = this.sendCode(principal, codeInfo);
                    user.setResetKey(key);
                    user.setResetExpirationDate(
                            Instant.now().plus(codeInfo.getValidityInSeconds(), ChronoUnit.SECONDS));
                    return user;
                });

        if (!appUser.isPresent()) {
            log.warn("Password reset requested for non existing principal");
        }
    }

    public Optional<U> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);
        return userRepository.findOneByResetKey(key)
                .filter(user -> user.getResetExpirationDate().isAfter(Instant.now())).map(user -> {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    user.setHasPassword(true);
                    user.setResetKey(null);
                    user.setResetExpirationDate(null);
                    return user;
                });
    }

    public Optional<U> requestLogin(String login) {
        return userRepository.findOneByLogin(login).filter(AbstractUser::isActivated).map(user -> {
            CodeInfo codeInfo = accountProperties.getLogin();
            String key = this.sendCode(user.getPrincipal(), codeInfo);
            user.setValidateKey(key);
            user.setValidateExpirationDate(Instant.now().plus(codeInfo.getValidityInSeconds(), ChronoUnit.SECONDS));
            return user;
        });
    }

    public U createUser(AbstractUserDetailDTO userDTO) {
        U user = newEmptyUser();

        String login = userDTO.getLogin().toLowerCase();
        user.setLogin(login);

        String principal = userDTO.getPrincipal();
        if (StringUtils.isEmpty(principal)) {
            user.setPrincipal(login);
        } else {
            user.setPrincipal(principal.toLowerCase());
        }

        user.setNickName(userDTO.getNickName());
        user.setImageUrl(userDTO.getImageUrl());
        if (userDTO.getLangKey() == null) {
            user.setLangKey(CommonsConstants.DEFAULT_LANGUAGE); // default language
        } else {
            user.setLangKey(userDTO.getLangKey());
        }
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);

        CodeInfo codeInfo = accountProperties.getCreate();
        String key = this.sendCode(principal, codeInfo);
        user.setResetKey(key);
        user.setResetExpirationDate(Instant.now().plus(codeInfo.getValidityInSeconds(), ChronoUnit.SECONDS));
        user.setActivated(true);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update.
     * @return updated user.
     */
    public Optional<U> updateUser(AbstractUserDetailDTO userDTO) {
        return userRepository.findById(userDTO.getId()).map(user -> {
            this.clearUserCaches(user);
            user.setLogin(userDTO.getLogin().toLowerCase());
            user.setNickName(userDTO.getNickName());
            if (userDTO.getPrincipal() != null) {
                user.setPrincipal(userDTO.getPrincipal().toLowerCase());
            }
            user.setImageUrl(userDTO.getImageUrl());
            user.setActivated(userDTO.isActivated());
            user.setLangKey(userDTO.getLangKey());
            this.clearUserCaches(user);
            log.debug("Changed Information for User: {}", user);
            return user;
        });
    }

    /**
     * Update basic information (first name, last name, email, language) for the current user.
     */
    public void updateUser(AbstractUserDTO userDTO) {
        SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin).ifPresent(user -> {
            user.setNickName(userDTO.getNickName());
            String principal = userDTO.getPrincipal();
            if (principal != null) {
                principal = principal.toLowerCase();
                if (!codeHandler.check(principal)) {
                    throw new ServiceException(ErrorCodeContants.DATA_FORMAT, "Principal format is wrong.");
                }
                user.setPrincipal(principal.toLowerCase());
            }
            user.setLangKey(userDTO.getLangKey());
            user.setImageUrl(userDTO.getImageUrl());
            this.clearUserCaches(user);
            log.debug("Changed Information for User: {}", user);
        });
    }

    public void deleteUser(String login) {
        userRepository.findOneByLogin(login).ifPresent(user -> {
            userRepository.delete(user);
            this.clearUserCaches(user);
            log.debug("Deleted User: {}", user);
        });
    }

    public Optional<U> getUserByLogin(String login) {
        return userRepository.findOneByLogin(login);
    }

    public Optional<U> getCurrentUser() {
        return SecurityUtils.getCurrentUserLogin().flatMap(this::getUserByLogin);
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        userRepository.findAllByActivatedIsFalseAndCreatedDateBefore(Instant.now().minus(1, ChronoUnit.DAYS))
                .forEach(user -> {
                    log.debug("Deleting not activated user {}", user.getLogin());
                    userRepository.delete(user);
                    this.clearUserCaches(user);
                });
    }

    private String sendCode(String principal, CodeInfo codeInfo) {
        return this.sendCode(principal, codeInfo, true);
    }

    private String sendCode(String principal, CodeInfo codeInfo, boolean send) {
        String key = generateKey(codeInfo.getType());
        if (!send) {
            return key;
        }

        if (!codeHandler.check(principal)) {
            throw new ServiceException(ErrorCodeContants.DATA_FORMAT, "Principal format is wrong.");
        }

        codeHandler.send(principal, key, codeInfo.getParams());
        return key;
    }

    private boolean removeNonActivatedUser(U existingUser) {
        if (existingUser.isActivated()) {
            return false;
        }
        userRepository.delete(existingUser);
        userRepository.flush();
        this.clearUserCaches(existingUser);
        return true;
    }

    abstract protected void clearUserCaches(U user);

    @SuppressWarnings("unchecked")
    protected U newEmptyUser() {
        try {
            U user = ((Class<U>) GenericTypeUtil.getGenericSuperclassType(getClass())).getDeclaredConstructor()
                    .newInstance();
            return user;
        } catch (Exception e) {
            throw new ServiceException(ErrorCodeContants.NOT_IMPLEMENTED, "Cannot new user.");
        }
    }

    public static String generateKey(String type) {
        if (CodeKeyType.NUMBER.equals(type)) {
            return RandomStringUtils.randomNumeric(6);
        }
        return RandomUtil.generateRandomAlphanumericString();
    }
}
