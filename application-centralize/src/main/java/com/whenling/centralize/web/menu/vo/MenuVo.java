package com.whenling.centralize.web.menu.vo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.whenling.centralize.model.Menu;
import com.whenling.module.domain.model.EntityVo;

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

	public Menu applyTo(Menu menu) {
		menu.setText(text);
		menu.setCode(code);
		menu.setIconCls(iconCls);
		menu.setView(view);
		menu.setConfig(config);
		return menu;
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
