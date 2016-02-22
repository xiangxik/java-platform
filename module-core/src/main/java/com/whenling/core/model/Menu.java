package com.whenling.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.whenling.core.support.entity.Resourceable;
import com.whenling.core.support.entity.TreeEntity;

@Entity
@Table(name = "sys_menu")
public class Menu extends TreeEntity<Long, User, Menu> implements Resourceable {

	private static final long serialVersionUID = -1346718655779585942L;

	@Column(nullable = false, length = 50)
	private String name;

	@Column(nullable = false, length = 50, unique = true)
	private String code;

	@Column(length = 50)
	private String iconCls;

	@Column(length = 100)
	private String view;

	@Column(length = 500)
	private String config;

	@Override
	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
