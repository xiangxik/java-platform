package com.whenling.module.domain.model;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.validation.groups.Default;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.util.ClassUtils;

/**
 * 实体基类
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午6:08:27
 * @param <ID>
 */
@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable> extends AbstractPersistable<ID> {

	private static final long serialVersionUID = 5227521689231872715L;

	/**
	 * 保存验证组
	 */
	public interface Save extends Default {

	}

	/**
	 * 更新验证组
	 */
	public interface Update extends Default {

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

		BaseEntity<?> that = (BaseEntity<?>) obj;

		return null == this.getId() ? false : this.getId().equals(that.getId());
	}
}
