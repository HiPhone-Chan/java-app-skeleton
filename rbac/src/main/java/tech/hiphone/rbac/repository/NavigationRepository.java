package tech.hiphone.rbac.repository;

import java.util.List;

import tech.hiphone.framework.jpa.support.JpaExtRepository;
import tech.hiphone.rbac.domain.Navigation;

public interface NavigationRepository extends JpaExtRepository<Navigation, String> {

    boolean existsByParent(Navigation parent);

    List<Navigation> findAllByParent(Navigation parent);

}
