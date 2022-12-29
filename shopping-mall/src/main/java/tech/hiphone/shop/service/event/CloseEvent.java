package tech.hiphone.shop.service.event;

import org.springframework.context.ApplicationEvent;

public class CloseEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	public CloseEvent(String source) {
		super(source);
	}

	public String getSource() {
		return (String) super.getSource();
	}

}
