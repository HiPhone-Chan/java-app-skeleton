package tech.hiphone.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tech.hiphone.framework.jpa.support.JpaExtRepository;
import tech.hiphone.shop.domain.OrderInfo;

public interface OrderInfoRepository extends JpaExtRepository<OrderInfo, String> {

    Page<OrderInfo> findByStatus(Pageable pageable, int status);

    long countByStatus(Integer status);

    void deleteByStatus(int status);

}
