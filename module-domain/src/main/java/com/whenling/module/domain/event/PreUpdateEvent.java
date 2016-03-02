package com.whenling.module.domain.event;

public class PreUpdateEvent<T> extends EntityEvent<T>{

	private static final long serialVersionUID = -5700705668510463293L;

	public PreUpdateEvent(Object source, T entity) {
		super(source, entity);
		// TODO Auto-generated constructor stub
	}

}
