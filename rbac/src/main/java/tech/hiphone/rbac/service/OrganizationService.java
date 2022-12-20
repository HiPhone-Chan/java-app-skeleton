package tech.hiphone.rbac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tech.hiphone.rbac.domain.Organization;
import tech.hiphone.rbac.repository.OrganizationStaffRelationRepository;
import tech.hiphone.rbac.service.dto.AdminStaffDTO;

@Service
@Transactional
public class OrganizationService {

    @Autowired
    private OrganizationStaffRelationRepository organizationStaffRelationRepository;

    public Page<AdminStaffDTO> getOrganizationStaffs(Pageable pageable, Organization organization) {
        return organizationStaffRelationRepository.findByIdOrganization(pageable, organization).map(orgUser -> {
            return new AdminStaffDTO(orgUser.getId().getStaff().getUser());
        });
    }

}
