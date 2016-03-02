package com.whenling.module.domain.event;

public class PostUpdateEvent<T> extends EntityEvent<T> {

	private static final long serialVersionUID = -4938054063240972454L;

	public PostUpdateEvent(Object source, T entity) {
		super(source, entity);
	}

}
