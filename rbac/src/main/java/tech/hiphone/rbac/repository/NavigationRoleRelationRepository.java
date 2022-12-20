package tech.hiphone.rbac.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tech.hiphone.rbac.domain.NavigationRoleRelation;
import tech.hiphone.rbac.domain.Role;
import tech.hiphone.rbac.domain.id.NavigationRoleId;

public interface NavigationRoleRelationRepository extends JpaRepository<NavigationRoleRelation, NavigationRoleId> {

    void deleteByIdRole(Role role);

    List<NavigationRoleRelation> findAllByIdRole(Role role);

    Page<NavigationRoleRelation> findByIdRole(Pageable pageable, Role role);

}
