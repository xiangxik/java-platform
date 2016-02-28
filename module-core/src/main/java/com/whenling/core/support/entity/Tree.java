package com.whenling.core.support.entity;

import java.util.List;
import java.util.Set;

public interface Tree<T extends Treeable<T>> {

	List<Node<T>> getRoots();

	Set<T> getChecked();

	void setChecked(Set<T> checked);

	boolean isCheckable();

	void makeCheckable();
}
