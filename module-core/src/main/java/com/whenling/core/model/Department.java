package com.whenling.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.whenling.core.support.entity.TreeEntity;

/**
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年2月6日 上午10:44:44
 */
@Entity
@Table(name = "sys_department")
public class Department extends TreeEntity<Long, User, Department> {

	private static final long serialVersionUID = 7859937470706747261L;

	@Column(length = 100, nullable = false)
	private String name;

	@Column(length = 50)
	private String code;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manager_id")
	private User manager;

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

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

}
