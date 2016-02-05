package com.whenling.core.support.integration;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.whenling.core.support.menu.model.MenuSet;
import com.whenling.core.support.menu.service.MenuService;

@Component
public class Application {

	@Autowired
	private List<Module> modules;

	@Autowired
	private MenuService menuService;

	private List<MenuSet> menuSets = new ArrayList<>();

	@PostConstruct
	public void init() {

		if (modules != null) {
			for (Module module : modules) {
				module.init(this);
			}
		}
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

}
