package com.whenling.core.support.menu.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;
import com.whenling.core.support.menu.model.MenuItem;
import com.whenling.core.support.menu.model.MenuSet;

@Component
public class MenuService {

	public MenuSet loadMenuSetResource(Resource resource) {
		XStream stream = new XStream();
		stream.alias("menu-set", MenuSet.class);
		stream.alias("menu-item", MenuItem.class);

		stream.processAnnotations(MenuSet.class);
		stream.processAnnotations(MenuItem.class);

		MenuSet menuSet = null;
		try {
			menuSet = (MenuSet) stream.fromXML(resource.getInputStream());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return menuSet;
	}

}
