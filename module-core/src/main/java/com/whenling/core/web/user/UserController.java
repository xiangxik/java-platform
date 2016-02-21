package com.whenling.core.web.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.whenling.core.model.User;
import com.whenling.core.service.UserService;
import com.whenling.core.support.entity.Result;
import com.whenling.core.support.security.CurrentUser;
import com.whenling.core.web.user.vo.UserReference;
import com.whenling.core.web.user.vo.UserVo;

@Controller
@RequestMapping("/admin/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Page<User> list(Pageable pageable) {

		return userService.findAll(pageable);
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public User get(@RequestParam(value = "id", required = false) User user) {

		return user == null ? userService.newEntity() : user;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Result save(@ModelAttribute @Valid UserVo userVo, BindingResult bindingResult, String password,
			Model model) {
		if (userVo.isNew() && Strings.isNullOrEmpty(password)) {
			bindingResult.addError(new ObjectError("password", "密码不能为空"));
		}
		if (bindingResult.hasErrors()) {
			return Result.validateError(bindingResult.getAllErrors());
		}

		User user = userVo.getId() == null ? userService.newEntity() : userService.findOne(userVo.getId());
		userVo.applyTo(user);
		if (user.isNew()) {
			userService.changePassword(user, null, password);
		}
		userService.save(user);

		return Result.success();
	}

	@RequestMapping(value = "/reference", method = RequestMethod.GET)
	@ResponseBody
	public UserReference getReference(@CurrentUser(required = true) User user) {
		UserReference reference = new UserReference();
		reference.setMenuSets(userService.getMenuSets(user));
		return reference;
	}
}
