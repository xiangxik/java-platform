package com.whenling.core.web.menu.vo;

import java.util.List;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.whenling.core.model.Menu;
import com.whenling.core.support.extjs.api.data.NodeInterface;

public class MenuNode extends NodeInterface<MenuNode> {

	private String code;
	private String iconCls;
	private String view;
	private String config;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public static MenuNode convert(Menu menu) {
		MenuNode node = new MenuNode();
		node.setText(menu.getText());
		node.setCode(menu.getCode());
		node.setIconCls(menu.getIconCls());
		node.setView(menu.getView());
		node.setConfig(menu.getConfig());
		node.setChildren(converters(menu.getChildren()));
		return node;
	}

	public static MenuNode[] converters(List<Menu> menus) {
		if (menus == null || menus.size() == 0) {
			return null;
		}
		return Iterables.toArray(Lists.transform(menus, MenuNode::convert), MenuNode.class);
	}

}
