package com.whenling.centralize.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whenling.centralize.model.Menu;
import com.whenling.module.domain.model.Tree;
import com.whenling.module.web.controller.TreeController;

@Controller
@RequestMapping("/admin/menu")
public class MenuController extends TreeController<Menu, Long> {

	@Override
	protected void onPostTree(Tree<Menu> tree) {
		tree.makeExpandAll();
	}
}
