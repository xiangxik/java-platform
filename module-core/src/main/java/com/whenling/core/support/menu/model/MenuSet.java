package com.whenling.core.support.menu.model;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.whenling.core.support.entity.Resourceable;
import com.whenling.core.support.entity.Sortable;

public class MenuSet implements Sortable, Resourceable {

	@XStreamAsAttribute
	private String name;

	@XStreamAsAttribute
	private String code;

	@XStreamAsAttribute
	private Integer sortNo;

	@XStreamAsAttribute
	private List<MenuItem> menuItems = new ArrayList<MenuItem>();

	@Override
	public Integer getSortNo() {
		return sortNo;
	}

	@Override
	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<MenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}

}
