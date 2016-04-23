package com.whenling.centralize.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.mysema.query.types.Predicate;
import com.whenling.centralize.model.Menu;
import com.whenling.centralize.model.QUser;
import com.whenling.centralize.model.Role;
import com.whenling.centralize.model.User;
import com.whenling.centralize.model.UserRole;
import com.whenling.centralize.service.MenuService;
import com.whenling.centralize.service.RoleService;
import com.whenling.centralize.service.UserRoleService;
import com.whenling.centralize.service.UserService;
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

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/authMenu", method = RequestMethod.GET)
	@ResponseBody
	public Tree<Menu> authMenu(@RequestParam(value = "id", required = false) Role role) {
		Tree<Menu> tree = menuService.findTree(null);
		tree.makeCheckable();
		tree.setChecked(role.getMenus());
		tree.makeExpandAll();
		return tree;
	}

	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	@ResponseBody
	public Result auth(@RequestParam(value = "id", required = false) Role role,
			@RequestParam(value = "menus") Menu[] menus) {
		role.setMenus(Sets.newHashSet(menus));
		roleService.save(role);
		return Result.success();
	}

	@RequestMapping(value = "/userSelect", method = RequestMethod.GET)
	@ResponseBody
	public Page<User> userSelect(@RequestParam(value = "id", required = false) Role role, Predicate predicate,
			Pageable pageable) {
		return userService.findAll(QUser.user.userRoles.any().role.eq(role).not().and(predicate), pageable);
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	@ResponseBody
	public Page<User> user(@RequestParam(value = "id", required = false) Role role, Predicate predicate,
			Pageable pageable) {
		return userService.findAll(QUser.user.userRoles.any().role.eq(role).and(predicate), pageable);
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

	@RequestMapping(value = "/userRemove", method = RequestMethod.POST)
	@ResponseBody
	public Result removeUser(@RequestParam(value = "id", required = false) Role role,
			@RequestParam(value = "users") User[] users) {
		if (users != null) {
			List<UserRole> userRoles = userRoleService.findByRole(role);
			for (User user : users) {
				Optional<UserRole> optional = Iterables.tryFind(userRoles,
						(userRole) -> Objects.equal(userRole.getUser(), user));
				if (optional.isPresent()) {
					userRoleService.delete(optional.get());
				}
			}
		}
		return Result.success();
	}

}
