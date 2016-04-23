package com.whenling.module.domain.event;

public class PreInsertEvent extends EntityEvent {

	private static final long serialVersionUID = 1676951782819808570L;

	public PreInsertEvent(Object source, Object entity) {
		super(source, entity);
	}

}
