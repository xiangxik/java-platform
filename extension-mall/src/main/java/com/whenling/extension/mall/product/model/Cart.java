package com.whenling.extension.mall.product.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.DigestUtils;

import com.whenling.centralize.model.User;
import com.whenling.extension.mall.MallSetting;
import com.whenling.extension.mall.market.Coupon;
import com.whenling.extension.mall.market.Promotion;
import com.whenling.module.domain.model.BizEntity;

@Entity
@Table(name = "mall_cart")
public class Cart extends BizEntity<User, Long> {

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

	/**
	 * 获取商品重量
	 * 
	 * @return 商品重量
	 */
	@Transient
	public int getWeight() {
		int weight = 0;
		if (getCartItems() != null) {
			for (CartItem cartItem : getCartItems()) {
				if (cartItem != null) {
					weight += cartItem.getWeight();
				}
			}
		}
		return weight;
	}

	/**
	 * 获取商品数量
	 * 
	 * @return 商品数量
	 */
	@Transient
	public int getQuantity() {
		int quantity = 0;
		if (getCartItems() != null) {
			for (CartItem cartItem : getCartItems()) {
				if (cartItem != null && cartItem.getQuantity() != null) {
					quantity += cartItem.getQuantity();
				}
			}
		}
		return quantity;
	}

	/**
	 * 获取赠送积分
	 * 
	 * @return 赠送积分
	 */
	@Transient
	public long getPoint() {
		long point = 0L;
		if (getCartItems() != null) {
			for (CartItem cartItem : getCartItems()) {
				if (cartItem != null) {
					point += cartItem.getPoint();
				}
			}
		}
		return point;
	}

	/**
	 * 获取赠送积分增加值
	 * 
	 * @return 赠送积分增加值
	 */
	@Transient
	public long getAddedPoint() {
		long originalPoint = 0L;
		long currentPoint = 0L;
		for (Promotion promotion : getPromotions()) {
			if (promotion != null) {
				int promotionQuantity = getQuantity(promotion);
				long originalPromotionPoint = getTempPoint(promotion);
				long currentPromotionPoint = promotion.calculatePoint(promotionQuantity, originalPromotionPoint);
				originalPoint += originalPromotionPoint;
				currentPoint += currentPromotionPoint;
				Set<CartItem> cartItems = getCartItems(promotion);
				for (CartItem cartItem : cartItems) {
					if (cartItem != null && cartItem.getTempPoint() != null) {
						long tempPoint;
						if (originalPromotionPoint > 0) {
							tempPoint = (long) (currentPromotionPoint / (double) originalPromotionPoint
									* cartItem.getTempPoint());
						} else {
							tempPoint = (currentPromotionPoint - originalPromotionPoint) / promotionQuantity;
						}
						cartItem.setTempPoint(tempPoint > 0 ? tempPoint : 0L);
					}
				}
			}
		}
		if (getCartItems() != null) {
			for (CartItem cartItem : getCartItems()) {
				if (cartItem != null) {
					cartItem.setTempPoint(null);
				}
			}
		}
		long addedPoint = currentPoint - originalPoint;
		return addedPoint > 0 ? addedPoint : 0L;
	}

	/**
	 * 获取有效赠送积分
	 * 
	 * @return 有效赠送积分
	 */
	@Transient
	public long getEffectivePoint() {
		long effectivePoint = getPoint() + getAddedPoint();
		return effectivePoint > 0L ? effectivePoint : 0L;
	}

	/**
	 * 获取商品价格
	 * 
	 * @return 商品价格
	 */
	@Transient
	public BigDecimal getPrice() {
		BigDecimal price = new BigDecimal(0);
		if (getCartItems() != null) {
			for (CartItem cartItem : getCartItems()) {
				if (cartItem != null && cartItem.getSubtotal() != null) {
					price = price.add(cartItem.getSubtotal());
				}
			}
		}
		return price;
	}

	/**
	 * 获取折扣
	 * 
	 * @return 折扣
	 */
	@Transient
	public BigDecimal getDiscount() {
		BigDecimal originalPrice = new BigDecimal(0);
		BigDecimal currentPrice = new BigDecimal(0);
		for (Promotion promotion : getPromotions()) {
			if (promotion != null) {
				int promotionQuantity = getQuantity(promotion);
				BigDecimal originalPromotionPrice = getTempPrice(promotion);
				BigDecimal currentPromotionPrice = promotion.calculatePrice(promotionQuantity, originalPromotionPrice);
				originalPrice = originalPrice.add(originalPromotionPrice);
				currentPrice = currentPrice.add(currentPromotionPrice);
				Set<CartItem> cartItems = getCartItems(promotion);
				for (CartItem cartItem : cartItems) {
					if (cartItem != null && cartItem.getTempPrice() != null) {
						BigDecimal tempPrice;
						if (originalPromotionPrice.compareTo(new BigDecimal(0)) > 0) {
							tempPrice = currentPromotionPrice.divide(originalPromotionPrice, 50, RoundingMode.DOWN)
									.multiply(cartItem.getTempPrice());
						} else {
							tempPrice = new BigDecimal(0);
						}
						cartItem.setTempPrice(
								tempPrice.compareTo(new BigDecimal(0)) > 0 ? tempPrice : new BigDecimal(0));
					}
				}
			}
		}
		if (getCartItems() != null) {
			for (CartItem cartItem : getCartItems()) {
				if (cartItem != null) {
					cartItem.setTempPrice(null);
				}
			}
		}
		MallSetting setting = MallSetting.get();
		BigDecimal discount = setting.setScale(originalPrice.subtract(currentPrice));
		return discount.compareTo(new BigDecimal(0)) > 0 ? discount : new BigDecimal(0);
	}

	/**
	 * 获取有效商品价格
	 * 
	 * @return 有效商品价格
	 */
	@Transient
	public BigDecimal getEffectivePrice() {
		BigDecimal effectivePrice = getPrice().subtract(getDiscount());
		return effectivePrice.compareTo(new BigDecimal(0)) > 0 ? effectivePrice : new BigDecimal(0);
	}

	/**
	 * 获取赠品项
	 * 
	 * @return 赠品项
	 */
	@Transient
	public Set<GiftItem> getGiftItems() {
		Set<GiftItem> giftItems = new HashSet<GiftItem>();
		for (Promotion promotion : getPromotions()) {
			if (promotion.getGiftItems() != null) {
				for (final GiftItem giftItem : promotion.getGiftItems()) {
					GiftItem target = (GiftItem) CollectionUtils.find(giftItems, new Predicate() {
						public boolean evaluate(Object object) {
							GiftItem other = (GiftItem) object;
							return other != null && other.getGift().equals(giftItem.getGift());
						}
					});
					if (target != null) {
						target.setQuantity(target.getQuantity() + giftItem.getQuantity());
					} else {
						giftItems.add(giftItem);
					}
				}
			}
		}
		return giftItems;
	}

	/**
	 * 获取促销
	 * 
	 * @return 促销
	 */
	@Transient
	public Set<Promotion> getPromotions() {
		Set<Promotion> allPromotions = new HashSet<Promotion>();
		if (getCartItems() != null) {
			for (CartItem cartItem : getCartItems()) {
				if (cartItem != null && cartItem.getProduct() != null) {
					allPromotions.addAll(cartItem.getProduct().getValidPromotions());
				}
			}
		}
		Set<Promotion> promotions = new TreeSet<Promotion>();
		for (Promotion promotion : allPromotions) {
			if (isValid(promotion)) {
				promotions.add(promotion);
			}
		}
		return promotions;
	}

	/**
	 * 获取促销购物车项
	 * 
	 * @param promotion
	 *            促销
	 * @return 促销购物车项
	 */
	@Transient
	private Set<CartItem> getCartItems(Promotion promotion) {
		Set<CartItem> cartItems = new HashSet<CartItem>();
		if (promotion != null && getCartItems() != null) {
			for (CartItem cartItem : getCartItems()) {
				if (cartItem != null && cartItem.getProduct() != null && cartItem.getProduct().isValid(promotion)) {
					cartItems.add(cartItem);
				}
			}
		}
		return cartItems;
	}

	/**
	 * 获取促销商品数量
	 * 
	 * @param promotion
	 *            促销
	 * @return 促销商品数量
	 */
	@Transient
	private int getQuantity(Promotion promotion) {
		int quantity = 0;
		for (CartItem cartItem : getCartItems(promotion)) {
			if (cartItem != null && cartItem.getQuantity() != null) {
				quantity += cartItem.getQuantity();
			}
		}
		return quantity;
	}

	/**
	 * 获取促销商品赠送积分
	 * 
	 * @param promotion
	 *            促销
	 * @return 促销商品赠送积分
	 */
	@Transient
	private long getPoint(Promotion promotion) {
		long point = 0L;
		for (CartItem cartItem : getCartItems(promotion)) {
			if (cartItem != null) {
				point += cartItem.getPoint();
			}
		}
		return point;
	}

	/**
	 * 获取促销商品临时赠送积分
	 * 
	 * @param promotion
	 *            促销
	 * @return 促销商品临时赠送积分
	 */
	@Transient
	private long getTempPoint(Promotion promotion) {
		long tempPoint = 0L;
		for (CartItem cartItem : getCartItems(promotion)) {
			if (cartItem != null && cartItem.getTempPoint() != null) {
				tempPoint += cartItem.getTempPoint();
			}
		}
		return tempPoint;
	}

	/**
	 * 获取促销商品价格
	 * 
	 * @param promotion
	 *            促销
	 * @return 促销商品价格
	 */
	@Transient
	private BigDecimal getPrice(Promotion promotion) {
		BigDecimal price = new BigDecimal(0);
		for (CartItem cartItem : getCartItems(promotion)) {
			if (cartItem != null && cartItem.getSubtotal() != null) {
				price = price.add(cartItem.getSubtotal());
			}
		}
		return price;
	}

	/**
	 * 获取促销商品临时价格
	 * 
	 * @param promotion
	 *            促销
	 * @return 促销商品临时价格
	 */
	@Transient
	private BigDecimal getTempPrice(Promotion promotion) {
		BigDecimal tempPrice = new BigDecimal(0);
		for (CartItem cartItem : getCartItems(promotion)) {
			if (cartItem != null && cartItem.getTempPrice() != null) {
				tempPrice = tempPrice.add(cartItem.getTempPrice());
			}
		}
		return tempPrice;
	}

	/**
	 * 判断促销是否有效
	 * 
	 * @param promotion
	 *            促销
	 * @return 促销是否有效
	 */
	@Transient
	private boolean isValid(Promotion promotion) {
		if (promotion == null || !promotion.hasBegun() || promotion.hasEnded()) {
			return false;
		}
		// if (promotion.getMemberRanks() == null ||
		// promotion.getMemberRanks().isEmpty() || getUser() == null ||
		// getMember().getMemberRank() == null ||
		// !promotion.getMemberRanks().contains(getMember().getMemberRank())) {
		// return false;
		// }
		Integer quantity = getQuantity(promotion);
		if ((promotion.getMinimumQuantity() != null && promotion.getMinimumQuantity() > quantity)
				|| (promotion.getMaximumQuantity() != null && promotion.getMaximumQuantity() < quantity)) {
			return false;
		}
		BigDecimal price = getPrice(promotion);
		if ((promotion.getMinimumPrice() != null && promotion.getMinimumPrice().compareTo(price) > 0)
				|| (promotion.getMaximumPrice() != null && promotion.getMaximumPrice().compareTo(price) < 0)) {
			return false;
		}
		return true;
	}

	/**
	 * 判断优惠券是否有效
	 * 
	 * @param coupon
	 *            优惠券
	 * @return 优惠券是否有效
	 */
	@Transient
	public boolean isValid(Coupon coupon) {
		if (coupon == null || !coupon.getIsEnabled() || !coupon.hasBegun() || coupon.hasExpired()) {
			return false;
		}
		if ((coupon.getMinimumQuantity() != null && coupon.getMinimumQuantity() > getQuantity())
				|| (coupon.getMaximumQuantity() != null && coupon.getMaximumQuantity() < getQuantity())) {
			return false;
		}
		if ((coupon.getMinimumPrice() != null && coupon.getMinimumPrice().compareTo(getEffectivePrice()) > 0)
				|| (coupon.getMaximumPrice() != null && coupon.getMaximumPrice().compareTo(getEffectivePrice()) < 0)) {
			return false;
		}
		return true;
	}

	/**
	 * 获取购物车项
	 * 
	 * @param product
	 *            商品
	 * @return 购物车项
	 */
	@Transient
	public CartItem getCartItem(Product product) {
		if (product != null && getCartItems() != null) {
			for (CartItem cartItem : getCartItems()) {
				if (cartItem != null && cartItem.getProduct() != null && cartItem.getProduct().equals(product)) {
					return cartItem;
				}
			}
		}
		return null;
	}

	/**
	 * 判断是否包含商品
	 * 
	 * @param product
	 *            商品
	 * @return 是否包含商品
	 */
	@Transient
	public boolean contains(Product product) {
		if (product != null && getCartItems() != null) {
			for (CartItem cartItem : getCartItems()) {
				if (cartItem != null && cartItem.getProduct() != null && cartItem.getProduct().equals(product)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 获取令牌
	 * 
	 * @return 令牌
	 */
	@Transient
	public String getToken() {
		HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(17, 37).append(getKey());
		if (getCartItems() != null) {
			for (CartItem cartItem : getCartItems()) {
				hashCodeBuilder.append(cartItem.getProduct()).append(cartItem.getQuantity())
						.append(cartItem.getPrice());
			}
		}
		return DigestUtils.md5DigestAsHex(hashCodeBuilder.toString().getBytes());
	}

	/**
	 * 获取是否库存不足
	 * 
	 * @return 是否库存不足
	 */
	@Transient
	public boolean getIsLowStock() {
		if (getCartItems() != null) {
			for (CartItem cartItem : getCartItems()) {
				if (cartItem != null && cartItem.getIsLowStock()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断是否已过期
	 * 
	 * @return 是否已过期
	 */
	@Transient
	public boolean hasExpired() {
		return new Date().after(DateUtils.addSeconds(getLastModifiedDate().toDate(), TIMEOUT));
	}

	/**
	 * 判断是否允许使用优惠券
	 * 
	 * @return 是否允许使用优惠券
	 */
	@Transient
	public boolean isCouponAllowed() {
		for (Promotion promotion : getPromotions()) {
			if (promotion != null && !promotion.getIsCouponAllowed()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断是否为空
	 * 
	 * @return 是否为空
	 */
	@Transient
	public boolean isEmpty() {
		return getCartItems() == null || getCartItems().isEmpty();
	}
}
