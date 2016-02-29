package com.whenling.module.domain.model;

import java.util.List;

public interface Treeable<T> extends Sortable {

	public T getParent();

	public void setParent(T parent);

	public List<T> getChildren();

	public void setChildren(List<T> children);
}
