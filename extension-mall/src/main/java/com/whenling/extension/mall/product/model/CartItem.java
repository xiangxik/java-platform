package com.whenling.extension.mall.product.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.whenling.module.domain.model.BaseEntity;

@Entity
@Table(name = "mall_cart_item")
public class CartItem extends BaseEntity<Long> {

	private static final long serialVersionUID = 4032292636677214197L;

	/** 最大数量 */
	public static final Integer MAX_QUANTITY = 10000;

	/** 数量 */
	@Column(nullable = false)
	private Integer quantity;

	/** 商品 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private Product product;

	/** 购物车 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Cart cart;

	/** 临时商品价格 */
	@Transient
	private BigDecimal tempPrice;

	/** 临时赠送积分 */
	@Transient
	private Long tempPoint;

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public BigDecimal getTempPrice() {
		return tempPrice;
	}

	public void setTempPrice(BigDecimal tempPrice) {
		this.tempPrice = tempPrice;
	}

	public Long getTempPoint() {
		return tempPoint;
	}

	public void setTempPoint(Long tempPoint) {
		this.tempPoint = tempPoint;
	}

}
