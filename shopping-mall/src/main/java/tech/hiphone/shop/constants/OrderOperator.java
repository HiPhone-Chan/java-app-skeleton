package tech.hiphone.shop.constants;

public interface OrderOperator {
	// 创建
	String CREATE = "create";
	// 支付
	String CHARGE = "charge";
	// 重新支付
	String RECHARGE = "recharge";
	// 更新状态
	String REFRESH_STATUS = "refreshStatus";
	// 取消
	String CANCEL = "cancel";

}
