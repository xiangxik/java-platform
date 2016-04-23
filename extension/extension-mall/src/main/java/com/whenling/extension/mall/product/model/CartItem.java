package com.whenling.extension.mall.product.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.whenling.extension.mall.MallSetting;
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

	/**
	 * 获取临时商品价格
	 * 
	 * @return 临时商品价格
	 */
	@Transient
	public BigDecimal getTempPrice() {
		if (tempPrice == null) {
			return getSubtotal();
		}
		return tempPrice;
	}

	/**
	 * 设置临时商品价格
	 * 
	 * @param tempPrice
	 *            临时商品价格
	 */
	@Transient
	public void setTempPrice(BigDecimal tempPrice) {
		this.tempPrice = tempPrice;
	}

	/**
	 * 获取临时赠送积分
	 * 
	 * @return 临时赠送积分
	 */
	@Transient
	public Long getTempPoint() {
		if (tempPoint == null) {
			return getPoint();
		}
		return tempPoint;
	}

	/**
	 * 设置临时赠送积分
	 * 
	 * @param tempPoint
	 *            临时赠送积分
	 */
	@Transient
	public void setTempPoint(Long tempPoint) {
		this.tempPoint = tempPoint;
	}

	/**
	 * 获取赠送积分
	 * 
	 * @return 赠送积分
	 */
	@Transient
	public long getPoint() {
		if (getProduct() != null && getProduct().getPoint() != null && getQuantity() != null) {
			return getProduct().getPoint() * getQuantity();
		} else {
			return 0L;
		}
	}

	/**
	 * 获取商品重量
	 * 
	 * @return 商品重量
	 */
	@Transient
	public int getWeight() {
		if (getProduct() != null && getProduct().getWeight() != null && getQuantity() != null) {
			return getProduct().getWeight() * getQuantity();
		} else {
			return 0;
		}
	}

	/**
	 * 获取价格
	 * 
	 * @return 价格
	 */
	@Transient
	public BigDecimal getPrice() {
		if (getProduct() != null && getProduct().getPrice() != null) {
			MallSetting setting = MallSetting.get();
			// if (getCart() != null && getCart().getUser() != null &&
			// getCart().getUser().getMemberRank() != null) {
			// MemberRank memberRank = getCart().getUser().getMemberRank();
			// Map<MemberRank, BigDecimal> memberPrice =
			// getProduct().getMemberPrice();
			// if (memberPrice != null && !memberPrice.isEmpty()) {
			// if (memberPrice.containsKey(memberRank)) {
			// return setting.setScale(memberPrice.get(memberRank));
			// }
			// }
			// if (memberRank.getScale() != null) {
			// return setting.setScale(getProduct().getPrice().multiply(new
			// BigDecimal(memberRank.getScale())));
			// }
			// }
			return setting.setScale(getProduct().getPrice());
		} else {
			return new BigDecimal(0);
		}
	}

	/**
	 * 获取小计
	 * 
	 * @return 小计
	 */
	@Transient
	public BigDecimal getSubtotal() {
		if (getQuantity() != null) {
			return getPrice().multiply(new BigDecimal(getQuantity()));
		} else {
			return new BigDecimal(0);
		}
	}

	/**
	 * 获取是否库存不足
	 * 
	 * @return 是否库存不足
	 */
	@Transient
	public boolean getIsLowStock() {
		if (getQuantity() != null && getProduct() != null && getProduct().getStock() != null
				&& getQuantity() > getProduct().getAvailableStock()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 增加商品数量
	 * 
	 * @param quantity
	 *            数量
	 */
	@Transient
	public void add(int quantity) {
		if (quantity > 0) {
			if (getQuantity() != null) {
				setQuantity(getQuantity() + quantity);
			} else {
				setQuantity(quantity);
			}
		}
	}
}
