package tech.hiphone.shop.repository;

import tech.hiphone.framework.jpa.support.JpaExtRepository;
import tech.hiphone.shop.domain.OrderGoodsRelation;
import tech.hiphone.shop.domain.id.OrderGoodsRelationId;

public interface OrderGoodsRelationRepository extends JpaExtRepository<OrderGoodsRelation, OrderGoodsRelationId> {

}
