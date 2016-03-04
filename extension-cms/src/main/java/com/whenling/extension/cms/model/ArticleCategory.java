package com.whenling.extension.cms.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.whenling.centralize.model.User;
import com.whenling.module.domain.model.TreeEntity;

/**
 * 文章分类
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月2日 下午4:17:19
 */
@Entity
@Table(name = "cms_article_category")
public class ArticleCategory extends TreeEntity<User, Long, ArticleCategory> {

	private static final long serialVersionUID = -3664321932012445026L;

	/** 名称 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String name;

	/** 页面标题 */
	@Length(max = 200)
	private String seoTitle;

	/** 页面关键词 */
	@Length(max = 200)
	private String seoKeywords;

	/** 页面描述 */
	@Length(max = 200)
	private String seoDescription;

	/** 文章 */
	@OneToMany(mappedBy = "articleCategory", fetch = FetchType.LAZY)
	private Set<Article> articles = new HashSet<Article>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSeoTitle() {
		return seoTitle;
	}

	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	public String getSeoKeywords() {
		return seoKeywords;
	}

	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}

	public String getSeoDescription() {
		return seoDescription;
	}

	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}

	public Set<Article> getArticles() {
		return articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

}
