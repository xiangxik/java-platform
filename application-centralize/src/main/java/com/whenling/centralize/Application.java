package com.whenling.centralize;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Objects;
import com.whenling.centralize.model.ExtensionEntity;
import com.whenling.centralize.model.ExtensionEntity.Type;
import com.whenling.centralize.model.Menu;
import com.whenling.centralize.model.Role;
import com.whenling.centralize.service.ExtensionService;
import com.whenling.centralize.service.MenuService;
import com.whenling.centralize.service.RoleService;
import com.whenling.centralize.service.UserService;
import com.whenling.module.domain.model.User;
import com.whenling.module.domain.model.User.Sex;

/**
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年2月6日 下午12:24:15
 */
@Component
public class Application {

	@Autowired
	private List<Extension> extensions;

	@Autowired
	private ExtensionService extensionService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@PostConstruct
	public void init() {
		ExtensionEntity app = extensionService.findApplication();
		boolean isNew = app == null;
		boolean isUpdate = isNew || !Objects.equal(getVersion(), app.getVersion());

		initApplication(isNew, isUpdate, isNew ? null : app.getVersion());

		if (isNew) {
			app = extensionService.newEntity();
			app.setCreatedBy(getAuthor());
			app.setCreatedDate(new Date());
			app.setType(Type.Application);
		}

		if (isUpdate) {
			app.setCode("main");
			app.setName("主应用");
			app.setVersion(getVersion());
			app.setLastModifiedBy(getAuthor());
			app.setLastModifiedDate(new Date());

			extensionService.save(app);
		}

		initExtension();
	}

	private void initApplication(boolean isNew, boolean isUpdate, Integer historyVersion) {

		if (isNew) {
			Role role = roleService.newEntity();
			role.setName("管理员");
			role.setCode("admin");
			role.markLocked();
			roleService.save(role);

			User superAdmin = userService.newEntity();
			superAdmin.setSuperAdmin(true);
			superAdmin.markLocked();
			superAdmin.setUsername("admin");
			userService.changePassword(superAdmin, null, "asd123");
			superAdmin.setEmail("ken@whenling.com");
			superAdmin.setMobile("13265323553");
			superAdmin.setName("管理员");
			superAdmin.setSex(Sex.male);

			userService.save(superAdmin);

			Menu systemMenu = addMenu("系统管理", "system", null, null, null, null);

			Menu personnelMenu = addMenu("企业管理", "enterprise", "Userhome", null, null, systemMenu);
			addMenu("用户列表", "user", "User", "app.view.user.UserList", null, personnelMenu);
			addMenu("角色列表", "role", "Userkey", "app.view.role.Role", null, personnelMenu);

			Menu siteMenu = addMenu("站点管理", "site", "Computer", null, null, systemMenu);
			addMenu("参数设置", "setting", "Cog", "app.view.setting.SettingForm", null, siteMenu);
			addMenu("模块列表", "module", "Applicationcascade", "app.view.module.ModuleList", null, siteMenu);
			addMenu("菜单管理", "menu", "Applicationsidelist", "app.view.menu.Menu", null, siteMenu);
			addMenu("操作日志", "log", "Databasego", "app.view.log.LogList", null, siteMenu);
		}
	}

	private void initExtension() {
		List<ExtensionEntity> moduleEntities = extensionService.findAllExtension();

		if (extensions != null) {
			for (Extension module : extensions) {
				ExtensionEntity moduleEntity = findModuleEntityByCode(moduleEntities, module.getCode());
				boolean isNew = moduleEntity == null;

				boolean isUpdate = isNew || !Objects.equal(module.getVersion(), moduleEntity.getVersion());

				module.init(this, isNew, isUpdate, isNew ? null : moduleEntity.getVersion());

				if (isNew) {
					moduleEntity = extensionService.newEntity();
					moduleEntity.setCreatedBy(module.getAuthor());
					moduleEntity.setCreatedDate(new Date());
					moduleEntity.setType(Type.Extension);
				}
				if (isUpdate) {
					moduleEntity.setCode(module.getCode());
					moduleEntity.setName(module.getName());
					moduleEntity.setVersion(module.getVersion());
					moduleEntity.setLastModifiedBy(module.getAuthor());
					moduleEntity.setLastModifiedDate(new Date());

					extensionService.save(moduleEntity);
				}
			}
		}
	}

	private ExtensionEntity findModuleEntityByCode(List<ExtensionEntity> moduleEntities, String code) {
		for (ExtensionEntity moduleEntity : moduleEntities) {
			if (Objects.equal(moduleEntity.getCode(), code)) {
				return moduleEntity;
			}
		}
		return null;
	}

	public List<Extension> getExtensions() {
		return extensions;
	}

	public Menu addMenu(String text, String code, String iconCls, String view, String config, Menu parent) {
		Menu menu = menuService.newEntity();
		menu.setText(text);
		menu.setCode(code);
		menu.setIconCls(iconCls);
		menu.setView(view);
		menu.setConfig(config);
		menu.setParent(parent);
		return menuService.save(menu);
	}

	public String getAuthor() {
		return "ken";
	}

	public Integer getVersion() {
		return 1;
	}

}
