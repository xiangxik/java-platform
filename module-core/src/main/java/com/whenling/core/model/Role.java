package com.whenling.core.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.whenling.core.support.entity.BizEntity;
import com.whenling.core.support.entity.Lockedable;

@Entity
@Table(name = "sys_role")
public class Role extends BizEntity<User, Long> implements Lockedable {

	private static final long serialVersionUID = 5532516952163494134L;

	@Column(nullable = false, length = 50)
	private String name;

	@Column(nullable = false, length = 50, unique = true)
	private String code;

	@Column(nullable = false)
	private boolean locked = false;

	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
	private Set<UserRole> userRoles = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "sys_role_menu")
	@OrderBy("sortNo asc")
	private Set<Menu> menus = new HashSet<>();

	@ElementCollection
	@CollectionTable(name = "sys_role_authority")
	private List<String> authorities = new ArrayList<String>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public boolean getLocked() {
		return locked;
	}

	@Override
	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	@Override
	public void markLocked() {
		this.locked = true;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public Set<Menu> getMenus() {
		return menus;
	}

	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}

	public List<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}

}
