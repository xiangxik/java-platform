package com.whenling.core.support.repo;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.whenling.core.support.entity.TreeEntity;

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
