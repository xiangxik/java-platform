package com.whenling.extension.mall.market;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.whenling.centralize.model.User;
import com.whenling.extension.mall.product.model.Brand;
import com.whenling.extension.mall.product.model.GiftItem;
import com.whenling.extension.mall.product.model.Product;
import com.whenling.extension.mall.product.model.ProductCategory;
import com.whenling.module.domain.model.SortEntity;

@Entity
@Table(name = "mall_promotion")
public class Promotion extends SortEntity<User, Long> {

	private static final long serialVersionUID = -4087637623597971465L;

	/** 名称 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String name;

	/** 标题 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String title;

	/** 起始日期 */
	private Date beginDate;

	/** 结束日期 */
	private Date endDate;

	/** 最小商品数量 */
	@Min(0)
	private Integer minimumQuantity;

	/** 最大商品数量 */
	@Min(0)
	private Integer maximumQuantity;

	/** 最小商品价格 */
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(precision = 21, scale = 6)
	private BigDecimal minimumPrice;

	/** 最大商品价格 */
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(precision = 21, scale = 6)
	private BigDecimal maximumPrice;

	/** 价格运算表达式 */
	private String priceExpression;

	/** 积分运算表达式 */
	private String pointExpression;

	/** 是否免运费 */
	@NotNull
	@Column(nullable = false)
	private Boolean isFreeShipping;

	/** 是否允许使用优惠券 */
	@NotNull
	@Column(nullable = false)
	private Boolean isCouponAllowed;

	/** 介绍 */
	@Lob
	private String introduction;

	/** 允许参加会员等级 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "mall_promotion_member_rank")
	private Set<MemberRank> memberRanks = new HashSet<MemberRank>();

	/** 允许参与商品分类 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "mall_promotion_product_category")
	private Set<ProductCategory> productCategories = new HashSet<ProductCategory>();

	/** 允许参与商品 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "mall_promotion_product")
	private Set<Product> products = new HashSet<Product>();

	/** 允许参与品牌 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "mall_promotion_brand")
	private Set<Brand> brands = new HashSet<Brand>();

	/** 赠送优惠券 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "mall_promotion_coupon")
	private Set<Coupon> coupons = new HashSet<Coupon>();

	/** 赠品 */
	@Valid
	@OneToMany(mappedBy = "promotion", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<GiftItem> giftItems = new ArrayList<GiftItem>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getMinimumQuantity() {
		return minimumQuantity;
	}

	public void setMinimumQuantity(Integer minimumQuantity) {
		this.minimumQuantity = minimumQuantity;
	}

	public Integer getMaximumQuantity() {
		return maximumQuantity;
	}

	public void setMaximumQuantity(Integer maximumQuantity) {
		this.maximumQuantity = maximumQuantity;
	}

	public BigDecimal getMinimumPrice() {
		return minimumPrice;
	}

	public void setMinimumPrice(BigDecimal minimumPrice) {
		this.minimumPrice = minimumPrice;
	}

	public BigDecimal getMaximumPrice() {
		return maximumPrice;
	}

	public void setMaximumPrice(BigDecimal maximumPrice) {
		this.maximumPrice = maximumPrice;
	}

	public String getPriceExpression() {
		return priceExpression;
	}

	public void setPriceExpression(String priceExpression) {
		this.priceExpression = priceExpression;
	}

	public String getPointExpression() {
		return pointExpression;
	}

	public void setPointExpression(String pointExpression) {
		this.pointExpression = pointExpression;
	}

	public Boolean getIsFreeShipping() {
		return isFreeShipping;
	}

	public void setIsFreeShipping(Boolean isFreeShipping) {
		this.isFreeShipping = isFreeShipping;
	}

	public Boolean getIsCouponAllowed() {
		return isCouponAllowed;
	}

	public void setIsCouponAllowed(Boolean isCouponAllowed) {
		this.isCouponAllowed = isCouponAllowed;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Set<MemberRank> getMemberRanks() {
		return memberRanks;
	}

	public void setMemberRanks(Set<MemberRank> memberRanks) {
		this.memberRanks = memberRanks;
	}

	public Set<ProductCategory> getProductCategories() {
		return productCategories;
	}

	public void setProductCategories(Set<ProductCategory> productCategories) {
		this.productCategories = productCategories;
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

	public Set<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Set<Coupon> coupons) {
		this.coupons = coupons;
	}

	public List<GiftItem> getGiftItems() {
		return giftItems;
	}

	public void setGiftItems(List<GiftItem> giftItems) {
		this.giftItems = giftItems;
	}

}
