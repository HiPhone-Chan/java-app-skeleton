package tech.hiphone.rbac.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tech.hiphone.rbac.domain.Organization;
import tech.hiphone.rbac.domain.OrganizationStaffRelation;
import tech.hiphone.rbac.domain.id.OrganizationStaffId;

public interface OrganizationStaffRelationRepository extends JpaRepository<OrganizationStaffRelation, OrganizationStaffId> {

    Page<OrganizationStaffRelation> findByIdOrganization(Pageable pageable, Organization organization);

}
