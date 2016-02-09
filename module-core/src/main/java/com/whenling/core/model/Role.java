package com.whenling.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.whenling.core.support.entity.BizEntity;
import com.whenling.core.support.entity.Lockedable;

@Entity
@Table(name = "sys_role")
public class Role extends BizEntity<User, Long> implements Lockedable {

	private static final long serialVersionUID = 5532516952163494134L;

	@Column(nullable = false, length = 50)
	private String name;

	@Column(nullable = false, length = 50, unique = true)
	private String code;

	@Column(nullable = false)
	private boolean locked = false;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public boolean getLocked() {
		// TODO Auto-generated method stub
		return locked;
	}

	@Override
	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	@Override
	public void markLocked() {
		this.locked = true;
	}

}
