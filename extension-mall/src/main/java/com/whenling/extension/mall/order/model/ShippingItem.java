package com.whenling.extension.mall.order.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.whenling.module.domain.model.BaseEntity;

@Entity
@Table(name = "xx_shipping_item")
public class ShippingItem extends BaseEntity<Long> {

	private static final long serialVersionUID = 4883958012370518864L;

	/** 商品编号 */
	@NotEmpty
	@Column(nullable = false, updatable = false)
	private String sn;

	/** 商品名称 */
	@NotEmpty
	@Column(nullable = false, updatable = false)
	private String name;

	/** 数量 */
	@NotNull
	@Min(1)
	@Column(nullable = false, updatable = false)
	private Integer quantity;

	/** 发货单 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private Shipping shipping;

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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Shipping getShipping() {
		return shipping;
	}

	public void setShipping(Shipping shipping) {
		this.shipping = shipping;
	}

}
