package tech.hiphone.rbac.web.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tech.hiphone.rbac.domain.ApiInfo;
import tech.hiphone.rbac.domain.Navigation;
import tech.hiphone.rbac.domain.NavigationApiRelation;
import tech.hiphone.rbac.domain.Role;
import tech.hiphone.rbac.domain.RoleApiRelation;
import tech.hiphone.rbac.domain.Staff;
import tech.hiphone.rbac.domain.StaffRoleRelation;
import tech.hiphone.rbac.domain.id.NavigationApiId;
import tech.hiphone.rbac.domain.id.RoleApiId;
import tech.hiphone.rbac.domain.id.StaffRoleId;
import tech.hiphone.rbac.repository.ApiInfoRepository;
import tech.hiphone.rbac.repository.NavigationApiRelationRepository;
import tech.hiphone.rbac.repository.NavigationRepository;
import tech.hiphone.rbac.repository.RoleApiRelationRepository;
import tech.hiphone.rbac.repository.StaffRoleRelationRepository;
import tech.hiphone.rbac.service.StaffService;
import tech.hiphone.rbac.web.vm.NavigationApiVM;

@RestController
@RequestMapping("/api")
@Transactional
public class NavigationApiResource {

    @Autowired
    private NavigationApiRelationRepository navigationApiRelationRepository;

    @Autowired
    private ApiInfoRepository apiInfoRepository;

    @Autowired
    private NavigationRepository navigationRepository;

    @Autowired
    private RoleApiRelationRepository roleApiRelationRepository;

    @Autowired
    private StaffRoleRelationRepository staffRoleRelationRepository;

    @Autowired
    private StaffService staffService;

    @PostMapping("/manager/navigation-api")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNavigationApi(@RequestBody NavigationApiVM navigationApiVM) {
        Navigation navigation = navigationRepository.findById(navigationApiVM.getNavId()).orElseThrow();
        ApiInfo apiInfo = apiInfoRepository.findById(navigationApiVM.getApiId()).orElseThrow();
        NavigationApiRelation navigationApiRelation = new NavigationApiRelation();
        navigationApiRelation.setId(new NavigationApiId(navigation, apiInfo));
        navigationApiRelationRepository.save(navigationApiRelation);
    }

    @GetMapping("/manager/navigation-api/apis")
    public List<ApiInfo> getNavigationApis(@RequestParam String navId) {
        Navigation navigation = navigationRepository.findById(navId).orElseThrow();

        List<ApiInfo> list = navigationApiRelationRepository.findAllByIdNavigation(navigation).stream().map(navigationApiRelation -> {
            return navigationApiRelation.getId().getApiInfo();
        }).collect(Collectors.toList());
        return list;
    }

    // 获取员工菜单中的api
    @GetMapping("/navigation-api/apis")
    public List<ApiInfo> getStaffNavigationApis(@RequestParam String navId) {
        Staff staff = staffService.getCurrentStaff().orElseThrow();
        List<Role> staffRoleList = staffRoleRelationRepository.findAllByIdStaff(staff).stream().map(StaffRoleRelation::getId)
                .map(StaffRoleId::getRole).collect(Collectors.toList());

        Navigation navigation = navigationRepository.findById(navId).orElseThrow();
        List<ApiInfo> apiInfoList = navigationApiRelationRepository.findAllByIdNavigation(navigation).stream()
                .map(navigationApiRelation -> {
                    return navigationApiRelation.getId().getApiInfo();
                }).collect(Collectors.toList());

        List<ApiInfo> result = new ArrayList<>();
        for (ApiInfo apiInfo : apiInfoList) {
            List<Role> roleList = roleApiRelationRepository.findAllByIdApiInfo(apiInfo).stream().map(RoleApiRelation::getId)
                    .map(RoleApiId::getRole).collect(Collectors.toList());
            for (Role staffRole : staffRoleList) {
                if (roleList.contains(staffRole)) {
                    result.add(apiInfo);
                    break;
                }
            }
        }
        return result;
    }

    @DeleteMapping("/manager/navigation-api")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNavigationApi(@RequestParam String navId, @RequestParam String apiId) {
        Navigation navigation = navigationRepository.findById(navId).orElseThrow();
        ApiInfo apiInfo = apiInfoRepository.findById(apiId).orElseThrow();
        NavigationApiRelation navigationApiRelation = new NavigationApiRelation();
        navigationApiRelation.setId(new NavigationApiId(navigation, apiInfo));
        navigationApiRelationRepository.delete(navigationApiRelation);
    }

}
