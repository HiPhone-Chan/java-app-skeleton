package tech.hiphone.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.hiphone.commons.constants.ErrorCodeContants;
import tech.hiphone.commons.domain.User;
import tech.hiphone.commons.exceptioin.ServiceException;
import tech.hiphone.commons.service.UserService;
import tech.hiphone.commons.utils.BeanUtil;
import tech.hiphone.shop.domain.Goods;
import tech.hiphone.shop.domain.OrderInfo;
import tech.hiphone.shop.repository.GoodsRepository;
import tech.hiphone.shop.service.order.dto.GoodsPricingDataDTO;
import tech.hiphone.shop.service.order.dto.OrderInfoDTO;
import tech.hiphone.shop.service.order.dto.PricingDTO;

@Service
public class GoodsService {

    @Autowired
    private GoodsRepository goodsRepository;
    
    @Autowired
    private UserService userService;

    // 检查是否重复购买
    public void checkGoodsBought(OrderInfoDTO orderInfoDTO) {
        User user = userService.getUserWithAuthorities().orElseThrow();
        PricingDTO pricingDTO = BeanUtil.castObject(orderInfoDTO.getPricingInfo(), PricingDTO.class);
        GoodsPricingDataDTO data = BeanUtil.castObject(pricingDTO.getData(), GoodsPricingDataDTO.class);
        Long goodsId = data.getGoodsId();
        Goods goods = goodsRepository.findById(goodsId)
                .orElseThrow(() -> new ServiceException(ErrorCodeContants.LACK_OF_DATA));
        String goodsType = goods.getType();
        // TODO
    }

    public void finishCharge(OrderInfo orderInfo) {
        PricingDTO pricingDTO = BeanUtil.castObject(orderInfo.getPricingInfo(), PricingDTO.class);
        GoodsPricingDataDTO result = BeanUtil.castObject(pricingDTO.getData(), GoodsPricingDataDTO.class);
        long goodsId = result.getGoodsId();
        Goods goods = goodsRepository.findById(goodsId)
                .orElseThrow(() -> new ServiceException(ErrorCodeContants.LACK_OF_DATA));

        User user = userService.getUserWithAuthoritiesByLogin(orderInfo.getCreatedBy())
                .orElseThrow(() -> new ServiceException(ErrorCodeContants.LACK_OF_DATA, "Cannot find student."));
        String goodsType = goods.getType();
        switch (goodsType) {

//        case GoodsType.VIRTUAL: {
//            VirtualGoods virtualGoods = virtualGoodsRepository.findById(goodsId).orElseThrow(
//                    () -> new ServiceException(ErrorCodeContants.LACK_OF_DATA, "Cannot get virtual goods."));
//            VirtualGoodsUserRelation virtualGoodsUserRelation = new VirtualGoodsUserRelation();
//            virtualGoodsUserRelation.setId(new VirtualGoodsUserKey(virtualGoods, student));
//            virtualGoodsUserRelation.setAmount(1);
//            virtualGoodsUserRelationRepository.save(virtualGoodsUserRelation);
//            break;
//        }
//        case GoodsType.OBJECT: {
//            break;
//        }
        default:
            throw new ServiceException(ErrorCodeContants.LACK_OF_DATA, "Not support this goods type: " + goodsType);
        }
    }
}
