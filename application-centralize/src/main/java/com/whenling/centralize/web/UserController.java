package com.whenling.centralize.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.whenling.centralize.model.User;
import com.whenling.centralize.service.MenuService;
import com.whenling.centralize.service.UserService;
import com.whenling.centralize.support.web.CurrentUser;
import com.whenling.module.domain.model.Result;
import com.whenling.module.web.controller.EntityController;

@Controller
@RequestMapping("/admin/user")
public class UserController extends EntityController<User, Long> {

	@Autowired
	private UserService userService;

	@Autowired
	private MenuService menuService;

	@Override
	public Result save(User entity, BindingResult bindingResult) {
		throw new UnsupportedOperationException();
	}

	@RequestMapping(value = "/saveBy", method = RequestMethod.POST)
	@ResponseBody
	public Result saveBy(@ModelAttribute @Valid User user, BindingResult bindingResult, String password) {
		if (user.isNew() && Strings.isNullOrEmpty(password)) {
			bindingResult.addError(new ObjectError("password", "密码不能为空"));
		}
		if (bindingResult.hasErrors()) {
			return Result.validateError(bindingResult.getAllErrors());
		}

		if (user.isNew()) {
			userService.changePassword(user, null, password);
		}
		userService.save(user);

		return Result.success();
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	@ResponseBody
	public Result changePassword(@RequestParam(value = "id") User user, String oldPassword, String newPassword,
			String repeatPassword) {
		Assert.notNull(user);
		Assert.isTrue(!Strings.isNullOrEmpty(oldPassword));
		Assert.isTrue(!Strings.isNullOrEmpty(newPassword));
		Assert.isTrue(!Strings.isNullOrEmpty(repeatPassword));

		Assert.isTrue(Objects.equal(newPassword, repeatPassword));

		userService.changePassword(user, oldPassword, newPassword);
		userService.save(user);

		return Result.success();
	}

	@RequestMapping(value = "/reference", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getReference(@CurrentUser(required = true) User user) {
		Map<String, Object> reference = new HashMap<>();
		reference.put("menus", menuService.findTree(null));
		reference.put("user", user);
		return reference;
	}

}
