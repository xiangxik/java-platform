package com.whenling.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whenling.core.support.entity.BizEntity;
import com.whenling.core.support.entity.Lockedable;

/**
 * 用户实体类
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年1月15日 下午4:38:24
 */
@Entity
@Table(name = "sys_user")
public class User extends BizEntity<User, Long> implements Lockedable {

	private static final long serialVersionUID = 2839091334861650994L;

	/** 账号 */
	@Column(length = 50)
	private String username;

	/** 密码 */
	@JsonIgnore
	@Column(length = 256, nullable = false)
	private String password;

	/** 邮箱 */
	@Column(length = 50)
	private String email;

	/** 手机 */
	@Column(length = 50)
	private String mobile;

	/** 姓名 */
	@Column(length = 50)
	private String name;

	/** 头像 */
	@Column(length = 200)
	private String avatar;

	/** 性别 */
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Sex sex;

	@Temporal(TemporalType.DATE)
	private Date birthday;

	@Column(nullable = false)
	private boolean superAdmin = false;

	@Column(nullable = false)
	private boolean locked = false;

	public enum Sex {
		/** 男 */
		male,

		/** 女 */
		female
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
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

	public boolean isSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(boolean superAdmin) {
		this.superAdmin = superAdmin;
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

}
