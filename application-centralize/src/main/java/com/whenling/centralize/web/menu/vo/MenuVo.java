package com.whenling.centralize.web.menu.vo;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.google.common.collect.Lists;
import com.whenling.centralize.model.Menu;
import com.whenling.module.domain.model.EntityVo;

/**
 * 菜单值对象
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午4:30:52
 */
public class MenuVo extends EntityVo<Long> {

	@NotBlank
	@Length(max = 50)
	private String text;

	@NotBlank
	@Length(max = 50)
	private String code;

	private String iconCls;
	private String view;
	private String config;

	private List<MenuVo> children;

	public Boolean getLeaf() {
		List<MenuVo> children = getChildren();
		return children == null || children.size() == 0;
	}

	public Menu applyTo(Menu menu) {
		menu.setText(text);
		menu.setCode(code);
		menu.setIconCls(iconCls);
		menu.setView(view);
		menu.setConfig(config);
		return menu;
	}

	public static MenuVo convert(Menu menu) {
		MenuVo menuVo = new MenuVo();
		menuVo.setId(menu.getId());
		menuVo.setText(menu.getText());
		menuVo.setCode(menu.getCode());
		menuVo.setConfig(menu.getConfig());
		menuVo.setIconCls(menu.getIconCls());
		menuVo.setView(menu.getView());

		List<Menu> children = menu.getChildren();
		if (children != null && children.size() > 0) {
			menuVo.setChildren(Lists.transform(children, MenuVo::convert));
		}
		return menuVo;
	}

	public List<MenuVo> getChildren() {
		return children;
	}

	public void setChildren(List<MenuVo> children) {
		this.children = children;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

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

}
