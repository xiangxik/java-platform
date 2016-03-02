package com.whenling.centralize;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

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

/**
 * 应用
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年2月6日 下午12:24:15
 */
@Component
public class Application implements ApplicationContextAware {

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

	public String getAuthor() {
		return "ken";
	}

	public Integer getVersion() {
		return 1;
	}

	private static ApplicationContext applicationContext;

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void autowireBean(Object existingBean) {
		getApplicationContext().getAutowireCapableBeanFactory().autowireBean(existingBean);
	}

	public static Object getBean(String name) {
		Assert.hasText(name);
		return applicationContext.getBean(name);
	}

	public static <T> T getBean(Class<T> type) {
		Assert.notNull(type);
		return applicationContext.getBean(type);
	}

	public static <T> T getBean(String name, Class<T> type) {
		Assert.hasText(name);
		Assert.notNull(type);
		return applicationContext.getBean(name, type);
	}

	public static <T extends Annotation, F> List<F> findAnnotatedBeans(Class<T> annotationType, Class<F> elementType) {
		List<F> beans = new ArrayList<>();
		for (String name : BeanFactoryUtils.beanNamesForTypeIncludingAncestors(applicationContext, Object.class)) {
			if (applicationContext.findAnnotationOnBean(name, annotationType) != null) {
				beans.add(applicationContext.getBean(name, elementType));
			}
		}

		return beans;
	}

	public static <T> List<T> findBeansByType(Class<T> beanType) {
		List<T> beans = new ArrayList<>();
		beans.addAll(applicationContext.getBeansOfType(beanType).values());

		return beans;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Application.applicationContext = applicationContext;
	}

}
