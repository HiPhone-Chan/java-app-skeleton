package tech.hiphone.rbac.web.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tech.hiphone.commons.web.vm.ImportDataVM;
import tech.hiphone.framework.security.RandomUtil;
import tech.hiphone.framework.web.util.ResponseUtil;
import tech.hiphone.rbac.domain.Role;
import tech.hiphone.rbac.repository.RoleRepository;

@RestController
@RequestMapping("/api/manager")
public class RoleResource {

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/role")
    @ResponseStatus(HttpStatus.CREATED)
    public void createRole(@RequestBody Role roleVM) {
        Role role = newRole(roleVM);
        roleRepository.save(role);
    }

    @PostMapping("/role/import")
    public void importRole(@RequestBody ImportDataVM<Role> importDataVM) {
        List<Role> roleList = new ArrayList<>();
        for (Role roleVM : importDataVM.getDataList()) {

            Role role = newRole(roleVM);
            roleList.add(role);
        }

        if (importDataVM.isAdded()) {
        } else {
            roleRepository.deleteAll();
        }

        roleRepository.saveAll(roleList);
    }

    @PutMapping("/role")
    public void updateRole(@RequestBody Role roleVM) {
        roleRepository.findById(roleVM.getId()).ifPresent(role -> {
            String code = role.getCode();
            if (StringUtils.isEmpty(code)) {
                code = role.getId();
            }
            role.setCode(code);

            role.setName(roleVM.getName());
            role.setRemark(roleVM.getRemark());
            roleRepository.save(role);
        });
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles(Pageable pageable) {
        Page<Role> page = roleRepository.findAll(pageable);
        return ResponseUtil.wrapPage(page);
    }

    @DeleteMapping("/role")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@RequestParam String id) {
        roleRepository.deleteById(id);
    }

    private Role newRole(Role roleVM) {
        Role role = new Role();
        role.setId(RandomUtil.uuid());

        String code = roleVM.getCode();
        if (StringUtils.isEmpty(code)) {
            code = RandomStringUtils.randomNumeric(12);
        }
        role.setCode(code);
        role.setName(roleVM.getName());
        role.setRemark(roleVM.getRemark());
        return role;
    }

}
