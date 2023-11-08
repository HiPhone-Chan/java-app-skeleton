package tech.hiphone.commons.security;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import tech.hiphone.commons.domain.Authority;
import tech.hiphone.commons.domain.User;
import tech.hiphone.commons.repository.UserRepository;
import tech.hiphone.commons.security.helper.AbstractUserDetailsService;

@Component("userDetailsService")
public class DomainUserDetailsService extends AbstractUserDetailsService<User> implements UserDetailsService {

    private final UserRepository userRepository;

    public DomainUserDetailsService(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    @Override
    protected Optional<User> findOneByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }

    @Override
    protected Set<String> getAuthorities(User user) {
        return user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet());
    }

}
