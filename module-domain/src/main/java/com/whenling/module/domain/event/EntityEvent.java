package com.whenling.module.domain.event;

import org.springframework.context.ApplicationEvent;

public class EntityEvent<T> extends ApplicationEvent {

	private static final long serialVersionUID = 3509191619140799740L;

	private T entity;

	public EntityEvent(Object source, T entity) {
		super(source);

		this.entity = entity;
	}

	public T getEntity() {
		return entity;
	}

}
