package com.whenling.core.support.integration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.google.common.base.Objects;
import com.whenling.core.model.ModuleEntity;
import com.whenling.core.service.ModuleEntityService;
import com.whenling.core.support.integration.menu.MenuService;
import com.whenling.core.support.integration.menu.MenuSet;

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
	private MenuService menuService;

	@Autowired
	private ModuleEntityService moduleEntityService;

	private List<MenuSet> menuSets = new ArrayList<>();

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

	public void addNavMenuSetResource(Resource resource) {
		MenuSet menuSet = menuService.loadMenuSetResource(resource);
		menuSets.add(menuSet);
	}

	public List<MenuSet> getMenuSets() {
		return menuSets;
	}

	public Integer getVersion() {
		return 1;
	}

}
