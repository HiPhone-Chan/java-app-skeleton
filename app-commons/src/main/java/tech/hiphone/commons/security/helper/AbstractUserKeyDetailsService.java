package tech.hiphone.commons.security.helper;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import tech.hiphone.commons.constants.ErrorCodeContants;
import tech.hiphone.commons.domain.common.AbstractUser;
import tech.hiphone.commons.exceptioin.ServiceException;
import tech.hiphone.commons.exceptioin.UserNotActivatedException;
import tech.hiphone.commons.repository.AbstractUserRepository;

// 验证码登录
public class AbstractUserKeyDetailsService<U extends AbstractUser> implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(AbstractUserKeyDetailsService.class);

    private final AbstractUserRepository<U> userRepository;

    public AbstractUserKeyDetailsService(AbstractUserRepository<U> userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);

        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        return userRepository.findOneByLogin(lowercaseLogin).map(user -> createSpringSecurityUser(lowercaseLogin, user))
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User " + lowercaseLogin + " was not found in the database"));
    }

    protected Set<String> getAuthorities(U user) {
        return Collections.emptySet();
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin, U user) {
        if (!user.isActivated()) {
            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
        } else if (Optional.ofNullable(user.getValidateExpirationDate()).map(date -> date.isBefore(Instant.now()))
                .orElse(true)) {
            throw new ServiceException(ErrorCodeContants.LACK_OF_DATA, "User " + lowercaseLogin + " has not set key.");
        }
        List<GrantedAuthority> grantedAuthorities = getAuthorities(user).stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getValidateKey(),
                grantedAuthorities);
    }
}
