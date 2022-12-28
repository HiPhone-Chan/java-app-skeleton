package tech.hiphone.cms.repository;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

// TM TargetMaterial, T target<>
@NoRepositoryBean
public interface TargetMaterialRepository<TM, T> {

    List<TM> findAllByIdTarget(T target);

}
