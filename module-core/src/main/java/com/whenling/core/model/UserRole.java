package com.whenling.core.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.whenling.core.support.entity.BaseEntity;

@Entity
@Table(name = "sys_user_role")
public class UserRole extends BaseEntity<Long> {

	private static final long serialVersionUID = -2682709065132821870L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	private Role role;

	public UserRole() {
	}

	public UserRole(User user, Role role) {
		this.user = user;
		this.role = role;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
