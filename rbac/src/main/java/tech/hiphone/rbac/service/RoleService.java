package tech.hiphone.rbac.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tech.hiphone.commons.constants.AuthoritiesConstants;
import tech.hiphone.commons.repository.UserRepository;
import tech.hiphone.commons.security.SecurityUtils;
import tech.hiphone.rbac.domain.Role;
import tech.hiphone.rbac.repository.ApiInfoRepository;
import tech.hiphone.rbac.repository.RoleApiRelationRepository;
import tech.hiphone.rbac.repository.StaffRepository;
import tech.hiphone.rbac.repository.StaffRoleRelationRepository;

@Service
@Transactional
public class RoleService {

    @Autowired
    private ApiInfoRepository apiInfoRepository;

    @Autowired
    private StaffRoleRelationRepository staffRoleRelationRepository;

    @Autowired
    private RoleApiRelationRepository roleApiRelationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StaffRepository staffRepository;

    public boolean hasCurrentUserThisAuthority(String method, String path) {
        if (!SecurityUtils.hasCurrentUserThisAuthority(AuthoritiesConstants.MANAGER)) {
            return true;
        }

        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin).map(user -> {
            return staffRepository.findOneByUser(user).map(staff -> {
                return apiInfoRepository.findOneByMethodAndPath(method, path).map(apiInfo -> {
                    List<Role> neededList = roleApiRelationRepository.findAllByIdApiInfo(apiInfo).stream().map(roleApiRelation -> {
                        return roleApiRelation.getId().getRole();
                    }).collect(Collectors.toList());
                    List<Role> hasList = staffRoleRelationRepository.findAllByIdStaff(staff).stream().map(roleApiRelation -> {
                        return roleApiRelation.getId().getRole();
                    }).collect(Collectors.toList());

                    for (Role needed : neededList) {
                        if (hasList.contains(needed)) {
                            return true;
                        }
                    }
                    return false;
                }).orElse(false);
            }).orElse(false);

        }).orElse(false);

    }
}
