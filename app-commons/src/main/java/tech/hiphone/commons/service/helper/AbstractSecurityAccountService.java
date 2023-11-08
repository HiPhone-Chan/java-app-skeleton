package tech.hiphone.commons.service.helper;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import tech.hiphone.commons.config.AccountProperties;
import tech.hiphone.commons.config.AccountProperties.CodeInfo;
import tech.hiphone.commons.constants.AccountRelationStatus;
import tech.hiphone.commons.constants.ErrorCodeContants;
import tech.hiphone.commons.domain.common.AbstractAccountRelation;
import tech.hiphone.commons.domain.common.AbstractUser;
import tech.hiphone.commons.domain.id.ModulePrincipalId;
import tech.hiphone.commons.exceptioin.ServiceException;
import tech.hiphone.commons.repository.AbstractAccountRelationRepository;
import tech.hiphone.commons.repository.AbstractUserRepository;
import tech.hiphone.commons.service.ApplicationContextService;
import tech.hiphone.commons.service.ICodeHandler;
import tech.hiphone.commons.service.dto.AbstractUserDTO;
import tech.hiphone.commons.utils.GenericTypeUtil;
import tech.hiphone.framework.security.RandomUtil;
import tech.hiphone.security.authentication.third.I3rdAuthenticationHandler;
import tech.hiphone.security.authentication.third.ThirdAuthenticationToken;

// 账号安全服务
@Transactional
public abstract class AbstractSecurityAccountService<U extends AbstractUser, AR extends AbstractAccountRelation<U>, ARR extends AbstractAccountRelationRepository<U, AR>> {

    private static final Logger log = LoggerFactory.getLogger(AbstractSecurityAccountService.class);

    private final AccountProperties accountProperties;

    private final ARR accountRelationRepository;

    private final AbstractUserService<U, ? extends AbstractUserRepository<U>> userService;

    private final ApplicationContextService applicationContextService;

    public AbstractSecurityAccountService(AccountProperties accountProperties, ARR accountRelationRepository,
            AbstractUserService<U, ? extends AbstractUserRepository<U>> userService,
            ApplicationContextService applicationContextService) {
        this.accountProperties = accountProperties;
        this.accountRelationRepository = accountRelationRepository;
        this.userService = userService;
        this.applicationContextService = applicationContextService;
    }

    // 验证码请求绑定，发送验证码
    public void requestBinding(ModulePrincipalId id) {
        U user = userService.getCurrentUser().orElseThrow();
        if (accountRelationRepository.existsById(id)) {
            throw new ServiceException(ErrorCodeContants.DATA_EXISTS, "This account has bean bound.");
        }

        CodeInfo codeInfo = accountProperties.getBinding();
        String code = this.sendCode(id, codeInfo);

        AR accountRelation = newEmptyRelation();
        accountRelation.setId(id);
        accountRelation.setUser(user);
        accountRelation.setStatus(AccountRelationStatus.UNBOUND);
        accountRelation.setCode(code);
        accountRelation.setCodeExpirationDate(Instant.now().plusSeconds(codeInfo.getValidityInSeconds()));
        accountRelationRepository.save(accountRelation);
    }

    // 第三方绑定，验证第三方code
    public AR requestBinding(ModulePrincipalId thirdInfo, String code) {
        U user = userService.getCurrentUser().orElseThrow();
        String module = thirdInfo.getModule();

        ThirdAuthenticationToken authenticationToken = new ThirdAuthenticationToken(thirdInfo.getPrincipal(), code);
        String principal = get3rdAuthenticationHandler(module).authorize(authenticationToken);

        ModulePrincipalId id = new ModulePrincipalId(module, principal);
        if (accountRelationRepository.existsById(id)) {
            throw new ServiceException(ErrorCodeContants.DATA_EXISTS, "This account has bean bound.");
        }

        AR accountRelation = newEmptyRelation();
        accountRelation.setId(id);
        accountRelation.setUser(user);
        accountRelation.setStatus(AccountRelationStatus.UNBOUND);
        String bindCode = RandomUtil.generateRandomAlphanumericString();
        accountRelation.setCode(bindCode);
        accountRelation.setCodeExpirationDate(Instant.now().plus(60, ChronoUnit.SECONDS));
        accountRelationRepository.save(accountRelation);
        return accountRelation;
    }

    // 验证code后绑定
    public void bind(ModulePrincipalId id, String code) {
        U user = userService.getCurrentUser().orElseThrow();
        AR accountRelation = accountRelationRepository.findById(id)
                .filter(relation -> relation.getStatus() == AccountRelationStatus.UNBOUND)
                .filter(relation -> relation.getCode().equals(code))
                .filter(relation -> relation.getCodeExpirationDate().isAfter(Instant.now())).orElseThrow(
                        () -> new ServiceException(ErrorCodeContants.LACK_OF_DATA, "Cannot find binding account."));

        accountRelation.setStatus(AccountRelationStatus.BOUND);
        accountRelation.setUser(user);
        accountRelation.setCode(null);
        accountRelation.setCodeExpirationDate(null);
        accountRelationRepository.save(accountRelation);
    }

    // 验证码请求解除绑定，发送验证码
    public void requestUnbind(ModulePrincipalId id) {
        U user = userService.getCurrentUser().orElseThrow();

        AR accountRelation = accountRelationRepository.findById(id)
                .filter(relation -> relation.getStatus() == AccountRelationStatus.BOUND)
                .filter(relation -> relation.getUser().equals(user)).orElseThrow(
                        () -> new ServiceException(ErrorCodeContants.LACK_OF_DATA, "Cannot find binding account."));

        CodeInfo codeInfo = accountProperties.getUnbind();
        String code = this.sendCode(id, codeInfo);

        accountRelation.setCode(code);
        accountRelation.setCodeExpirationDate(Instant.now().plusSeconds(codeInfo.getValidityInSeconds()));
        accountRelationRepository.save(accountRelation);
    }

    // 第三方请求解除绑定，验证第三方code
    public AR requestUnbind(ModulePrincipalId thirdInfo, String code) {
        U user = userService.getCurrentUser().orElseThrow();
        String module = thirdInfo.getModule();
        ThirdAuthenticationToken authenticationToken = new ThirdAuthenticationToken(thirdInfo.getPrincipal(), code);
        String principal = get3rdAuthenticationHandler(module).authorize(authenticationToken);

        ModulePrincipalId id = new ModulePrincipalId(module, principal);
        AR accountRelation = accountRelationRepository.findById(id)
                .filter(relation -> relation.getStatus() == AccountRelationStatus.BOUND)
                .filter(relation -> relation.getUser().equals(user)).orElseThrow(
                        () -> new ServiceException(ErrorCodeContants.DATA_EXISTS, "This account has not bean bound."));

        String bindCode = RandomUtil.generateRandomAlphanumericString();
        accountRelation.setCode(bindCode);
        accountRelation.setCodeExpirationDate(Instant.now().plus(60, ChronoUnit.SECONDS));
        accountRelationRepository.save(accountRelation);
        return accountRelation;
    }

    // 验证code后解除绑定
    public void unbind(ModulePrincipalId id, String code) {
        U user = userService.getCurrentUser().orElseThrow();

        AR accountRelation = accountRelationRepository.findById(id).filter(relation -> relation.getCode().equals(code))
                .filter(relation -> relation.getUser().equals(user))
                .filter(relation -> relation.getCodeExpirationDate().isAfter(Instant.now())).orElseThrow(
                        () -> new ServiceException(ErrorCodeContants.LACK_OF_DATA, "Cannot find binding account."));

        accountRelationRepository.delete(accountRelation);
    }

    // 解除绑定
    public void unbind(String module) {
        U user = userService.getCurrentUser().orElseThrow();
        AR accountRelation = accountRelationRepository.findOneByIdModuleAndUser(module, user)
                .orElseThrow(() -> new ServiceException(ErrorCodeContants.LACK_OF_DATA, "Cannot find this account."));
        accountRelationRepository.delete(accountRelation);
    }

    // 验证码登录，发送验证码
    public void requestLogin(ModulePrincipalId id) {
        U user = userService.getCurrentUser().orElseThrow();

        AR accountRelation = accountRelationRepository.findById(id)
                .filter(relation -> relation.getStatus() == AccountRelationStatus.BOUND)
                .filter(relation -> relation.getUser().equals(user)).orElseThrow(
                        () -> new ServiceException(ErrorCodeContants.LACK_OF_DATA, "Cannot find binding account."));

        CodeInfo codeInfo = accountProperties.getLogin();
        String code = this.sendCode(id, codeInfo);
        accountRelation.setCode(code);
        accountRelation.setCodeExpirationDate(Instant.now().plus(codeInfo.getValidityInSeconds(), ChronoUnit.SECONDS));

        accountRelationRepository.save(accountRelation);
    }

    // 第三方登录，验证第三方code
    public AR requestLogin(ModulePrincipalId id, String code) {
        String module = id.getModule();

        ThirdAuthenticationToken authenticationToken = new ThirdAuthenticationToken(id.getPrincipal(), code);
        String principal = get3rdAuthenticationHandler(module).authorize(authenticationToken);

        AR accountRelation = accountRelationRepository.findById(new ModulePrincipalId(module, principal))
                .filter(user -> user.getStatus() == AccountRelationStatus.BOUND)
                .orElseThrow(() -> new ServiceException(ErrorCodeContants.USER_NOT_EXISTS, "User not found."));

        String bindCode = RandomUtil.generateRandomAlphanumericString();
        accountRelation.setCode(bindCode);
        accountRelation.setCodeExpirationDate(Instant.now().plus(60, ChronoUnit.SECONDS));
        accountRelationRepository.save(accountRelation);
        return accountRelation;
    }

    // 第三方登录, 没用户时注册
    public AR requestLoginAndRegisterIfNoUser(AbstractUserDTO userDTO, ModulePrincipalId thirdInfo, String code) {
        String module = thirdInfo.getModule();
        ThirdAuthenticationToken authenticationToken = new ThirdAuthenticationToken(thirdInfo.getPrincipal(), code);
        String principal = get3rdAuthenticationHandler(module).authorize(authenticationToken);

        ModulePrincipalId id = new ModulePrincipalId(module, principal);
        AR accountRelation = accountRelationRepository.findById(id).orElseGet(() -> {
            AR newAccountRelation = newEmptyRelation();
            newAccountRelation.setId(id);
            return newAccountRelation;
        });
        U user = accountRelation.getUser();

        if (user == null) {
            if (StringUtils.isEmpty(userDTO.getLogin())) {
                userDTO.setLogin(RandomUtil.uuid().replace("-", ""));
            }
            if (StringUtils.isEmpty(userDTO.getPrincipal())) {
                userDTO.setPrincipal(userDTO.getLogin());
            }
            user = userService.registerUser(userDTO, null, false);
            userService.activateRegistration(user.getLogin(), user.getValidateKey())
                    .orElseThrow(() -> new ServiceException(ErrorCodeContants.USER_NOT_EXISTS,
                            "No user was found for this activation key"));
            accountRelation.setUser(user);
        }

        accountRelation.setStatus(AccountRelationStatus.BOUND);
        String bindCode = RandomUtil.generateRandomAlphanumericString();
        accountRelation.setCode(bindCode);
        accountRelation.setCodeExpirationDate(Instant.now().plus(60, ChronoUnit.SECONDS));
        accountRelationRepository.save(accountRelation);
        return accountRelation;
    }

    public void clearCode(ModulePrincipalId id) {
        accountRelationRepository.findById(id).ifPresent(relation -> {
            relation.setCode(null);
            relation.setCodeExpirationDate(null);
        });
    }

    @SuppressWarnings("unchecked")
    protected AR newEmptyRelation() {
        try {
            AR relation = ((Class<AR>) GenericTypeUtil.getGenericSuperclassType(getClass(), 1)).getDeclaredConstructor()
                    .newInstance();
            return relation;
        } catch (Exception e) {
            throw new ServiceException(ErrorCodeContants.NOT_IMPLEMENTED, "Cannot new relation.");
        }
    }

    private String sendCode(ModulePrincipalId id, CodeInfo codeInfo) {
        String module = id.getModule();
        String beanName = module + ICodeHandler.HANDLER_SUFFIX;
        if (!applicationContextService.containsBeanDefinition(beanName)) {
            log.error("get code AuthenticationHandler fail: {}", module);
            throw new ServiceException(ErrorCodeContants.LACK_OF_DATA, "Cannot get code AuthenticationHandler.");
        }
        ICodeHandler codeHandler = applicationContextService.getBean(beanName, ICodeHandler.class);

        String code = AbstractUserService.generateKey(codeInfo.getType());
        codeHandler.send(id.getPrincipal(), code, codeInfo.getParams());
        return code;
    }

    private I3rdAuthenticationHandler get3rdAuthenticationHandler(String module) {
        String beanName = module + I3rdAuthenticationHandler.HANDLER_SUFFIX;
        if (!applicationContextService.containsBeanDefinition(beanName)) {
            log.error("get 3rd AuthenticationHandler fail: {}", module);
            throw new ServiceException(ErrorCodeContants.LACK_OF_DATA, "Cannot get 3rd AuthenticationHandler.");
        }
        return applicationContextService.getBean(beanName, I3rdAuthenticationHandler.class);
    }

}
