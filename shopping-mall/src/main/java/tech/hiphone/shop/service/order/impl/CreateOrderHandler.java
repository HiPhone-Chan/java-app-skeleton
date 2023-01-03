package tech.hiphone.shop.service.order.impl;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tech.hiphone.commons.utils.JsonUtil;
import tech.hiphone.shop.constants.OrderStatus;
import tech.hiphone.shop.domain.OrderInfo;
import tech.hiphone.shop.repository.OrderInfoRepository;
import tech.hiphone.shop.service.goods.GoodsService;
import tech.hiphone.shop.service.order.OrderHandler;
import tech.hiphone.shop.service.order.dto.OrderInfoDTO;
import tech.hiphone.shop.service.order.dto.PricingDTO;
import tech.hiphone.shop.service.order.extra.OrderExtraService;
import tech.hiphone.shop.utils.OrderUtil;

@Service
@Transactional
public class CreateOrderHandler implements OrderHandler {

    @Autowired
    private OrderInfoRepository orderInfoRepository;


    @Autowired
    private OrderExtraService orderExtraService;

    @Autowired
    private GoodsService goodsService;

    public CreateOrderHandler() {
        OrderUtil.init(RandomUtils.nextLong());
    }

    @Override
    public Object operate(OrderInfo orderInfo, Object reqValue) {
        OrderInfoDTO orderInfoDTO = (OrderInfoDTO) reqValue;
        orderInfo = createOrder(orderInfoDTO);
        orderInfoRepository.save(orderInfo);
        goodsService.save(orderInfo, orderInfoDTO.getPricingInfo());
        orderExtraService.save(orderInfo, orderInfoDTO.getExtraInfo());
        return orderInfo;
    }

    public OrderInfo createOrder(OrderInfoDTO orderInfoDTO) {
        goodsService.checkSituation(orderInfoDTO);
        OrderInfo orderInfo = new OrderInfo();
        long orderId = OrderUtil.generateOrderId();
        orderInfo.setId(String.valueOf(orderId));

        orderInfo.setStatus(OrderStatus.CREATED);
        orderInfo.setAppId(orderInfoDTO.getAppId());
        orderInfo.setTitle(orderInfoDTO.getTitle());
        orderInfo.setDescription(orderInfoDTO.getDescription());

        PricingDTO priceInfo = goodsService.getPrice(orderInfoDTO.getPricingInfo());
        orderInfo.setPrice(priceInfo.getPrice());
        orderInfo.setPricingInfo(JsonUtil.toJsonString(priceInfo));
        orderInfo.setExtraInfo(JsonUtil.toJsonString(orderInfoDTO.getExtraInfo()));
        orderInfo.setRemark(orderInfoDTO.getRemark());

        return orderInfo;
    }

}
