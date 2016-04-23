package com.whenling.module.domain.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

/**
 * 父级属性规格
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午7:03:33
 * @param <T>
 */
public class ParentSpecification<T> implements Specification<T> {

	private T parent;

	public ParentSpecification(T parent) {
		this.parent = parent;
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		return parent == null ? cb.isNull(root.get("parent")) : cb.equal(root.get("parent"), parent);
	}

}
