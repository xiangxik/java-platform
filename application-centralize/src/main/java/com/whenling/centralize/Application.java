package com.whenling.centralize;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Objects;
import com.whenling.centralize.model.ExtensionEntity;
import com.whenling.centralize.model.Menu;
import com.whenling.centralize.service.ExtensionService;
import com.whenling.centralize.service.MenuService;

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

	@PostConstruct
	public void init() {

		List<ExtensionEntity> moduleEntities = extensionService.findAll();

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

	public Integer getVersion() {
		return 1;
	}

}
