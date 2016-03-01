package com.whenling.extension.cms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.whenling.module.domain.model.BizEntity;
import com.whenling.module.domain.model.User;

@Entity
@Table(name = "sys_article")
public class Article extends BizEntity<User, Long> {

	private static final long serialVersionUID = 8004745715001630351L;

	@Column(length = 200)
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
