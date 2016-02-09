package com.whenling.core.web.role;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whenling.core.model.Role;
import com.whenling.core.service.RoleService;
import com.whenling.core.support.entity.Result;
import com.whenling.core.web.role.vo.RoleVo;

@Controller
@RequestMapping("/admin/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Page<Role> list(Pageable pageable) {

		return roleService.findAll(pageable);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Result save(@ModelAttribute("role") @Valid RoleVo roleVo, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return Result.validateFailure(bindingResult.getAllErrors());
		}

		Role role = roleVo.getId() == null ? roleService.newEntity() : roleService.findOne(roleVo.getId());
		roleVo.applyTo(role);
		roleService.save(role);

		return Result.success();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "id")
	@ResponseBody
	public Result delete(@RequestParam(value = "id") Role role) {
		roleService.delete(role);
		return Result.success();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "ids")
	@ResponseBody
	public Result batchDelete(@RequestParam(value = "ids") Role[] roles) {
		for (Role role : roles) {
			roleService.delete(role);
		}

		return Result.success();
	}
}
