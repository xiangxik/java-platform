package com.whenling.extension.mall.product.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.whenling.centralize.model.User;
import com.whenling.module.domain.model.BaseEntity;

@Entity
@Table(name = "mall_cart")
public class Cart extends BaseEntity<Long> {

	private static final long serialVersionUID = -573223077944217165L;

	/** 超时时间 */
	public static final int TIMEOUT = 604800;

	/** 最大商品数 */
	public static final Integer MAX_PRODUCT_COUNT = 100;

	/** "ID"Cookie名称 */
	public static final String ID_COOKIE_NAME = "cartId";

	/** "密钥"Cookie名称 */
	public static final String KEY_COOKIE_NAME = "cartKey";

	/** 密钥 */
	@Column(name = "cart_key", nullable = false, updatable = false)
	private String key;

	@OneToOne(fetch = FetchType.LAZY)
	private User user;

	/** 购物车项 */
	@OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<CartItem> cartItems = new HashSet<CartItem>();

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(Set<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

}
