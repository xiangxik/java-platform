package com.whenling.module.domain.model;

import java.util.List;
import java.util.Set;

public class TreeImpl<T extends Treeable<T>> implements Tree<T> {

	private List<Node<T>> roots;
	private Set<T> checked;
	private boolean checkable = false;

	public TreeImpl(List<Node<T>> roots) {
		this.roots = roots;
	}

	@Override
	public List<Node<T>> getRoots() {
		return roots;
	}

	@Override
	public Set<T> getChecked() {
		return checked;
	}

	@Override
	public void setChecked(Set<T> checked) {
		this.checked = checked;
	}

	@Override
	public boolean isCheckable() {
		return checkable;
	}

	@Override
	public void makeCheckable() {
		this.checkable = true;
	}

}
