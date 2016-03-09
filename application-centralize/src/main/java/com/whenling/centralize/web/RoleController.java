package com.whenling.centralize.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Sets;
import com.whenling.centralize.model.Menu;
import com.whenling.centralize.model.Role;
import com.whenling.centralize.model.User;
import com.whenling.centralize.model.UserRole;
import com.whenling.centralize.service.MenuService;
import com.whenling.centralize.service.RoleService;
import com.whenling.centralize.service.UserRoleService;
import com.whenling.module.domain.model.Result;
import com.whenling.module.domain.model.Tree;
import com.whenling.module.web.controller.EntityController;

@Controller
@RequestMapping("/admin/role")
public class RoleController extends EntityController<Role, Long> {

	@Autowired
	private RoleService roleService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private UserRoleService userRoleService;

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

}
