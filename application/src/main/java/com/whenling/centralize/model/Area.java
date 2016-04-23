package com.whenling.centralize.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.whenling.module.domain.model.TreeEntity;

@Entity
@Table(name = "sys_area")
public class Area extends TreeEntity<User, Long, Area> {

	private static final long serialVersionUID = 7040806381583412911L;

	/** 名称 */
	@NotEmpty
	@Length(max = 100)
	@Column(nullable = false, length = 100)
	private String name;

	/** 全称 */
	@Column(nullable = false, length = 500)
	private String fullName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}
