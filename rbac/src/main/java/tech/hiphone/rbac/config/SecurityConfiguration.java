package tech.hiphone.rbac.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import tech.hiphone.commons.constants.AuthoritiesConstants;
import tech.hiphone.rbac.constants.RbacConstants;
import tech.hiphone.rbac.security.StaffVoter;
import tech.hiphone.rbac.service.RoleService;

@Configuration("rbacSecurityConfiguration")
public class SecurityConfiguration extends GlobalMethodSecurityConfiguration {

    private final RoleService roleService;

    public SecurityConfiguration(RoleService roleService) {
        super();
        this.roleService = roleService;
    }

    @Bean("rbacFilterChain")
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http.authorizeRequests()
            .antMatchers(RbacConstants.API_PREFIX + "/**").hasAuthority(AuthoritiesConstants.MANAGER)
            ;
        return http.build();
        // @formatter:on
    }

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
        handler.setRoleHierarchy(getRoleHierarchy());
        return handler;
    }

    @Override
    protected AccessDecisionManager accessDecisionManager() {
        AccessDecisionManager accessDecisionManager = super.accessDecisionManager();
        if (accessDecisionManager instanceof AbstractAccessDecisionManager) {
            AbstractAccessDecisionManager abstractAccessDecisionManager = (AbstractAccessDecisionManager) accessDecisionManager;
            List<AccessDecisionVoter<?>> voters = abstractAccessDecisionManager.getDecisionVoters();

            for (AccessDecisionVoter<?> voter : voters) {
                if (voter instanceof RoleVoter) {
                    voters.remove(voter);
                }
            }

            voters.add(new RoleHierarchyVoter(getRoleHierarchy()));
            voters.add(new StaffVoter(roleService));

        }
        return accessDecisionManager;
    }

    private RoleHierarchy getRoleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String higher = " > ";
        StringBuilder sb = new StringBuilder(AuthoritiesConstants.ADMIN).append(higher)
                .append(AuthoritiesConstants.MANAGER).append(higher).append(AuthoritiesConstants.USER).append(higher)
                .append(AuthoritiesConstants.ANONYMOUS).append('\n');

        roleHierarchy.setHierarchy(sb.toString());
        return roleHierarchy;
    }

}
