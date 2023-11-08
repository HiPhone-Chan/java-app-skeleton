package tech.hiphone.framework.jpa.support;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;

@NoRepositoryBean
public interface JpaExtRepository<T, ID> extends JpaRepositoryImplementation<T, ID> {

    long sum(@NonNull Specification<T> spec);

}
