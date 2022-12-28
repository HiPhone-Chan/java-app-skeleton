package tech.hiphone.weixin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.hiphone.weixin.domain.WxMediaCheckRecord;
import tech.hiphone.weixin.domain.id.MediaCheckId;

public interface WxMediaCheckRecordRepository extends JpaRepository<WxMediaCheckRecord, MediaCheckId> {

}
