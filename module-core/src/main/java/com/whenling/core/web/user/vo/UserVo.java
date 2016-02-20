package com.whenling.core.web.user.vo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.whenling.core.model.User;
import com.whenling.core.support.web.EntityVo;

public class UserVo extends EntityVo<Long> {

	@NotBlank
	@Length(max = 50)
	private String username;

	@NotBlank
	@Length(max = 50)
	private String name;

	private String mobile;
	private String email;

	public User applyTo(User user) {
		user.setUsername(username);
		user.setName(name);
		user.setMobile(mobile);
		user.setEmail(email);
		return user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
