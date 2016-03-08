package com.whenling.centralize.support.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.whenling.module.domain.model.BaseEntity;

@Entity
@Table(name = DefaultDatabaseConfiguration.TABLE)
public class DatabaseConfigEntity extends BaseEntity<Long> {

	private static final long serialVersionUID = 7569248032441650622L;

	@Column(name = DefaultDatabaseConfiguration.NAME_COLUMN)
	private String name;

	@Column(name = DefaultDatabaseConfiguration.KEY_COLUMN)
	private String key;

	@Column(name = DefaultDatabaseConfiguration.VALUE_COLUMN)
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
