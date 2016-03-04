package com.whenling.module.web.controller;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whenling.module.domain.model.BaseEntity;
import com.whenling.module.domain.service.BaseService;

public abstract class EntityController<T extends BaseEntity<I>, I extends Serializable> extends BaseController {

	@Autowired
	private BaseService<T, I> baseService;

	@Autowired
	public void setBaseService(BaseService<T, I> baseService) {
		this.baseService = baseService;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Page<T> list(Pageable pageable) {

		return baseService.findAll(pageable);
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public T get(@RequestParam(value = "id", required = false) T entity) {

		return entity == null ? baseService.newEntity() : entity;
	}

}
