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

import com.google.common.collect.Sets;
import com.whenling.core.model.Menu;
import com.whenling.core.model.Role;
import com.whenling.core.model.User;
import com.whenling.core.model.UserRole;
import com.whenling.core.service.MenuService;
import com.whenling.core.service.RoleService;
import com.whenling.core.service.UserRoleService;
import com.whenling.core.support.entity.Result;
import com.whenling.core.support.entity.Tree;
import com.whenling.core.web.role.vo.RoleVo;

@Controller
@RequestMapping("/admin/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private UserRoleService userRoleService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Page<Role> list(Pageable pageable) {

		return roleService.findAll(pageable);
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public Role get(@RequestParam(value = "id", required = false) Role role) {

		return role == null ? roleService.newEntity() : role;
	}

	@RequestMapping(value = "/menuTree", method = RequestMethod.GET)
	@ResponseBody
	public Tree<Menu> getMenuTree(@RequestParam(value = "id", required = false) Role role) {
		Tree<Menu> tree = menuService.findTree(null);
		tree.makeCheckable();
		tree.setChecked(role.getMenus());
		return tree;
	}

	@RequestMapping(value = "/menuTree", method = RequestMethod.POST)
	@ResponseBody
	public Result postMenuTree(@RequestParam(value = "id", required = false) Role role,
			@RequestParam(value = "menus") Menu[] menus) {
		role.setMenus(Sets.newHashSet(menus));
		roleService.save(role);
		return Result.success();
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	@ResponseBody
	public Page<User> user(@RequestParam(value = "id", required = false) Role role, Pageable pageable) {
		return userRoleService.findByRole(role, pageable);
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	@ResponseBody
	public Result postUser(@RequestParam(value = "id", required = false) Role role,
			@RequestParam(value = "users") User[] users) {
		if (users != null) {
			for (User user : users) {
				UserRole userRole = userRoleService.newEntity();
				userRole.setUser(user);
				userRole.setRole(role);
				userRoleService.save(userRole);
			}
		}
		return Result.success();
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Result save(@ModelAttribute @Valid RoleVo roleVo, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return Result.validateError(bindingResult.getAllErrors());
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
