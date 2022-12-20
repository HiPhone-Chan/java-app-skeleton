package tech.hiphone.rbac.repository;

import java.util.Optional;

import tech.hiphone.framework.jpa.support.JpaExtRepository;
import tech.hiphone.rbac.domain.ApiInfo;

public interface ApiInfoRepository extends JpaExtRepository<ApiInfo, String> {

    Optional<ApiInfo> findOneByMethodAndPath(String method, String path);

    boolean existsByMethodAndPath(String method, String path);

}
