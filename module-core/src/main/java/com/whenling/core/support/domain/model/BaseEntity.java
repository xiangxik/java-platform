package com.whenling.core.support.domain.model;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.util.ClassUtils;

@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable> extends AbstractPersistable<ID> {

	private static final long serialVersionUID = 5227521689231872715L;

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

		BaseEntity<?> that = (BaseEntity<?>) obj;

		return null == this.getId() ? false : this.getId().equals(that.getId());
	}
}
