package com.whenling.centralize.model;

import com.whenling.module.web.extjs.api.data.NodeInterface;

public class TemplateFile extends NodeInterface<TemplateFile> {
	private String iconCls;
	private String path;

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
