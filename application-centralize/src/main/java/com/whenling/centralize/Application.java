package com.whenling.centralize;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.google.common.base.Objects;
import com.whenling.centralize.model.ExtensionEntity;
import com.whenling.centralize.model.ExtensionEntity.Type;
import com.whenling.centralize.model.Menu;
import com.whenling.centralize.model.Role;
import com.whenling.centralize.model.User;
import com.whenling.centralize.model.User.Sex;
import com.whenling.centralize.service.ExtensionService;
import com.whenling.centralize.service.MenuService;
import com.whenling.centralize.service.RoleService;
import com.whenling.centralize.service.UserService;
import com.whenling.centralize.support.config.ConfigModel;

/**
 * 应用
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年2月6日 下午12:24:15
 */
@Configuration
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class Application {

	@Autowired(required = false)
	private List<Extension> extensions;

	@Autowired
	private ExtensionService extensionService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	private final static Map<String, Filter> securityFilters = new HashMap<>();
	private final static Map<String, String> securityFilterChainDefinitionMap = new HashMap<>();
	private final static List<Filter> filters = new ArrayList<>();

	private List<ConfigModel> configurations = new ArrayList<>();

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

			app.setCode("main");
			app.setName("主应用");
			app.setVersion(getVersion());
			app.setLastModifiedBy(getAuthor());
			app.setLastModifiedDate(new Date());

			extensionService.save(app);
		}

		if (isUpdate) {

		}

		addSetting("mainConfig", "系统名称", MainSetting.KEY_NAME, "通用后台管理系统");
		addSetting("mainConfig", "公司名称", MainSetting.KEY_COMPANY, "广州当凌信息科技有限公司");
		addSetting("mainConfig", "系统版本", MainSetting.KEY_VERSION, "1.0");
		addSetting("mainConfig", "网站地址", MainSetting.KEY_SITEURL, "http://www.whenling.com");

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

			Menu systemMenu = addMenu("系统管理", "system", "Computer", null, null, null);

			Menu personnelMenu = addMenu("企业管理", "enterprise", "Userhome", null, null, systemMenu);
			addMenu("用户列表", "user", "User", "app.view.user.UserList", null, personnelMenu);
			addMenu("角色列表", "role", "Userkey", "app.view.role.RoleList", null, personnelMenu);
			addMenu("菜单管理", "menu", "Applicationsidelist", "app.view.menu.MenuList", null, personnelMenu);

			Menu siteMenu = addMenu("站点管理", "site", "Computer", null, null, systemMenu);
			addMenu("参数设置", "setting", "Cog", "app.view.setting.SettingForm", null, siteMenu);
			addMenu("模块列表", "module", "Applicationcascade", "app.view.module.ModuleList", null, siteMenu);
			addMenu("操作日志", "log", "Databasego", "app.view.log.LogList", null, siteMenu);
		}
	}

	private void initExtension() {
		if (extensions != null) {
			List<ExtensionEntity> moduleEntities = extensionService.findAllExtension();
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

	public Menu findMenuByCode(String code) {
		return menuService.findByCode(code);
	}

	public void addSetting(String configName, String display, String key, Object defaultValue) {
		configurations.add(new ConfigModel(configName, display, key, defaultValue));
	}

	public List<ConfigModel> getSettings() {
		return this.configurations;
	}

	public String getAuthor() {
		return "ken";
	}

	public Integer getVersion() {
		return 2;
	}

	public static List<Filter> getFilters() {
		return filters;
	}

	public static Map<String, Filter> getSecurityfilters() {
		return securityFilters;
	}

	public static Map<String, String> getSecurityFilterChainDefinitionMap() {
		return securityFilterChainDefinitionMap;
	}

}
