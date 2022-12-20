package tech.hiphone.rbac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.hiphone.rbac.domain.Navigation;
import tech.hiphone.rbac.domain.NavigationApiRelation;
import tech.hiphone.rbac.domain.id.NavigationApiId;

public interface NavigationApiRelationRepository extends JpaRepository<NavigationApiRelation, NavigationApiId> {

    boolean existsByIdNavigation(Navigation navigation);

    List<NavigationApiRelation> findAllByIdNavigation(Navigation navigation);

}
