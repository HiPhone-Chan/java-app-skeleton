package tech.hiphone.commons.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.hiphone.commons.domain.OperationLog;

public interface OperationLogRepository extends JpaRepository<OperationLog, String> {

}
