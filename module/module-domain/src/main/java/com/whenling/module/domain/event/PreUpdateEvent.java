package com.whenling.module.domain.event;

public class PreUpdateEvent extends EntityEvent {

	private static final long serialVersionUID = -5700705668510463293L;

	public PreUpdateEvent(Object source, Object entity) {
		super(source, entity);
	}

}
