package tech.hiphone.shop.service.goods.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tech.hiphone.commons.constants.ErrorCodeContants;
import tech.hiphone.commons.exceptioin.ServiceException;
import tech.hiphone.shop.domain.Goods;
import tech.hiphone.shop.domain.OrderGoodsRelation;
import tech.hiphone.shop.domain.OrderInfo;
import tech.hiphone.shop.domain.common.Price;
import tech.hiphone.shop.domain.id.OrderGoodsRelationId;
import tech.hiphone.shop.repository.GoodsRepository;
import tech.hiphone.shop.repository.OrderGoodsRelationRepository;
import tech.hiphone.shop.service.goods.GoodsHandler;
import tech.hiphone.shop.service.order.dto.GoodsInfoDTO;
import tech.hiphone.shop.service.order.dto.OrderInfoDTO;
import tech.hiphone.shop.service.order.dto.PricingDTO;

public class DefaultGoodsHandler implements GoodsHandler {

    private static final Logger log = LoggerFactory.getLogger(DefaultGoodsHandler.class);

    private GoodsRepository goodsRepository;

    private OrderGoodsRelationRepository orderGoodsRelationRepository;

    public DefaultGoodsHandler(GoodsRepository goodsRepository,
            OrderGoodsRelationRepository orderGoodsRelationRepository) {
        this.goodsRepository = goodsRepository;
        this.orderGoodsRelationRepository = orderGoodsRelationRepository;
    }

    @Override
    public PricingDTO getPrice(PricingDTO pricingDTO) {
        GoodsInfoDTO data = pricingDTO.getGoodsInfo();
        Long goodsId = data.getGoodsId();
        Price price = goodsRepository.findById(goodsId).map(Goods::getPrice)
                .orElseThrow(() -> new ServiceException(ErrorCodeContants.CANNOT_GET_PRICE));
        pricingDTO.setPrice(price);
        log.debug("resp: {}", pricingDTO);
        return pricingDTO;
    }

    @Override
    public void save(OrderInfo orderInfo, PricingDTO pricingDTO) {
        GoodsInfoDTO data = pricingDTO.getGoodsInfo();
        Long goodsId = data.getGoodsId();
        Goods goods = goodsRepository.findById(goodsId)
                .orElseThrow(() -> new ServiceException(ErrorCodeContants.LACK_OF_DATA));
        OrderGoodsRelation orderGoodsRelation = new OrderGoodsRelation();
        orderGoodsRelation.setId(new OrderGoodsRelationId(orderInfo, goods));
        orderGoodsRelationRepository.save(orderGoodsRelation);
    }

    @Override
    public void checkSituation(OrderInfoDTO orderInfoDTO) {
        // do nothing
    }

    @Override
    public void finishCharge(OrderInfo orderInfo) {
        // do nothing
    }

}
