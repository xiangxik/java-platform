package com.whenling.extension.cms.support.template;

/**
 * 模板文件
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午4:33:48
 */
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
