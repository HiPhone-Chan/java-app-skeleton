package tech.hiphone.shop.constants;

public interface OrderStatus {
    /**
     * 取消
     */
    int CANCEL = -4;
    /**
     * 过期
     */
    int EXPIRED = -3;
    /**
     * 系统处理中
     */
    int SYS_PROCESSING = -2;
    /**
     * 失败
     */
    int FAILED = -1;
    /**
     * 完成支付
     */
    int FINISH = 0;
    /**
     * 创建
     */
    int CREATED = 1;
    /**
     * 等待支付
     */
    int WAITING_CHARGE = 2;

}
