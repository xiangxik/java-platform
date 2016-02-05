package com.whenling.core.support.extjs.api.data;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class NodeInterface<T extends NodeInterface<T>> {

	private Boolean allowDrag;

	private Boolean allowDrop;
	private Boolean checked;
	private T[] children;
	private String cls;
	private Boolean expandable;
	private String expanded;
	private String href;
	private String hrefTarget;
	private String icon;
	private Integer qshowDelay;
	private String qtip;
	private String qtitle;

	@XStreamAsAttribute
	private String text;

	public Boolean getAllowDrag() {
		return allowDrag;
	}

	public void setAllowDrag(Boolean allowDrag) {
		this.allowDrag = allowDrag;
	}

	public Boolean getAllowDrop() {
		return allowDrop;
	}

	public void setAllowDrop(Boolean allowDrop) {
		this.allowDrop = allowDrop;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public T[] getChildren() {
		return children;
	}

	public void setChildren(T[] children) {
		this.children = children;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public Boolean getExpandable() {
		return expandable;
	}

	public void setExpandable(Boolean expandable) {
		this.expandable = expandable;
	}

	public String getExpanded() {
		return expanded;
	}

	public void setExpanded(String expanded) {
		this.expanded = expanded;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getHrefTarget() {
		return hrefTarget;
	}

	public void setHrefTarget(String hrefTarget) {
		this.hrefTarget = hrefTarget;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Boolean getLeaf() {
		T[] children = getChildren();
		return children == null || children.length == 0;
	}

	public Integer getQshowDelay() {
		return qshowDelay;
	}

	public void setQshowDelay(Integer qshowDelay) {
		this.qshowDelay = qshowDelay;
	}

	public String getQtip() {
		return qtip;
	}

	public void setQtip(String qtip) {
		this.qtip = qtip;
	}

	public String getQtitle() {
		return qtitle;
	}

	public void setQtitle(String qtitle) {
		this.qtitle = qtitle;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
