package tech.hiphone.rbac.repository;

import java.util.Optional;

import tech.hiphone.commons.domain.User;
import tech.hiphone.framework.jpa.support.JpaExtRepository;
import tech.hiphone.rbac.domain.Staff;

public interface StaffRepository extends JpaExtRepository<Staff, String> {

    boolean existsById(String id);

    Optional<Staff> findOneByUser(User user);

    boolean deleteByUser(User user);
}
