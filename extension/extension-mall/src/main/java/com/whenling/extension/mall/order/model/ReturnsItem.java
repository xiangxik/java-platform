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

/**
 * 退货项
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月2日 下午4:37:04
 */
@Entity
@Table(name = "mall_returns_item")
public class ReturnsItem extends BaseEntity<Long> {

	private static final long serialVersionUID = -3077717937333049473L;

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

	/** 退货单 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private Returns returns;

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

	public Returns getReturns() {
		return returns;
	}

	public void setReturns(Returns returns) {
		this.returns = returns;
	}

}
