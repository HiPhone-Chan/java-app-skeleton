package tech.hiphone.rbac.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import tech.hiphone.commons.constants.AuthoritiesConstants;
import tech.hiphone.rbac.constants.RbacConstants;
import tech.hiphone.rbac.security.StaffVoter;
import tech.hiphone.rbac.service.RoleService;

@Configuration
@ComponentScan(RbacConstants.BASE_PACKAGE)
@EnableJpaRepositories(basePackages = RbacConstants.BASE_PACKAGE + ".repository")
@EntityScan({ RbacConstants.BASE_PACKAGE })
public class RbacConfiguration {

    private final RoleService roleService;

    public RbacConfiguration(RoleService roleService) {
        super();
        this.roleService = roleService;
    }

    @Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters = Arrays.asList(new WebExpressionVoter(),
                getRoleHierarchyVoter(), new StaffVoter(roleService));
        return new UnanimousBased(decisionVoters);
    }

    private RoleVoter getRoleHierarchyVoter() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String higher = " > ";
        StringBuilder sb = new StringBuilder(AuthoritiesConstants.ADMIN).append(higher)
                .append(AuthoritiesConstants.MANAGER).append(higher).append(AuthoritiesConstants.USER).append(higher)
                .append(AuthoritiesConstants.ANONYMOUS).append('\n');

        roleHierarchy.setHierarchy(sb.toString());
        return new RoleHierarchyVoter(roleHierarchy);
    }
}
