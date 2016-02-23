package com.whenling.core.support.integration;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Objects;
import com.whenling.core.model.Menu;
import com.whenling.core.model.ModuleEntity;
import com.whenling.core.service.MenuService;
import com.whenling.core.service.ModuleService;

/**
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年2月6日 下午12:24:15
 */
@Component
public class Application {

	@Autowired
	private List<Module> modules;

	@Autowired
	private ModuleService moduleEntityService;

	@Autowired
	private MenuService menuService;

	@PostConstruct
	public void init() {

		List<ModuleEntity> moduleEntities = moduleEntityService.findAll();

		if (modules != null) {
			for (Module module : modules) {
				ModuleEntity moduleEntity = findModuleEntityByCode(moduleEntities, module.getCode());
				boolean isNew = moduleEntity == null;

				boolean isUpdate = isNew || !Objects.equal(module.getVersion(), moduleEntity.getVersion());

				module.init(this, isNew, isUpdate);

				if (isNew) {
					moduleEntity = moduleEntityService.newEntity();
					moduleEntity.setCreatedBy(module.getAuthor());
					moduleEntity.setCreatedDate(new Date());
				}
				if (isUpdate) {
					moduleEntity.setCode(module.getCode());
					moduleEntity.setName(module.getName());
					moduleEntity.setVersion(module.getVersion());
					moduleEntity.setLastModifiedBy(module.getAuthor());
					moduleEntity.setLastModifiedDate(new Date());

					moduleEntityService.save(moduleEntity);
				}
			}
		}
	}

	private ModuleEntity findModuleEntityByCode(List<ModuleEntity> moduleEntities, String code) {
		for (ModuleEntity moduleEntity : moduleEntities) {
			if (Objects.equal(moduleEntity.getCode(), code)) {
				return moduleEntity;
			}
		}
		return null;
	}

	public List<Module> getModules() {
		return modules;
	}

	public Menu addMenu(String name, String code, String iconCls, String view, String config, Menu parent) {
		Menu menu = menuService.newEntity();
		menu.setName(name);
		menu.setCode(code);
		menu.setIconCls(iconCls);
		menu.setView(view);
		menu.setConfig(config);
		menu.setParent(parent);
		return menuService.save(menu);
	}

	public Integer getVersion() {
		return 1;
	}

}
