package com.whenling.core.support.entity;

import java.io.Serializable;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class TreeEntityListener {

	/** 树路径分隔符 */
	private static final String TREE_PATH_SEPARATOR = ",";

	/**
	 * 
	 * @param entity
	 */
	@PrePersist
	public <I extends Serializable, U, T> void prePersist(TreeEntity<I, U, T> entity) {
		@SuppressWarnings("unchecked")
		TreeEntity<I, U, T> parent = (TreeEntity<I, U, T>) entity.getParent();
		if (parent != null) {
			entity.setTreePath(parent.getTreePath() + parent.getId() + TREE_PATH_SEPARATOR);
		} else {
			entity.setTreePath(TREE_PATH_SEPARATOR);
		}
	}

	/**
	 * 
	 * @param entity
	 */
	@PreUpdate
	public <I extends Serializable, U, T> void preUpdate(TreeEntity<I, U, T> entity) {
		@SuppressWarnings("unchecked")
		TreeEntity<I, U, T> parent = (TreeEntity<I, U, T>) entity.getParent();
		if (parent != null) {
			entity.setTreePath(parent.getTreePath() + parent.getId() + TREE_PATH_SEPARATOR);
		} else {
			entity.setTreePath(TREE_PATH_SEPARATOR);
		}
	}
}
