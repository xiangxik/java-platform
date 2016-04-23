package com.whenling.module.domain.model;

import java.io.Serializable;

import javax.persistence.Transient;

import org.springframework.util.ClassUtils;

/**
 * 实体值对象
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午6:08:48
 * @param <ID>
 */
public class EntityVo<ID extends Serializable> {

	private ID id;

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	@Transient
	public boolean isNew() {
		return null == getId();
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
