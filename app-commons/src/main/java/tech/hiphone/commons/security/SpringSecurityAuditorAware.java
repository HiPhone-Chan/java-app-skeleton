package tech.hiphone.commons.security;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

import tech.hiphone.commons.constants.CommonsConstants;

/**
 * Implementation of AuditorAware based on Spring Security.
 */
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityUtils.getCurrentUserLogin().orElse(CommonsConstants.SYSTEM));
    }
}
