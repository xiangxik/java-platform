package com.whenling.centralize.web.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.google.common.collect.Lists;
import com.whenling.centralize.model.User;
import com.whenling.centralize.service.UserService;
import com.whenling.centralize.support.web.CurrentUser;
import com.whenling.centralize.web.menu.vo.MenuVo;
import com.whenling.centralize.web.user.vo.UserReference;
import com.whenling.centralize.web.user.vo.UserVo;
import com.whenling.module.domain.model.Result;

/**
 * 用户控制器
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午4:33:18
 */
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

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "id")
	@ResponseBody
	public Result delete(@RequestParam(value = "id") User user) {
		userService.delete(user);
		return Result.success();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "ids")
	@ResponseBody
	public Result batchDelete(@RequestParam(value = "ids") User[] users) {
		for (User user : users) {
			userService.delete(user);
		}

		return Result.success();
	}

	@RequestMapping(value = "/reference", method = RequestMethod.GET)
	@ResponseBody
	public UserReference getReference(@CurrentUser(required = true) User user) {
		UserReference reference = new UserReference();
		reference.setMenus(Lists.transform(userService.getMenus(user), MenuVo::convert));
		reference.setUser(UserVo.convert(user));
		return reference;
	}
}
