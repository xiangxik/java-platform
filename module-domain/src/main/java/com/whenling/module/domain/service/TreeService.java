package com.whenling.module.domain.service;

import java.io.Serializable;
import java.util.List;

import com.whenling.module.domain.model.Tree;
import com.whenling.module.domain.model.TreeEntity;
import com.whenling.module.domain.repository.TreeRepository;

/**
 * 树形服务
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午7:04:51
 * @param <T>
 * @param <I>
 */
public class TreeService<T extends TreeEntity<?, I, T>, I extends Serializable> extends BaseService<T, I> {

	public List<T> findRoots() {
		return getTreeRepository().findRoots();
	}

	public List<T> findAllChildren(T current) {
		return getTreeRepository().findAllChildren(current);
	}

	public Tree<T> findTree(T current) {
		return getTreeRepository().findTree(current);
	}

	protected TreeRepository<T, I> getTreeRepository() {
		return ((TreeRepository<T, I>) baseRepository);
	}
}
