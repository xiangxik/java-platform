package com.whenling.module.domain.event;

public class PreInsertEvent<T> extends EntityEvent<T> {

	private static final long serialVersionUID = 1676951782819808570L;

	public PreInsertEvent(Object source, T entity) {
		super(source, entity);
	}

}
