package com.whenling.module.domain.model;

import java.io.Serializable;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * 树形实体监听器
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午7:02:31
 */
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
