package com.whenling.core.support.entity;

import java.util.List;

public interface Treeable<T> extends Sortable {

	public T getParent();

	public void setParent(T parent);

	public List<T> getChildren();

	public void setChildren(List<T> children);
}
