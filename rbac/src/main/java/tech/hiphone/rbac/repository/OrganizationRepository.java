package tech.hiphone.rbac.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tech.hiphone.rbac.domain.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, String> {

    Page<Organization> findByParent(Pageable pageable, Organization parent);

    boolean existsByParent(Organization parent);
}
