package com.whenling.module.domain.event;

public class PostInsertEvent<T> extends EntityEvent<T> {

	private static final long serialVersionUID = 4276069421486369324L;

	public PostInsertEvent(Object source, T entity) {
		super(source, entity);
	}

}
