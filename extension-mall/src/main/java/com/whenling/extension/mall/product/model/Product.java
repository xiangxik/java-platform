package com.whenling.extension.mall.product.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.whenling.centralize.model.User;
import com.whenling.extension.mall.market.Consultation;
import com.whenling.extension.mall.market.MemberRank;
import com.whenling.extension.mall.market.Promotion;
import com.whenling.extension.mall.market.Review;
import com.whenling.extension.mall.order.model.OrderItem;
import com.whenling.module.domain.model.BizEntity;

@Entity
@Table(name = "mall_product")
public class Product extends BizEntity<User, Long> {

	private static final long serialVersionUID = 1L;

	/**
	 * 排序类型
	 */
	public enum OrderType {

		/** 置顶降序 */
		topDesc,

		/** 价格升序 */
		priceAsc,

		/** 价格降序 */
		priceDesc,

		/** 销量降序 */
		salesDesc,

		/** 评分降序 */
		scoreDesc,

		/** 日期降序 */
		dateDesc
	}

	/** 编号 */
	@Length(max = 100)
	@Column(nullable = false, unique = true, length = 100)
	private String sn;

	/** 名称 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false, length = 200)
	private String name;

	/** 全称 */
	@Column(nullable = false)
	private String fullName;

	/** 销售价 */
	@NotNull
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(nullable = false, precision = 21, scale = 6)
	private BigDecimal price;

	/** 成本价 */
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(precision = 21, scale = 6)
	private BigDecimal cost;

	/** 市场价 */
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(nullable = false, precision = 21, scale = 6)
	private BigDecimal marketPrice;

	/** 展示图片 */
	@Column(length = 200)
	private String image;

	/** 单位 */
	@Column(length = 200)
	private String unit;

	/** 重量 */
	@Min(0)
	private Integer weight;

	/** 库存 */
	@Min(0)
	private Integer stock;

	/** 已分配库存 */
	@Column(nullable = false)
	private Integer allocatedStock;

	/** 库存备注 */
	@Column(length = 200)
	private String stockMemo;

	/** 赠送积分 */
	@Min(0)
	@Column(nullable = false)
	private Long point;

	/** 是否上架 */
	@NotNull
	@Column(nullable = false)
	private Boolean isMarketable;

	/** 是否列出 */
	@NotNull
	@Column(nullable = false)
	private Boolean isList;

	/** 是否置顶 */
	@NotNull
	@Column(nullable = false)
	private Boolean isTop;

	/** 是否为赠品 */
	@NotNull
	@Column(nullable = false)
	private Boolean isGift;

	/** 介绍 */
	@Lob
	private String introduction;

	/** 备注 */
	@Length(max = 200)
	@Column(length = 200)
	private String memo;

	/** 搜索关键词 */
	@Length(max = 200)
	@Column(length = 200)
	private String keyword;

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

	/** 评分 */
	@Column(nullable = false, precision = 12, scale = 6)
	private Float score;

	/** 总评分 */
	@Column(nullable = false)
	private Long totalScore;

	/** 评分数 */
	@Column(nullable = false)
	private Long scoreCount;

	/** 点击数 */
	@Column(nullable = false)
	private Long hits;

	/** 周点击数 */
	@Column(nullable = false)
	private Long weekHits;

	/** 月点击数 */
	@Column(nullable = false)
	private Long monthHits;

	/** 销量 */
	@Column(nullable = false)
	private Long sales;

	/** 周销量 */
	@Column(nullable = false)
	private Long weekSales;

	/** 月销量 */
	@Column(nullable = false)
	private Long monthSales;

	/** 周点击数更新日期 */
	@Column(nullable = false)
	private Date weekHitsDate;

	/** 月点击数更新日期 */
	@Column(nullable = false)
	private Date monthHitsDate;

	/** 周销量更新日期 */
	@Column(nullable = false)
	private Date weekSalesDate;

	/** 月销量更新日期 */
	@Column(nullable = false)
	private Date monthSalesDate;

	/** 商品属性值0 */
	@Length(max = 200)
	@Column(length = 200)
	private String attributeValue0;

	/** 商品属性值1 */
	@Length(max = 200)
	@Column(length = 200)
	private String attributeValue1;

	/** 商品属性值2 */
	@Length(max = 200)
	@Column(length = 200)
	private String attributeValue2;

	/** 商品属性值3 */
	@Length(max = 200)
	@Column(length = 200)
	private String attributeValue3;

	/** 商品属性值4 */
	@Length(max = 200)
	@Column(length = 200)
	private String attributeValue4;

	/** 商品属性值5 */
	@Length(max = 200)
	@Column(length = 200)
	private String attributeValue5;

	/** 商品属性值6 */
	@Length(max = 200)
	@Column(length = 200)
	private String attributeValue6;

	/** 商品属性值7 */
	@Length(max = 200)
	@Column(length = 200)
	private String attributeValue7;

	/** 商品属性值8 */
	@Length(max = 200)
	@Column(length = 200)
	private String attributeValue8;

	/** 商品属性值9 */
	@Length(max = 200)
	@Column(length = 200)
	private String attributeValue9;

	/** 商品属性值10 */
	@Length(max = 200)
	@Column(length = 200)
	private String attributeValue10;

	/** 商品属性值11 */
	@Length(max = 200)
	@Column(length = 200)
	private String attributeValue11;

	/** 商品属性值12 */
	@Length(max = 200)
	@Column(length = 200)
	private String attributeValue12;

	/** 商品属性值13 */
	@Length(max = 200)
	@Column(length = 200)
	private String attributeValue13;

	/** 商品属性值14 */
	@Length(max = 200)
	@Column(length = 200)
	private String attributeValue14;

	/** 商品属性值15 */
	@Length(max = 200)
	@Column(length = 200)
	private String attributeValue15;

	/** 商品属性值16 */
	@Length(max = 200)
	@Column(length = 200)
	private String attributeValue16;

	/** 商品属性值17 */
	@Length(max = 200)
	@Column(length = 200)
	private String attributeValue17;

	/** 商品属性值18 */
	@Length(max = 200)
	@Column(length = 200)
	private String attributeValue18;

	/** 商品属性值19 */
	@Length(max = 200)
	@Column(length = 200)
	private String attributeValue19;

	/** 商品分类 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private ProductCategory productCategory;

	/** 货品 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private Goods goods;

	/** 品牌 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Brand brand;

	/** 商品图片 */
	@Valid
	@ElementCollection
	@CollectionTable(name = "mall_product_product_image")
	private List<ProductImage> productImages = new ArrayList<ProductImage>();

	/** 评论 */
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<Review> reviews = new HashSet<Review>();

	/** 咨询 */
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<Consultation> consultations = new HashSet<Consultation>();

	/** 标签 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "mall_product_tag")
	@OrderBy("sortNo asc")
	private Set<ProductTag> tags = new HashSet<ProductTag>();

	/** 收藏会员 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "mall_user_favorite_product")
	@OrderBy("createdDate desc")
	private Set<User> favoriteUsers = new HashSet<User>();

	/** 规格 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "mall_product_specification")
	@OrderBy("sortNo asc")
	private Set<Specification> specifications = new HashSet<Specification>();

	/** 规格值 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "mall_product_specification_value")
	@OrderBy("specification asc")
	private Set<SpecificationValue> specificationValues = new HashSet<SpecificationValue>();

	/** 促销 */
	@ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
	private Set<Promotion> promotions = new HashSet<Promotion>();

	/** 购物车项 */
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<CartItem> cartItems = new HashSet<CartItem>();

	/** 订单项 */
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private Set<OrderItem> orderItems = new HashSet<OrderItem>();

	/** 赠品项 */
	@OneToMany(mappedBy = "gift", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<GiftItem> giftItems = new HashSet<GiftItem>();

	/** 到货通知 */
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<ProductNotify> productNotifies = new HashSet<ProductNotify>();

	/** 会员价 */
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "mall_product_member_price")
	private Map<MemberRank, BigDecimal> memberPrice = new HashMap<MemberRank, BigDecimal>();

	/** 参数值 */
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "mall_product_parameter_value")
	private Map<Parameter, String> parameterValue = new HashMap<Parameter, String>();

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getAllocatedStock() {
		return allocatedStock;
	}

	public void setAllocatedStock(Integer allocatedStock) {
		this.allocatedStock = allocatedStock;
	}

	public String getStockMemo() {
		return stockMemo;
	}

	public void setStockMemo(String stockMemo) {
		this.stockMemo = stockMemo;
	}

	public Long getPoint() {
		return point;
	}

	public void setPoint(Long point) {
		this.point = point;
	}

	public Boolean getIsMarketable() {
		return isMarketable;
	}

	public void setIsMarketable(Boolean isMarketable) {
		this.isMarketable = isMarketable;
	}

	public Boolean getIsList() {
		return isList;
	}

	public void setIsList(Boolean isList) {
		this.isList = isList;
	}

	public Boolean getIsTop() {
		return isTop;
	}

	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}

	public Boolean getIsGift() {
		return isGift;
	}

	public void setIsGift(Boolean isGift) {
		this.isGift = isGift;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
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

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public Long getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Long totalScore) {
		this.totalScore = totalScore;
	}

	public Long getScoreCount() {
		return scoreCount;
	}

	public void setScoreCount(Long scoreCount) {
		this.scoreCount = scoreCount;
	}

	public Long getHits() {
		return hits;
	}

	public void setHits(Long hits) {
		this.hits = hits;
	}

	public Long getWeekHits() {
		return weekHits;
	}

	public void setWeekHits(Long weekHits) {
		this.weekHits = weekHits;
	}

	public Long getMonthHits() {
		return monthHits;
	}

	public void setMonthHits(Long monthHits) {
		this.monthHits = monthHits;
	}

	public Long getSales() {
		return sales;
	}

	public void setSales(Long sales) {
		this.sales = sales;
	}

	public Long getWeekSales() {
		return weekSales;
	}

	public void setWeekSales(Long weekSales) {
		this.weekSales = weekSales;
	}

	public Long getMonthSales() {
		return monthSales;
	}

	public void setMonthSales(Long monthSales) {
		this.monthSales = monthSales;
	}

	public Date getWeekHitsDate() {
		return weekHitsDate;
	}

	public void setWeekHitsDate(Date weekHitsDate) {
		this.weekHitsDate = weekHitsDate;
	}

	public Date getMonthHitsDate() {
		return monthHitsDate;
	}

	public void setMonthHitsDate(Date monthHitsDate) {
		this.monthHitsDate = monthHitsDate;
	}

	public Date getWeekSalesDate() {
		return weekSalesDate;
	}

	public void setWeekSalesDate(Date weekSalesDate) {
		this.weekSalesDate = weekSalesDate;
	}

	public Date getMonthSalesDate() {
		return monthSalesDate;
	}

	public void setMonthSalesDate(Date monthSalesDate) {
		this.monthSalesDate = monthSalesDate;
	}

	public String getAttributeValue0() {
		return attributeValue0;
	}

	public void setAttributeValue0(String attributeValue0) {
		this.attributeValue0 = attributeValue0;
	}

	public String getAttributeValue1() {
		return attributeValue1;
	}

	public void setAttributeValue1(String attributeValue1) {
		this.attributeValue1 = attributeValue1;
	}

	public String getAttributeValue2() {
		return attributeValue2;
	}

	public void setAttributeValue2(String attributeValue2) {
		this.attributeValue2 = attributeValue2;
	}

	public String getAttributeValue3() {
		return attributeValue3;
	}

	public void setAttributeValue3(String attributeValue3) {
		this.attributeValue3 = attributeValue3;
	}

	public String getAttributeValue4() {
		return attributeValue4;
	}

	public void setAttributeValue4(String attributeValue4) {
		this.attributeValue4 = attributeValue4;
	}

	public String getAttributeValue5() {
		return attributeValue5;
	}

	public void setAttributeValue5(String attributeValue5) {
		this.attributeValue5 = attributeValue5;
	}

	public String getAttributeValue6() {
		return attributeValue6;
	}

	public void setAttributeValue6(String attributeValue6) {
		this.attributeValue6 = attributeValue6;
	}

	public String getAttributeValue7() {
		return attributeValue7;
	}

	public void setAttributeValue7(String attributeValue7) {
		this.attributeValue7 = attributeValue7;
	}

	public String getAttributeValue8() {
		return attributeValue8;
	}

	public void setAttributeValue8(String attributeValue8) {
		this.attributeValue8 = attributeValue8;
	}

	public String getAttributeValue9() {
		return attributeValue9;
	}

	public void setAttributeValue9(String attributeValue9) {
		this.attributeValue9 = attributeValue9;
	}

	public String getAttributeValue10() {
		return attributeValue10;
	}

	public void setAttributeValue10(String attributeValue10) {
		this.attributeValue10 = attributeValue10;
	}

	public String getAttributeValue11() {
		return attributeValue11;
	}

	public void setAttributeValue11(String attributeValue11) {
		this.attributeValue11 = attributeValue11;
	}

	public String getAttributeValue12() {
		return attributeValue12;
	}

	public void setAttributeValue12(String attributeValue12) {
		this.attributeValue12 = attributeValue12;
	}

	public String getAttributeValue13() {
		return attributeValue13;
	}

	public void setAttributeValue13(String attributeValue13) {
		this.attributeValue13 = attributeValue13;
	}

	public String getAttributeValue14() {
		return attributeValue14;
	}

	public void setAttributeValue14(String attributeValue14) {
		this.attributeValue14 = attributeValue14;
	}

	public String getAttributeValue15() {
		return attributeValue15;
	}

	public void setAttributeValue15(String attributeValue15) {
		this.attributeValue15 = attributeValue15;
	}

	public String getAttributeValue16() {
		return attributeValue16;
	}

	public void setAttributeValue16(String attributeValue16) {
		this.attributeValue16 = attributeValue16;
	}

	public String getAttributeValue17() {
		return attributeValue17;
	}

	public void setAttributeValue17(String attributeValue17) {
		this.attributeValue17 = attributeValue17;
	}

	public String getAttributeValue18() {
		return attributeValue18;
	}

	public void setAttributeValue18(String attributeValue18) {
		this.attributeValue18 = attributeValue18;
	}

	public String getAttributeValue19() {
		return attributeValue19;
	}

	public void setAttributeValue19(String attributeValue19) {
		this.attributeValue19 = attributeValue19;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public List<ProductImage> getProductImages() {
		return productImages;
	}

	public void setProductImages(List<ProductImage> productImages) {
		this.productImages = productImages;
	}

	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	public Set<Consultation> getConsultations() {
		return consultations;
	}

	public void setConsultations(Set<Consultation> consultations) {
		this.consultations = consultations;
	}

	public Set<ProductTag> getTags() {
		return tags;
	}

	public void setTags(Set<ProductTag> tags) {
		this.tags = tags;
	}

	public Set<User> getFavoriteUsers() {
		return favoriteUsers;
	}

	public void setFavoriteUsers(Set<User> favoriteUsers) {
		this.favoriteUsers = favoriteUsers;
	}

	public Set<Specification> getSpecifications() {
		return specifications;
	}

	public void setSpecifications(Set<Specification> specifications) {
		this.specifications = specifications;
	}

	public Set<SpecificationValue> getSpecificationValues() {
		return specificationValues;
	}

	public void setSpecificationValues(Set<SpecificationValue> specificationValues) {
		this.specificationValues = specificationValues;
	}

	public Set<Promotion> getPromotions() {
		return promotions;
	}

	public void setPromotions(Set<Promotion> promotions) {
		this.promotions = promotions;
	}

	public Set<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(Set<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Set<GiftItem> getGiftItems() {
		return giftItems;
	}

	public void setGiftItems(Set<GiftItem> giftItems) {
		this.giftItems = giftItems;
	}

	public Set<ProductNotify> getProductNotifies() {
		return productNotifies;
	}

	public void setProductNotifies(Set<ProductNotify> productNotifies) {
		this.productNotifies = productNotifies;
	}

	public Map<MemberRank, BigDecimal> getMemberPrice() {
		return memberPrice;
	}

	public void setMemberPrice(Map<MemberRank, BigDecimal> memberPrice) {
		this.memberPrice = memberPrice;
	}

	public Map<Parameter, String> getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(Map<Parameter, String> parameterValue) {
		this.parameterValue = parameterValue;
	}

}
