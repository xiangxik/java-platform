package com.whenling.module.web.controller;

import java.io.Serializable;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whenling.module.domain.model.BaseEntity;
import com.whenling.module.domain.model.Result;
import com.whenling.module.domain.service.BaseService;

public abstract class EntityController<T extends BaseEntity<I>, I extends Serializable> extends BaseController {

	protected BaseService<T, I> baseService;

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

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Result save(@ModelAttribute @Valid T entity, BindingResult bindingResult) {

		onBeforeValidate(entity, bindingResult);
		if (bindingResult.hasErrors()) {
			return Result.validateError(bindingResult.getAllErrors());
		}

		onBeforeSave(entity);
		baseService.save(entity);
		onAfterSave(entity);

		return Result.success();
	}

	protected void onBeforeValidate(T entity, BindingResult bindingResult) {
	}

	protected void onBeforeSave(T entity) {
	}

	protected void onAfterSave(T entity) {
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "id")
	@ResponseBody
	public Result delete(@RequestParam(value = "id") T entity) {
		baseService.delete(entity);
		return Result.success();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "ids")
	@ResponseBody
	public Result batchDelete(@RequestParam(value = "ids") T[] entities) {
		for (T entity : entities) {
			baseService.delete(entity);
		}

		return Result.success();
	}

}
