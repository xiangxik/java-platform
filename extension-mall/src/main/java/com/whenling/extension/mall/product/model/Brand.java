package com.whenling.extension.mall.product.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.whenling.centralize.model.User;
import com.whenling.extension.mall.market.Promotion;
import com.whenling.module.domain.model.SortEntity;

@Entity
@Table(name = "mall_brand")
public class Brand extends SortEntity<User, Long> {

	private static final long serialVersionUID = -8108458656733965530L;

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
	@Column(length = 200, nullable = false)
	private String name;

	/** 类型 */
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Type type;

	/** logo */
	@Length(max = 200)
	@Column(length = 200)
	private String logo;

	/** 网址 */
	@Length(max = 200)
	@Column(length = 200)
	private String url;

	/** 介绍 */
	@Lob
	private String introduction;

	/** 商品 */
	@OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
	private Set<Product> products = new HashSet<Product>();

	/** 商品分类 */
	@ManyToMany(mappedBy = "brands", fetch = FetchType.LAZY)
	@OrderBy("sortNo asc")
	private Set<ProductCategory> productCategories = new HashSet<ProductCategory>();

	/** 促销 */
	@ManyToMany(mappedBy = "brands", fetch = FetchType.LAZY)
	private Set<Promotion> promotions = new HashSet<Promotion>();

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

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public Set<ProductCategory> getProductCategories() {
		return productCategories;
	}

	public void setProductCategories(Set<ProductCategory> productCategories) {
		this.productCategories = productCategories;
	}

	public Set<Promotion> getPromotions() {
		return promotions;
	}

	public void setPromotions(Set<Promotion> promotions) {
		this.promotions = promotions;
	}

	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {
		Set<Product> products = getProducts();
		if (products != null) {
			for (Product product : products) {
				product.setBrand(null);
			}
		}
		Set<ProductCategory> productCategories = getProductCategories();
		if (productCategories != null) {
			for (ProductCategory productCategory : productCategories) {
				productCategory.getBrands().remove(this);
			}
		}
		Set<Promotion> promotions = getPromotions();
		if (promotions != null) {
			for (Promotion promotion : promotions) {
				promotion.getBrands().remove(this);
			}
		}
	}
}
