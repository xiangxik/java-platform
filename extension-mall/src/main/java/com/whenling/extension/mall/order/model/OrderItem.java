package com.whenling.extension.mall.order.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.whenling.extension.mall.product.model.Product;
import com.whenling.module.domain.model.BaseEntity;

@Entity
@Table(name = "xx_order_item")
public class OrderItem extends BaseEntity<Long> {

	private static final long serialVersionUID = -7877769490006596996L;

	/** 商品编号 */
	@NotEmpty
	@Column(nullable = false, updatable = false)
	private String sn;

	/** 商品名称 */
	@Column(nullable = false, updatable = false)
	private String name;

	/** 商品全称 */
	@Column(nullable = false, updatable = false)
	private String fullName;

	/** 商品价格 */
	@NotNull
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(nullable = false, precision = 21, scale = 6)
	private BigDecimal price;

	/** 商品重量 */
	@Column(updatable = false)
	private Integer weight;

	/** 商品缩略图 */
	@Column(updatable = false)
	private String thumbnail;

	/** 是否为赠品 */
	@Column(nullable = false, updatable = false)
	private Boolean isGift;

	/** 数量 */
	@NotNull
	@Min(1)
	@Max(10000)
	@Column(nullable = false)
	private Integer quantity;

	/** 已发货数量 */
	@Column(nullable = false)
	private Integer shippedQuantity;

	/** 已退货数量 */
	@Column(nullable = false)
	private Integer returnQuantity;

	/** 商品 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;

	/** 订单 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orders", nullable = false, updatable = false)
	private Order order;

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

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public Boolean getIsGift() {
		return isGift;
	}

	public void setIsGift(Boolean isGift) {
		this.isGift = isGift;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getShippedQuantity() {
		return shippedQuantity;
	}

	public void setShippedQuantity(Integer shippedQuantity) {
		this.shippedQuantity = shippedQuantity;
	}

	public Integer getReturnQuantity() {
		return returnQuantity;
	}

	public void setReturnQuantity(Integer returnQuantity) {
		this.returnQuantity = returnQuantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}
