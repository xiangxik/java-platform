package com.whenling.extension.cms.model;

public class TemplateFile {
	private String text;
	private String iconCls;
	private String path;
	private TemplateFile[] children;

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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public TemplateFile[] getChildren() {
		return children;
	}

	public void setChildren(TemplateFile[] children) {
		this.children = children;
	}

	public Boolean getLeaf() {
		TemplateFile[] children = getChildren();
		return children == null || children.length == 0;
	}
}
