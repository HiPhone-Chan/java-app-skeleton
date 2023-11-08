package tech.hiphone.commons.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tech.hiphone.commons.config.ApplicationProperties;
import tech.hiphone.commons.constants.AuthoritiesConstants;
import tech.hiphone.commons.constants.ErrorCodeContants;
import tech.hiphone.commons.domain.Authority;
import tech.hiphone.commons.domain.User;
import tech.hiphone.commons.exceptioin.ServiceException;
import tech.hiphone.commons.repository.AuthorityRepository;
import tech.hiphone.commons.repository.UserRepository;
import tech.hiphone.commons.security.SecurityUtils;
import tech.hiphone.commons.service.dto.AdminUserDTO;
import tech.hiphone.commons.service.dto.UserDTO;
import tech.hiphone.commons.service.helper.AbstractUserService;
import tech.hiphone.commons.service.impl.NoOpCodeHandler;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService extends AbstractUserService<User, UserRepository> {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;

    private final CacheManager cacheManager;

    public UserService(ApplicationProperties applicationProperties, UserRepository userRepository,
            AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder, CacheManager cacheManager) {
        super(applicationProperties.getAccount(), passwordEncoder, userRepository, NoOpCodeHandler.getInstance());
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
        this.cacheManager = cacheManager;
    }

    public User createUser(AdminUserDTO userDTO) {
        User user = super.createUser(userDTO);
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setMobile(userDTO.getMobile());

        if (userDTO.getAuthorities() != null) {
            Set<Authority> authorities = userDTO.getAuthorities().stream()
                    .filter(authority -> !AuthoritiesConstants.ADMIN.equals(authority))
                    .map(authorityRepository::findById).filter(Optional::isPresent).map(Optional::get)
                    .collect(Collectors.toSet());
            user.setAuthorities(authorities);
        }
        userRepository.save(user);
        this.clearUserCaches(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    public Optional<AdminUserDTO> updateUser(AdminUserDTO userDTO) {
        return Optional.of(userRepository.findById(userDTO.getId())).filter(Optional::isPresent).map(Optional::get)
                .map(user -> {
                    this.clearUserCaches(user);
                    user.setLogin(userDTO.getLogin().toLowerCase());
                    user.setNickName(userDTO.getNickName());
                    user.setFirstName(userDTO.getFirstName());
                    user.setLastName(userDTO.getLastName());
                    user.setEmail(userDTO.getEmail());
                    user.setMobile(userDTO.getMobile());
                    user.setImageUrl(userDTO.getImageUrl());
                    user.setActivated(userDTO.isActivated());
                    user.setLangKey(userDTO.getLangKey());
                    Set<Authority> managedAuthorities = user.getAuthorities();
                    managedAuthorities.clear();
                    userDTO.getAuthorities().stream().map(authorityRepository::findById).filter(Optional::isPresent)
                            .map(Optional::get).forEach(managedAuthorities::add);
                    this.clearUserCaches(user);
                    log.debug("Changed Information for User: {}", user);
                    return user;
                }).map(AdminUserDTO::new);
    }

    public void updateUser(String firstName, String lastName, String nickName, String mobile, String imageUrl) {
        SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin).ifPresent(user -> {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setNickName(nickName);
            user.setMobile(mobile);
            user.setImageUrl(imageUrl);

            this.clearUserCaches(user);
            log.debug("Changed Information for User: {}", user);
        });
    }

    public void changePasswordBySuperior(User subordinate, String superiorClearTextPassword, String newPassword) {
        SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin).ifPresent(superior -> {
            String currentEncryptedPassword = superior.getPassword();
            if (!passwordEncoder.matches(superiorClearTextPassword, currentEncryptedPassword)) {
                throw new ServiceException(ErrorCodeContants.BAD_PARAMETERS, "Old password not matched");
            }

            String encryptedPassword = passwordEncoder.encode(newPassword);
            subordinate.setHasPassword(true);
            subordinate.setPassword(encryptedPassword);
            userRepository.save(subordinate);
            this.clearUserCaches(subordinate);
            log.debug("Changed password for User: {}", subordinate.getLogin());
        });
    }

    @Transactional(readOnly = true)
    public Page<AdminUserDTO> getAllManagedUsers(Specification<User> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable).map(AdminUserDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllPublicUsers(Pageable pageable) {
        return userRepository.findAllByIdNotNullAndActivatedIsTrue(pageable).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
    }

    @Transactional(readOnly = true)
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }

    protected void clearUserCaches(User user) {
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USER_BY_LOGIN_CACHE)).evict(user.getLogin());
    }
}
