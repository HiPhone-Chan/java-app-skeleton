package tech.hiphone.rbac.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.hiphone.rbac.domain.Role;

public interface RoleRepository extends JpaRepository<Role, String> {

}
