package com.whenling.extension.cms.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.whenling.centralize.model.User;
import com.whenling.module.domain.model.SortEntity;

@Entity
@Table(name = "cms_article_tag")
public class ArticleTag extends SortEntity<User, Long> {

	private static final long serialVersionUID = 1L;

	/** 名称 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String name;

	/** 图标 */
	@Length(max = 200)
	private String icon;

	/** 备注 */
	@Length(max = 200)
	private String memo;

	/** 文章 */
	private Set<Article> articles = new HashSet<Article>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Set<Article> getArticles() {
		return articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {
		Set<Article> articles = getArticles();
		if (articles != null) {
			for (Article article : articles) {
				article.getTags().remove(this);
			}
		}
	}

}
