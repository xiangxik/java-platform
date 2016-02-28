package com.whenling.core.support.repo;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

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
