package tech.hiphone.shop.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.hiphone.commons.constants.ErrorCodeContants;
import tech.hiphone.commons.exceptioin.ServiceException;
import tech.hiphone.commons.utils.BeanUtil;
import tech.hiphone.shop.constants.PricingType;
import tech.hiphone.shop.domain.Goods;
import tech.hiphone.shop.domain.OrderGoodsRelation;
import tech.hiphone.shop.domain.OrderInfo;
import tech.hiphone.shop.domain.common.Price;
import tech.hiphone.shop.domain.id.OrderGoodsRelationId;
import tech.hiphone.shop.repository.GoodsRepository;
import tech.hiphone.shop.repository.OrderGoodsRelationRepository;
import tech.hiphone.shop.service.order.dto.GoodsPricingDataDTO;
import tech.hiphone.shop.service.order.dto.PricingDTO;

@Service
public class PricingService {

    private static final Logger log = LoggerFactory.getLogger(PricingService.class);

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private OrderGoodsRelationRepository orderGoodsRelationRepository;

    public PricingDTO getPrice(PricingDTO pricingDTO) {
        String type = pricingDTO.getType();
        switch (type) {
        case PricingType.GOODS: {
            GoodsPricingDataDTO data = BeanUtil.castObject(pricingDTO.getData(), GoodsPricingDataDTO.class);
            Long goodsId = data.getGoodsId();
            Price price = goodsRepository.findById(goodsId).map(Goods::getPrice)
                    .orElseThrow(() -> new ServiceException(ErrorCodeContants.CANNOT_GET_PRICE));
            pricingDTO.setPrice(price);
            log.debug("resp: {}", pricingDTO);
            return pricingDTO;
        }
        default:
        }
        throw new ServiceException(ErrorCodeContants.CANNOT_GET_PRICE);
    }

    // 保存信息到其他表
    public void save(OrderInfo orderInfo, PricingDTO pricingDTO) {
        String type = pricingDTO.getType();
        switch (type) {
        case PricingType.GOODS: {
            GoodsPricingDataDTO data = BeanUtil.castObject(pricingDTO.getData(), GoodsPricingDataDTO.class);
            Long goodsId = data.getGoodsId();
            Goods goods = goodsRepository.findById(goodsId)
                    .orElseThrow(() -> new ServiceException(ErrorCodeContants.LACK_OF_DATA));
            OrderGoodsRelation orderGoodsRelation = new OrderGoodsRelation();
            orderGoodsRelation.setId(new OrderGoodsRelationId(orderInfo, goods));
            orderGoodsRelationRepository.save(orderGoodsRelation);
        }
        default:
        }
    }

}
