package tech.hiphone.commons.repository;

import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;

import tech.hiphone.commons.domain.common.AbstractAccountRelation;
import tech.hiphone.commons.domain.common.AbstractUser;
import tech.hiphone.commons.domain.id.ModulePrincipalId;
import tech.hiphone.framework.jpa.support.JpaExtRepository;

@NoRepositoryBean
public interface AbstractAccountRelationRepository<U extends AbstractUser, T extends AbstractAccountRelation<U>>
        extends JpaExtRepository<T, ModulePrincipalId> {

    Optional<T> findOneByIdModuleAndUser(String module, U user);

}
