package com.whenling.module.domain.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;

import com.google.common.base.Objects;
import com.whenling.module.domain.model.Node;
import com.whenling.module.domain.model.SortNoComparator;
import com.whenling.module.domain.model.Tree;
import com.whenling.module.domain.model.TreeEntity;
import com.whenling.module.domain.model.TreeImpl;

/**
 * 树形仓库实现类
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午7:04:25
 * @param <T>
 * @param <I>
 */
public class TreeRepositoryImpl<T extends TreeEntity<?, I, T>, I extends Serializable> extends BaseRepositoryImpl<T, I>
		implements TreeRepository<T, I> {

	public TreeRepositoryImpl(JpaEntityInformation<T, I> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
	}

	@Override
	public List<T> findRoots() {
		return getQuery(new ParentSpecification<T>(null), (Sort) null).getResultList();
	}

	@Override
	public List<T> findAllChildren(T current) {
		return getQuery(new SublevelSpecification<T>(current), (Sort) null).getResultList();
	}

	@Override
	public Tree<T> findTree(T current) {
		List<T> allChildren = current == null ? findAll() : findAllChildren(current);
		return toTree(current, allChildren);
	}

	@Override
	public Tree<T> toTree(T current, List<T> nodes) {
		Collections.sort(nodes, SortNoComparator.COMPARATOR);
		List<Node<T>> directSubordinates = findDirectSubordinates(current, nodes);
		if (current != null) {
			Node<T> root = toNode(current, directSubordinates);
			directSubordinates = new ArrayList<>();
			directSubordinates.add(root);
		}
		return new TreeImpl<>(directSubordinates);
	}

	protected List<Node<T>> findDirectSubordinates(T root, List<T> allChildren) {
		List<Node<T>> nodes = new ArrayList<>();
		for (T entity : allChildren) {
			if (Objects.equal(entity.getParent(), root)) {
				nodes.add(toNode(entity, findDirectSubordinates(entity, allChildren)));
			}
		}
		return nodes;
	}

	protected Node<T> toNode(T entity, List<Node<T>> children) {
		Node<T> node = new Node<>();
		node.setData(entity);
		node.setChildren(children);
		return node;
	}
}
