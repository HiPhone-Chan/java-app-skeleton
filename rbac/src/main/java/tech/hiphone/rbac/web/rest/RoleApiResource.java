package tech.hiphone.rbac.web.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tech.hiphone.rbac.domain.ApiInfo;
import tech.hiphone.rbac.domain.Role;
import tech.hiphone.rbac.domain.RoleApiRelation;
import tech.hiphone.rbac.domain.id.RoleApiId;
import tech.hiphone.rbac.repository.ApiInfoRepository;
import tech.hiphone.rbac.repository.RoleApiRelationRepository;
import tech.hiphone.rbac.repository.RoleRepository;
import tech.hiphone.rbac.web.vm.RoleApiVM;

@RestController
@RequestMapping("/api/manager")
@Transactional
public class RoleApiResource {

    @Autowired
    private RoleApiRelationRepository roleApiRelationRepository;

    @Autowired
    private ApiInfoRepository apiInfoRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PutMapping("/role-api")
    public void saveRoleApi(@RequestBody RoleApiVM roleApiVM) {
        Role role = roleRepository.findById(roleApiVM.getRoleId()).orElseThrow();

        List<RoleApiRelation> list = new ArrayList<>();
        for (String apiId : roleApiVM.getApiIds()) {
            apiInfoRepository.findById(apiId).ifPresent(apiInfo -> {
                RoleApiRelation roleApi = new RoleApiRelation();
                roleApi.setId(new RoleApiId(role, apiInfo));
                list.add(roleApi);
            });
        }
        roleApiRelationRepository.deleteByIdRole(role);
        roleApiRelationRepository.saveAll(list);
    }

    @GetMapping("/role-api/apis")
    public List<ApiInfo> getRoleApis(@RequestParam String roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow();

        List<ApiInfo> page = roleApiRelationRepository.findAllByIdRole(role).stream().map(roleApiRelation -> {
            return roleApiRelation.getId().getApiInfo();
        }).collect(Collectors.toList());
        return page;
    }

}
