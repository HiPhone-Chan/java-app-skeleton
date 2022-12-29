package tech.hiphone.shop.constants;

public interface CouponStatus {
    /**
     * 使用中,订单支付后等结果的状态
     */
    byte USING = -3;
    /**
     * 过期
     */
    byte EXPIRED = -2;
    /**
     * 删除了
     */
    byte DELETED = -1;
    /**
     * 激活
     */
    byte ACTIVED = 0;
    /**
     * 使用了
     */
    byte USED = 1;
    /**
     * 已领取, 未使用
     */
    byte RECEIVED = 2;

}
