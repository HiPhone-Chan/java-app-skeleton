package com.chf.commons.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chf.commons.domain.OperationLog;

public interface OperationLogRepository extends JpaRepository<OperationLog, String> {

}
