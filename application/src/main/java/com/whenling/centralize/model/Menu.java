package com.whenling.centralize.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.whenling.module.domain.model.Resourceable;
import com.whenling.module.domain.model.TreeEntity;

/**
 * 菜单实体类
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午4:26:03
 */
@Entity
@Table(name = "sys_menu")
public class Menu extends TreeEntity<User, Long, Menu> implements Resourceable {

	private static final long serialVersionUID = -1346718655779585942L;

	@Column(nullable = false, length = 50)
	private String text;

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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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
