package com.whenling.core.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.whenling.core.model.Department;
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
		app.addNavMenuSetResource(new ClassPathResource("/com/whenling/core/support/menu-system.xml"));
		app.addNavMenuSetResource(new ClassPathResource("/com/whenling/core/support/menu-log.xml"));

		if (isUpdate) {
			Role role = roleService.newEntity();
			role.setName("管理员");
			role.setCode("admin");
			role.markLocked();
			roleService.save(role);
		}

		if (isNew) {
			Department company = departmentService.newEntity();
			company.setName("总公司");
			company.setCode("company");
			company.setSortNo(0);
			company = departmentService.save(company);

			User superAdmin = userService.newEntity();
			superAdmin.setSuperAdmin(true);
			superAdmin.markLocked();
			superAdmin.setUsername("admin");
			superAdmin.setPassword("asd123");
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
		return 1;
	}

	@Override
	public String getAuthor() {
		return "孔祥溪";
	}

}
