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
@Table(name = "xx_navigation")
public class Navigation extends SortEntity<User, Long> {

	private static final long serialVersionUID = -3446258081640040419L;

	/**
	 * 位置
	 */
	public enum Position {

		/** 顶部 */
		top,

		/** 中间 */
		middle,

		/** 底部 */
		bottom
	}

	/** 名称 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String name;

	/** 位置 */
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Position position;

	/** 链接地址 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String url;

	/** 是否新窗口打开 */
	@NotNull
	@Column(nullable = false)
	private Boolean isBlankTarget;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getIsBlankTarget() {
		return isBlankTarget;
	}

	public void setIsBlankTarget(Boolean isBlankTarget) {
		this.isBlankTarget = isBlankTarget;
	}
}
