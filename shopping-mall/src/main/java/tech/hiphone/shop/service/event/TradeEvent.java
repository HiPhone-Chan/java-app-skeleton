package tech.hiphone.shop.service.event;

import org.springframework.context.ApplicationEvent;

import tech.hiphone.shop.service.order.dto.TradeDTO;

public class TradeEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    public TradeEvent(TradeDTO source) {
        super(source);
    }

    public TradeDTO getSource() {
        return (TradeDTO) super.getSource();
    }

}
