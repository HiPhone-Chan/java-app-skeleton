package tech.hiphone.rbac.web.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tech.hiphone.framework.web.util.ResponseUtil;
import tech.hiphone.rbac.domain.Role;
import tech.hiphone.rbac.domain.Staff;
import tech.hiphone.rbac.domain.StaffRoleRelation;
import tech.hiphone.rbac.domain.id.StaffRoleId;
import tech.hiphone.rbac.repository.RoleRepository;
import tech.hiphone.rbac.repository.StaffRoleRelationRepository;
import tech.hiphone.rbac.service.StaffService;
import tech.hiphone.rbac.web.vm.StaffRoleVM;

@RestController
@RequestMapping("/api/manager")
@Transactional
public class StaffRoleRelationResource {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private StaffRoleRelationRepository staffRoleRelationRepository;

    @Autowired
    private StaffService staffService;

    @PutMapping("/staff-role")
    public void saveStaffRole(@Valid @RequestBody StaffRoleVM userRoleVM) {
        Staff staff = staffService.getCurrentStaffByLogin(userRoleVM.getLogin()).orElseThrow();

        List<StaffRoleRelation> list = new ArrayList<>();
        for (String roleId : userRoleVM.getRoleIds()) {
            roleRepository.findById(roleId).ifPresent(role -> {
                StaffRoleRelation staffRole = new StaffRoleRelation();
                staffRole.setId(new StaffRoleId(staff, role));
                list.add(staffRole);
            });
        }
        staffRoleRelationRepository.deleteByIdStaff(staff);
        staffRoleRelationRepository.saveAll(list);
    }

    @GetMapping("/staff-roles")
    public ResponseEntity<List<Role>> getStaffRoles(Pageable pageable, String login) {
        Staff staff = staffService.getCurrentStaffByLogin(login).orElseThrow();

        Page<Role> page = staffRoleRelationRepository.findByIdStaff(pageable, staff).map(staffRoleRelation -> {
            return staffRoleRelation.getId().getRole();
        });
        return ResponseUtil.wrapPage(page);
    }

}
