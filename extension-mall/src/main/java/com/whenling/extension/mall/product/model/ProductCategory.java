package com.whenling.extension.mall.product.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.whenling.centralize.model.User;
import com.whenling.extension.mall.market.Promotion;
import com.whenling.module.domain.model.TreeEntity;

public class ProductCategory extends TreeEntity<User, Long, ProductCategory> {

	private static final long serialVersionUID = 2865111397278035659L;
	/** 名称 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false, length = 200)
	private String name;

	/** 页面标题 */
	@Length(max = 200)
	@Column(length = 200)
	private String seoTitle;

	/** 页面关键词 */
	@Length(max = 200)
	@Column(length = 200)
	private String seoKeywords;

	/** 页面描述 */
	@Length(max = 200)
	@Column(length = 200)
	private String seoDescription;

	/** 商品 */
	@OneToMany(mappedBy = "productCategory", fetch = FetchType.LAZY)
	private Set<Product> products = new HashSet<Product>();

	/** 筛选品牌 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "xx_product_category_brand")
	@OrderBy("sortNo asc")
	private Set<Brand> brands = new HashSet<Brand>();

	/** 参数组 */
	@OneToMany(mappedBy = "productCategory", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("sortNo asc")
	private Set<ParameterGroup> parameterGroups = new HashSet<ParameterGroup>();

	/** 筛选属性 */
	@OneToMany(mappedBy = "productCategory", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("sortNo asc")
	private Set<Attribute> attributes = new HashSet<Attribute>();

	/** 促销 */
	@ManyToMany(mappedBy = "productCategories", fetch = FetchType.LAZY)
	private Set<Promotion> promotions = new HashSet<Promotion>();

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

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public Set<Brand> getBrands() {
		return brands;
	}

	public void setBrands(Set<Brand> brands) {
		this.brands = brands;
	}

	public Set<ParameterGroup> getParameterGroups() {
		return parameterGroups;
	}

	public void setParameterGroups(Set<ParameterGroup> parameterGroups) {
		this.parameterGroups = parameterGroups;
	}

	public Set<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<Attribute> attributes) {
		this.attributes = attributes;
	}

	public Set<Promotion> getPromotions() {
		return promotions;
	}

	public void setPromotions(Set<Promotion> promotions) {
		this.promotions = promotions;
	}

}
