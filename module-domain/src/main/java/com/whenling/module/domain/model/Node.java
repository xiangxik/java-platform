package com.whenling.module.domain.model;

import java.util.List;

public class Node<T extends Treeable<T>> {

	private T data;
	private List<Node<T>> children;

	private Boolean checked;

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

}
