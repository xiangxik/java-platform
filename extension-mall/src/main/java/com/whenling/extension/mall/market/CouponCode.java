package com.whenling.extension.mall.market;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.whenling.centralize.model.User;
import com.whenling.extension.mall.order.model.Order;
import com.whenling.module.domain.model.BaseEntity;

@Entity
@Table(name = "mall_coupon_code")
public class CouponCode extends BaseEntity<Long> {

	private static final long serialVersionUID = 888694468522589913L;

	/** 号码 */
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	private String code;

	/** 是否已使用 */
	@Column(nullable = false)
	private Boolean isUsed;

	/** 使用日期 */
	private Date usedDate;

	/** 优惠券 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private Coupon coupon;

	/** 会员 */
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	/** 订单 */
	@OneToOne(mappedBy = "couponCode", fetch = FetchType.LAZY)
	@JoinColumn(name = "orders")
	private Order order;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}

	public Date getUsedDate() {
		return usedDate;
	}

	public void setUsedDate(Date usedDate) {
		this.usedDate = usedDate;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
