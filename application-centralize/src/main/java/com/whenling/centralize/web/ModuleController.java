package com.whenling.centralize.web;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whenling.centralize.model.ExtensionEntity;
import com.whenling.module.domain.model.Result;
import com.whenling.module.web.controller.EntityController;

@Controller
@RequestMapping("/admin/module")
public class ModuleController extends EntityController<ExtensionEntity, Long> {

	@Override
	public Result save(ExtensionEntity entity, BindingResult bindingResult) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Result delete(ExtensionEntity entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Result batchDelete(ExtensionEntity[] entities) {
		throw new UnsupportedOperationException();
	}

}
