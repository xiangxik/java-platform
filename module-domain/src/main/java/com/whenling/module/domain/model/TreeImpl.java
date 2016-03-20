package com.whenling.module.domain.model;

import java.util.List;
import java.util.Set;

/**
 * 树，实现类
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午7:02:43
 * @param <T>
 */
public class TreeImpl<T extends Treeable<T>> implements Tree<T> {

	private List<Node<T>> roots;
	private Set<T> checked;
	private boolean checkable = false;
	private boolean expendAll = false;
	private String iconProperty;

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

	@Override
	public void makeExpandAll() {
		this.expendAll = true;
	}

	@Override
	public boolean isExpandAll() {
		return expendAll;
	}

	@Override
	public void setIconProperty(String propertyName) {
		this.iconProperty = propertyName;
	}

	@Override
	public String getIconProperty() {
		return this.iconProperty;
	}

}
