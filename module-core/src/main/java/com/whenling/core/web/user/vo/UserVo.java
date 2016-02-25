package com.whenling.core.web.user.vo;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.whenling.core.model.User;
import com.whenling.core.model.User.Sex;
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

	private Sex sex;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;

	public User applyTo(User user) {
		user.setUsername(username);
		user.setName(name);
		user.setMobile(mobile);
		user.setEmail(email);
		user.setSex(sex);
		user.setBirthday(birthday);
		return user;
	}

	public static UserVo convert(User user) {
		UserVo vo = new UserVo();
		vo.setId(user.getId());
		vo.setEmail(user.getEmail());
		vo.setMobile(user.getMobile());
		vo.setName(user.getName());
		vo.setUsername(user.getUsername());
		vo.setSex(user.getSex());
		vo.setBirthday(user.getBirthday());
		return vo;
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

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

}
