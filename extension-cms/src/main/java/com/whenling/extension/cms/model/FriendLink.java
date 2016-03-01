package com.whenling.extension.cms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.whenling.centralize.model.User;
import com.whenling.module.domain.model.SortEntity;

@Entity
@Table(name = "xx_friend_link")
public class FriendLink extends SortEntity<User, Long> {

	private static final long serialVersionUID = 3564806473801955355L;

	/**
	 * 类型
	 */
	public enum Type {

		/** 文本 */
		text,

		/** 图片 */
		image
	}

	/** 名称 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String name;

	/** 类型 */
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Type type;

	/** logo */
	@Length(max = 200)
	private String logo;

	/** 链接地址 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
