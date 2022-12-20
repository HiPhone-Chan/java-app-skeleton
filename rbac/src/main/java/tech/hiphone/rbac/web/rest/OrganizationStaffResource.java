package tech.hiphone.rbac.web.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tech.hiphone.framework.web.util.ResponseUtil;
import tech.hiphone.rbac.domain.Organization;
import tech.hiphone.rbac.domain.OrganizationStaffRelation;
import tech.hiphone.rbac.domain.Staff;
import tech.hiphone.rbac.domain.id.OrganizationStaffId;
import tech.hiphone.rbac.repository.OrganizationRepository;
import tech.hiphone.rbac.repository.OrganizationStaffRelationRepository;
import tech.hiphone.rbac.service.OrganizationService;
import tech.hiphone.rbac.service.StaffService;
import tech.hiphone.rbac.service.dto.AdminStaffDTO;
import tech.hiphone.rbac.web.vm.OrganizationStaffRelationVM;

@RestController
@RequestMapping("/api")
public class OrganizationStaffResource {

    @Autowired
    private OrganizationStaffRelationRepository organizationStaffRelationRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private StaffService staffService;

    @PostMapping("/manager/organization-staff")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrganizationStaff(@Valid @RequestBody OrganizationStaffRelationVM organizationStaffRelationVM) {
        Organization org = organizationRepository.findById(organizationStaffRelationVM.getOrganizationId()).orElseThrow();

        staffService.getCurrentStaffByLogin(organizationStaffRelationVM.getLogin()).ifPresent(staff -> {
            OrganizationStaffRelation organizationStaffRelation = new OrganizationStaffRelation();
            organizationStaffRelation.setId(new OrganizationStaffId(org, staff));
            organizationStaffRelationRepository.save(organizationStaffRelation);
        });
    }

    @DeleteMapping("/manager/organization-staff")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganizationStaff(@RequestParam(name = "organizationId") String organizationId,
            @RequestParam(name = "login") String login) {
        Organization org = organizationRepository.findById(organizationId).orElseThrow();
        Staff staff = staffService.getCurrentStaffByLogin(login).orElseThrow();

        organizationStaffRelationRepository.deleteById(new OrganizationStaffId(org, staff));
    }

    @GetMapping("/manager/organization-staffs")
    public ResponseEntity<List<AdminStaffDTO>> getOrganizationStaffs(Pageable pageable,
            @RequestParam(name = "organizationId") String organizationId) {
        Organization organization = organizationRepository.findById(organizationId).orElseThrow();
        return ResponseUtil.wrapPage(organizationService.getOrganizationStaffs(pageable, organization));
    }
}
