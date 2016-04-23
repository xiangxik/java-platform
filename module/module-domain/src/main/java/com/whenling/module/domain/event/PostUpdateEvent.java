package com.whenling.module.domain.event;

public class PostUpdateEvent extends EntityEvent {

	private static final long serialVersionUID = -4938054063240972454L;

	public PostUpdateEvent(Object source, Object entity) {
		super(source, entity);
	}

}
