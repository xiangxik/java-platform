package com.whenling.module.domain.model;

import java.util.List;

/**
 * 节点。一般用于树形
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午6:09:32
 * @param <T>
 */
public class Node<T extends Treeable<T>> {

	private T data;
	private List<Node<T>> children;

	private Boolean checked;

	private Boolean expended;

	public Boolean getLeaf() {
		List<Node<T>> children = getChildren();
		return children == null || children.size() == 0;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<Node<T>> getChildren() {
		return children;
	}

	public void setChildren(List<Node<T>> children) {
		this.children = children;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Boolean getExpended() {
		return expended;
	}

	public void setExpended(Boolean expended) {
		this.expended = expended;
	}

}
