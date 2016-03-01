package com.whenling.centralize.web.user.vo;

import java.util.List;

import com.whenling.centralize.web.menu.vo.MenuVo;

/**
 * 用户引用
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午4:33:02
 */
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
