package com.whenling.extension.mall.product.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.whenling.centralize.model.User;
import com.whenling.extension.mall.market.Promotion;
import com.whenling.module.domain.model.BizEntity;

/**
 * 赠品
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午8:47:12
 */
@Entity
@Table(name = "xx_gift_item", uniqueConstraints = { @UniqueConstraint(columnNames = { "gift", "promotion" }) })
public class GiftItem extends BizEntity<User, Long> {

	private static final long serialVersionUID = -7880947368809161057L;

	/** 数量 */
	@NotNull
	@Min(1)
	@Column(nullable = false)
	private Integer quantity;

	/** 赠品 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private Product gift;

	/** 促销 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private Promotion promotion;

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Product getGift() {
		return gift;
	}

	public void setGift(Product gift) {
		this.gift = gift;
	}

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}
}
