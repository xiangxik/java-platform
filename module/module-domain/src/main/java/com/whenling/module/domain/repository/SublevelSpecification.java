package com.whenling.module.domain.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.whenling.module.domain.model.TreeEntity;

/**
 * 子级规格
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午7:03:58
 * @param <T>
 */
public class SublevelSpecification<T extends TreeEntity<?, ?, T>> implements Specification<T> {

	private T current;

	public SublevelSpecification(T current) {
		this.current = current;
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		String rootPath = current.getTreePath();
		return cb.like(root.get("treePath"), rootPath + "%");
	}

}
