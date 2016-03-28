package com.whenling.module.domain.event;

public class PostInsertEvent extends EntityEvent {

	private static final long serialVersionUID = 4276069421486369324L;

	public PostInsertEvent(Object source, Object entity) {
		super(source, entity);
	}

}
