package com.whenling.extension.mall.order.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.whenling.module.domain.model.BaseEntity;

/**
 * 发货单
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月2日 下午4:37:17
 */
@Entity
@Table(name = "mall_shipping")
public class Shipping extends BaseEntity<Long> {

	private static final long serialVersionUID = 7428567392079505861L;

	/** 编号 */
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	private String sn;

	/** 配送方式 */
	@NotEmpty
	@Column(nullable = false, updatable = false)
	private String shippingMethod;

	/** 物流公司 */
	@NotEmpty
	@Column(nullable = false, updatable = false)
	private String deliveryCorp;

	/** 物流公司网址 */
	@Column(updatable = false)
	private String deliveryCorpUrl;

	/** 物流公司代码 */
	@Column(updatable = false)
	private String deliveryCorpCode;

	/** 运单号 */
	@Length(max = 200)
	@Column(updatable = false)
	private String trackingNo;

	/** 物流费用 */
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(updatable = false, precision = 21, scale = 6)
	private BigDecimal freight;

	/** 收货人 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false, updatable = false)
	private String consignee;

	/** 地区 */
	@NotEmpty
	@Column(nullable = false, updatable = false)
	private String area;

	/** 地址 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false, updatable = false)
	private String address;

	/** 邮编 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false, updatable = false)
	private String zipCode;

	/** 电话 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false, updatable = false)
	private String phone;

	/** 操作员 */
	@Column(nullable = false, updatable = false)
	private String operator;

	/** 备注 */
	@Length(max = 200)
	@Column(updatable = false)
	private String memo;

	/** 订单 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orders", nullable = false, updatable = false)
	private Order order;

	/** 发货项 */
	@Valid
	@NotEmpty
	@OneToMany(mappedBy = "shipping", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ShippingItem> shippingItems = new ArrayList<ShippingItem>();

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public String getDeliveryCorp() {
		return deliveryCorp;
	}

	public void setDeliveryCorp(String deliveryCorp) {
		this.deliveryCorp = deliveryCorp;
	}

	public String getDeliveryCorpUrl() {
		return deliveryCorpUrl;
	}

	public void setDeliveryCorpUrl(String deliveryCorpUrl) {
		this.deliveryCorpUrl = deliveryCorpUrl;
	}

	public String getDeliveryCorpCode() {
		return deliveryCorpCode;
	}

	public void setDeliveryCorpCode(String deliveryCorpCode) {
		this.deliveryCorpCode = deliveryCorpCode;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public List<ShippingItem> getShippingItems() {
		return shippingItems;
	}

	public void setShippingItems(List<ShippingItem> shippingItems) {
		this.shippingItems = shippingItems;
	}

	/**
	 * 获取数量
	 * 
	 * @return 数量
	 */
	@Transient
	public int getQuantity() {
		int quantity = 0;
		if (getShippingItems() != null) {
			for (ShippingItem shippingItem : getShippingItems()) {
				if (shippingItem != null && shippingItem.getQuantity() != null) {
					quantity += shippingItem.getQuantity();
				}
			}
		}
		return quantity;
	}
}
