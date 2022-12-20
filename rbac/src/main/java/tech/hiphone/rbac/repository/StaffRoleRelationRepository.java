package tech.hiphone.rbac.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tech.hiphone.rbac.domain.Staff;
import tech.hiphone.rbac.domain.StaffRoleRelation;
import tech.hiphone.rbac.domain.id.StaffRoleId;

public interface StaffRoleRelationRepository extends JpaRepository<StaffRoleRelation, StaffRoleId> {

    List<StaffRoleRelation> findAllByIdStaff(Staff staff);

    Page<StaffRoleRelation> findByIdStaff(Pageable pageable, Staff staff);

    void deleteByIdStaff(Staff staff);
}
