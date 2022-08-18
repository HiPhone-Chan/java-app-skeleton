package com.chf.commons.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chf.commons.repository.OperationLogRepository;
import com.chf.commons.web.vm.OperationLogVM;
import com.chf.framework.web.util.ResponseUtil;

/**
 * 错做日志
 */
@RestController
@RequestMapping("/api/admin")
public class OperationLogResource {

    @Autowired
    private OperationLogRepository operationLogRepository;

    @GetMapping("/operation-logs")
    public ResponseEntity<List<OperationLogVM>> getOperationLogs(Pageable pageable) {
        Page<OperationLogVM> page = operationLogRepository.findAll(pageable).map(OperationLogVM::new);
        return ResponseUtil.wrapPage(page);
    }
}
