package tech.hiphone.shop.service.goods;

import tech.hiphone.shop.domain.OrderInfo;
import tech.hiphone.shop.service.order.dto.OrderInfoDTO;
import tech.hiphone.shop.service.order.dto.PricingDTO;

public interface GoodsHandler {

    String HANDLER_SUFFIX = "GoodsHandler";

    // 根据计价信息计算价格
    PricingDTO getPrice(PricingDTO pricingDTO);

    // 保存信息到其他表
    void save(OrderInfo orderInfo, PricingDTO pricingDTO);

    // 检查购买情况
    void checkSituation(OrderInfoDTO orderInfoDTO);

    // 购买成功后的操作
    void finishCharge(OrderInfo orderInfo);
}
