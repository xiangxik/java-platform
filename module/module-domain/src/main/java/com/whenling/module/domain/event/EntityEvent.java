package com.whenling.module.domain.event;

import org.springframework.context.ApplicationEvent;

public class EntityEvent extends ApplicationEvent {

	private static final long serialVersionUID = 3509191619140799740L;

	private Object entity;

	public EntityEvent(Object source, Object entity) {
		super(source);

		this.entity = entity;
	}

	public Object getEntity() {
		return entity;
	}

}
