package com.whenling.module.domain.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import com.whenling.module.domain.model.Tree;
import com.whenling.module.domain.model.TreeEntity;

/**
 * 树形仓库
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午7:04:12
 * @param <T>
 * @param <I>
 */
@NoRepositoryBean
public interface TreeRepository<T extends TreeEntity<?, I, T>, I extends Serializable> extends BaseRepository<T, I> {

	List<T> findRoots();

	List<T> findAllChildren(T current);

	Tree<T> findTree(T current);

	Tree<T> toTree(T current, List<T> nodes);
}
