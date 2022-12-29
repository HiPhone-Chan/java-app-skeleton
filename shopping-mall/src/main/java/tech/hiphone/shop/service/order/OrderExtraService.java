package tech.hiphone.shop.service.order;

import org.springframework.stereotype.Service;

import tech.hiphone.shop.domain.OrderInfo;
import tech.hiphone.shop.service.order.dto.OrderExtraDTO;

@Service
public class OrderExtraService {

    // 保存信息到其他表
    public void save(OrderInfo orderInfo, OrderExtraDTO orderExtraDTO) {
        if (orderExtraDTO == null) {
            return;
        }

        Object extraData = orderExtraDTO.getData();
        if (extraData == null) {
            return;
        }

        String type = orderExtraDTO.getType();
        switch (type) {
       // TODO
        }

    }

}
