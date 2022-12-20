package tech.hiphone.rbac.web.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tech.hiphone.framework.web.util.ResponseUtil;
import tech.hiphone.rbac.domain.NavigationRoleRelation;
import tech.hiphone.rbac.domain.Role;
import tech.hiphone.rbac.domain.id.NavigationRoleId;
import tech.hiphone.rbac.repository.NavigationRepository;
import tech.hiphone.rbac.repository.NavigationRoleRelationRepository;
import tech.hiphone.rbac.repository.RoleRepository;
import tech.hiphone.rbac.web.vm.NavigationRoleVM;
import tech.hiphone.rbac.web.vm.NavigationVM;

@RestController
@RequestMapping("/api/manager")
@Transactional
public class NavigationRoleResource {

    @Autowired
    private NavigationRoleRelationRepository navigationRoleRelationRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private NavigationRepository navigationRepository;

    @PutMapping("/navigation-role")
    public void updateNavigationRole(@RequestBody NavigationRoleVM navigationRoleVM) {
        roleRepository.findById(navigationRoleVM.getRoleId()).ifPresent(role -> {
            navigationRoleRelationRepository.deleteByIdRole(role);

            List<NavigationRoleRelation> list = new ArrayList<>();
            for (String navId : navigationRoleVM.getNavigationIds()) {
                navigationRepository.findById(navId).ifPresent(navigation -> {
                    NavigationRoleRelation navigationRole = new NavigationRoleRelation();
                    navigationRole.setId(new NavigationRoleId(navigation, role));
                    list.add(navigationRole);
                });
            }
            navigationRoleRelationRepository.saveAll(list);
        });
    }

    @GetMapping("/navigation-role/navigations")
    public ResponseEntity<List<NavigationVM>> getRoleNavigations(Pageable pageable, @RequestParam String roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow();

        Page<NavigationVM> page = navigationRoleRelationRepository.findByIdRole(pageable, role)
                .map(navigationRoleRelation -> {
                    return new NavigationVM(navigationRoleRelation.getId().getNavigation());
                });
        return ResponseUtil.wrapPage(page);
    }

}
