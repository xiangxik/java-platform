package com.whenling.centralize.web.user.vo;

import java.util.List;

import com.whenling.centralize.web.menu.vo.MenuVo;

public class UserReference {

	private List<MenuVo> menus;

	private UserVo user;

	public List<MenuVo> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuVo> menus) {
		this.menus = menus;
	}

	public UserVo getUser() {
		return user;
	}

	public void setUser(UserVo user) {
		this.user = user;
	}

}
