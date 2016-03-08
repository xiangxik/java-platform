package com.whenling.module.web.view;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import com.whenling.module.domain.model.BaseEntity;
import com.whenling.module.domain.service.BaseService;

public abstract class EntityFunctionPackage<T extends BaseEntity<I>, I extends Serializable>
		implements FunctionPackage {

	protected BaseService<T, I> baseService;

	@Autowired
	public void setBaseService(BaseService<T, I> baseService) {
		this.baseService = baseService;
	}

	public T findOne(I id) {
		return baseService.findOne(id);
	}

	public T getOne(I id) {
		return baseService.getOne(id);
	}

	public boolean exists(I id) {
		return baseService.exists(id);
	}

	public long count() {
		return baseService.count();
	}

	public List<T> findAll() {
		return baseService.findAll();
	}

	public List<T> findAll(Iterable<I> ids) {
		return baseService.findAll(ids);
	}

	public List<T> findAll(Sort sort) {
		return baseService.findAll(sort);
	}

	public Page<T> findAll(Pageable pageable) {
		return baseService.findAll(pageable);
	}

	public T findOne(Predicate predicate) {
		return baseService.findOne(predicate);
	}

	public Iterable<T> findAll(Predicate predicate) {
		return baseService.findAll(predicate);
	}

	public Iterable<T> findAll(Predicate predicate, Sort sort) {
		return baseService.findAll(predicate, sort);
	}

	public Iterable<T> findAll(Predicate predicate, OrderSpecifier<?>... orders) {
		return baseService.findAll(predicate, orders);
	}

	public Iterable<T> findAll(OrderSpecifier<?>... orders) {
		return baseService.findAll(orders);
	}

	public Page<T> findAll(Predicate predicate, Pageable pageable) {
		return baseService.findAll(predicate, pageable);
	}

	public long count(Predicate predicate) {
		return baseService.count(predicate);
	}

	public boolean exists(Predicate predicate) {
		return baseService.exists(predicate);
	}

}
