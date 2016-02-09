package com.whenling.core.support.web;

import java.io.Serializable;

import org.springframework.util.ClassUtils;

public class EntityVo<ID extends Serializable> {

	private ID id;

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {

		if (null == obj) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		Class<?> thisClass = ClassUtils.getUserClass(getClass());
		Class<?> objClass = ClassUtils.getUserClass(obj.getClass());

		if (!(ClassUtils.isAssignable(thisClass, objClass) || ClassUtils.isAssignable(objClass, thisClass))) {
			return false;
		}

		EntityVo<?> that = (EntityVo<?>) obj;

		return null == this.getId() ? false : this.getId().equals(that.getId());
	}
}
