package tech.hiphone.weixin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.hiphone.weixin.domain.WxMediaCheckResult;
import tech.hiphone.weixin.domain.id.MediaCheckId;

public interface WxMediaCheckResultRepository extends JpaRepository<WxMediaCheckResult, MediaCheckId> {

}
