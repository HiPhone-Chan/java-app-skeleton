package tech.hiphone.shop.service.goods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.hiphone.commons.service.SpringService;
import tech.hiphone.commons.utils.BeanUtil;
import tech.hiphone.shop.domain.OrderInfo;
import tech.hiphone.shop.service.order.dto.GoodsInfoDTO;
import tech.hiphone.shop.service.order.dto.OrderInfoDTO;
import tech.hiphone.shop.service.order.dto.PricingDTO;

@Service
public class GoodsService {

    private static final Logger log = LoggerFactory.getLogger(GoodsService.class);

    @Autowired
    private SpringService springService;

    public PricingDTO getPrice(PricingDTO pricingDTO) {
        GoodsInfoDTO goodsInfo = pricingDTO.getGoodsInfo();
        return getGoodsHandler(goodsInfo.getType()).getPrice(pricingDTO);
    }

    public void save(OrderInfo orderInfo, PricingDTO pricingDTO) {
        GoodsInfoDTO goodsInfo = pricingDTO.getGoodsInfo();
        getGoodsHandler(goodsInfo.getType()).save(orderInfo, pricingDTO);
    }

    // 检查是否重复购买
    public void checkSituation(OrderInfoDTO orderInfoDTO) {
        PricingDTO pricingDTO = BeanUtil.castObject(orderInfoDTO.getPricingInfo(), PricingDTO.class);
        GoodsInfoDTO goodsInfo = pricingDTO.getGoodsInfo();
        getGoodsHandler(goodsInfo.getType()).checkSituation(orderInfoDTO);
    }

    public void finishCharge(OrderInfo orderInfo) {
        PricingDTO pricingDTO = BeanUtil.castObject(orderInfo.getPricingInfo(), PricingDTO.class);
        GoodsInfoDTO goodsInfo = pricingDTO.getGoodsInfo();
        getGoodsHandler(goodsInfo.getType()).finishCharge(orderInfo);
    }

    private GoodsHandler getGoodsHandler(String type) {
        GoodsHandler goodsHandler = (GoodsHandler) springService.getBean(type + GoodsHandler.HANDLER_SUFFIX);

        if (goodsHandler == null) {
            log.error("Use default goodsHandler for: {}", type);
            goodsHandler = (GoodsHandler) springService.getBean("default" + GoodsHandler.HANDLER_SUFFIX);
        }
        return goodsHandler;
    }
}
