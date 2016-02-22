package com.whenling.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.whenling.core.model.Department;
import com.whenling.core.model.Menu;
import com.whenling.core.model.Role;
import com.whenling.core.model.User;
import com.whenling.core.model.User.Sex;
import com.whenling.core.service.DepartmentService;
import com.whenling.core.service.RoleService;
import com.whenling.core.service.UserService;
import com.whenling.core.support.integration.Application;
import com.whenling.core.support.integration.Module;

@Component
public class CoreModule extends Module {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private DepartmentService departmentService;

	@Override
	public void init(Application app, boolean isNew, boolean isUpdate) {
		if (isUpdate) {

			Menu systemMenu = app.addMenu("系统管理", "system", null, null, null, null);

			Menu personnelMenu = app.addMenu("人员管理", "personnel", "Userhome", null, null, systemMenu);
			app.addMenu("用户列表", "user", "User", "app.view.user.UserList", null, personnelMenu);
			app.addMenu("角色列表", "role", "Userkey", "app.view.role.RoleList", null, personnelMenu);

			Menu siteMenu = app.addMenu("站点管理", "site", "Computer", null, null, systemMenu);
			app.addMenu("参数设置", "setting", "Cog", "app.view.setting.SettingForm", null, siteMenu);
			app.addMenu("模块列表", "module", "Applicationcascade", "app.view.module.ModuleList", null, siteMenu);
			app.addMenu("模板管理", "template", "Page", "app.view.cms.Template", null, siteMenu);
			app.addMenu("操作日志", "log", "Databasego", "app.view.log.LogList", null, siteMenu);

		}

		if (isNew) {

			Role role = roleService.newEntity();
			role.setName("管理员");
			role.setCode("admin");
			role.markLocked();
			roleService.save(role);

			Department company = departmentService.newEntity();
			company.setName("总公司");
			company.setCode("company");
			company.setSortNo(0);
			company = departmentService.save(company);

			User superAdmin = userService.newEntity();
			superAdmin.setSuperAdmin(true);
			superAdmin.markLocked();
			superAdmin.setUsername("admin");
			userService.changePassword(superAdmin, null, "asd123");
			superAdmin.setEmail("ken@whenling.com");
			superAdmin.setMobile("13265323553");
			superAdmin.setName("管理员");
			superAdmin.setSex(Sex.male);
			superAdmin.setDepartment(company);

			userService.save(superAdmin);
		}
	}

	@Override
	public Integer getVersion() {
		return 2;
	}

	@Override
	public String getAuthor() {
		return "孔祥溪";
	}

}
