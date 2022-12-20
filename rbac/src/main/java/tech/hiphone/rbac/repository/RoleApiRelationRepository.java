package tech.hiphone.rbac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.hiphone.rbac.domain.ApiInfo;
import tech.hiphone.rbac.domain.RoleApiRelation;
import tech.hiphone.rbac.domain.Role;
import tech.hiphone.rbac.domain.id.RoleApiId;

public interface RoleApiRelationRepository extends JpaRepository<RoleApiRelation, RoleApiId> {

    List<RoleApiRelation> findAllByIdRole(Role role);

    List<RoleApiRelation> findAllByIdApiInfo(ApiInfo apiInfo);

    void deleteByIdRole(Role role);

}
