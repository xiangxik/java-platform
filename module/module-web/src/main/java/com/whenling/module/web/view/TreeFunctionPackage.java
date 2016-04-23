package com.whenling.module.web.view;

import java.io.Serializable;
import java.util.List;

import com.whenling.module.domain.model.Tree;
import com.whenling.module.domain.model.TreeEntity;
import com.whenling.module.domain.service.TreeService;

public abstract class TreeFunctionPackage<T extends TreeEntity<?, I, T>, I extends Serializable>
		extends EntityFunctionPackage<T, I> {

	public List<T> findRoots() {
		return getTreeService().findRoots();
	}

	public List<T> findAllChildren(T current) {
		return getTreeService().findAllChildren(current);
	}

	public Tree<T> findTree(T current) {
		return getTreeService().findTree(current);
	}

	protected TreeService<T, I> getTreeService() {
		return (TreeService<T, I>) baseService;
	}
}
