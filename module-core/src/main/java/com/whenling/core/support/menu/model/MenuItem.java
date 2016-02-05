package com.whenling.core.support.menu.model;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.whenling.core.support.entity.Resourceable;
import com.whenling.core.support.extjs.api.data.NodeInterface;

public class MenuItem extends NodeInterface<MenuItem> implements Resourceable {

	@XStreamAsAttribute
	private String code;

	@XStreamAsAttribute
	private String iconCls;

	@XStreamAsAttribute
	private String view;

	@XStreamAsAttribute
	private String config;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

}
