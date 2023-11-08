package tech.hiphone.commons.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;

import tech.hiphone.commons.domain.common.AbstractUser;
import tech.hiphone.framework.jpa.support.JpaExtRepository;

@NoRepositoryBean
public interface AbstractUserRepository<U extends AbstractUser> extends JpaExtRepository<U, Long> {

    boolean existsByLogin(String login);

    Optional<U> findOneByLogin(String login);

    Optional<U> findOneByPrincipal(String principal);

    Optional<U> findOneByValidateKey(String resetKey);

    Optional<U> findOneByResetKey(String resetKey);

    List<U> findAllByActivatedIsFalseAndCreatedDateBefore(Instant dateTime);

}
