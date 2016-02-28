package com.whenling.core.support.repo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import com.whenling.core.support.entity.Tree;
import com.whenling.core.support.entity.TreeEntity;

@NoRepositoryBean
public interface TreeRepository<T extends TreeEntity<I, ?, T>, I extends Serializable> extends BaseRepository<T, I> {

	List<T> findRoots();

	List<T> findAllChildren(T current);

	Tree<T> findTree(T current);

}
