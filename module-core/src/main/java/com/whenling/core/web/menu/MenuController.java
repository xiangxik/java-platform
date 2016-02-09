package com.whenling.core.web.menu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whenling.core.support.integration.Application;
import com.whenling.core.support.integration.menu.MenuSet;

@Controller
@RequestMapping("/admin/menu")
public class MenuController {

	@Autowired
	private Application app;

	@RequestMapping(value = "/nav", method = RequestMethod.GET)
	@ResponseBody
	public List<MenuSet> getNavMenuItem() {

		return app.getMenuSets();
	}

}
