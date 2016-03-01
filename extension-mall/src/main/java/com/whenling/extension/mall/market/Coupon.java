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
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.whenling.centralize.model.User;
import com.whenling.extension.mall.order.model.Order;
import com.whenling.module.domain.model.BizEntity;

@Entity
@Table(name = "mall_coupon")
public class Coupon extends BizEntity<User, Long> {

	private static final long serialVersionUID = 2790798778368681149L;

	/** 名称 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String name;

	/** 前缀 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String prefix;

	/** 使用起始日期 */
	private Date beginDate;

	/** 使用结束日期 */
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

	/** 是否启用 */
	@NotNull
	@Column(nullable = false)
	private Boolean isEnabled;

	/** 是否允许积分兑换 */
	@NotNull
	@Column(nullable = false)
	private Boolean isExchange;

	/** 积分兑换数 */
	@Min(0)
	private Long point;

	/** 介绍 */
	@Lob
	private String introduction;

	/** 优惠码 */
	@OneToMany(mappedBy = "coupon", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<CouponCode> couponCodes = new HashSet<CouponCode>();

	/** 促销 */
	@ManyToMany(mappedBy = "coupons", fetch = FetchType.LAZY)
	private Set<Promotion> promotions = new HashSet<Promotion>();

	/** 订单 */
	@ManyToMany(mappedBy = "coupons", fetch = FetchType.LAZY)
	private List<Order> orders = new ArrayList<Order>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
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

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Boolean getIsExchange() {
		return isExchange;
	}

	public void setIsExchange(Boolean isExchange) {
		this.isExchange = isExchange;
	}

	public Long getPoint() {
		return point;
	}

	public void setPoint(Long point) {
		this.point = point;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Set<CouponCode> getCouponCodes() {
		return couponCodes;
	}

	public void setCouponCodes(Set<CouponCode> couponCodes) {
		this.couponCodes = couponCodes;
	}

	public Set<Promotion> getPromotions() {
		return promotions;
	}

	public void setPromotions(Set<Promotion> promotions) {
		this.promotions = promotions;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

}
