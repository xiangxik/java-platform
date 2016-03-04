package com.whenling.extension.mall.order.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.whenling.module.domain.model.BaseEntity;

/**
 * 退款单
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月2日 下午4:21:33
 */
@Entity
@Table(name = "mall_refunds")
public class Refunds extends BaseEntity<Long> {

	private static final long serialVersionUID = 1712759381158892874L;

	/**
	 * 方式
	 */
	public enum Method {

		/** 在线支付 */
		online,

		/** 线下支付 */
		offline,

		/** 预存款支付 */
		deposit
	}

	/** 编号 */
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	private String sn;

	/** 方式 */
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, updatable = false)
	private Method method;

	/** 支付方式 */
	@Column(updatable = false)
	private String paymentMethod;

	/** 退款银行 */
	@Length(max = 200)
	@Column(updatable = false)
	private String bank;

	/** 退款账号 */
	@Length(max = 200)
	@Column(updatable = false)
	private String account;

	/** 退款金额 */
	@NotNull
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(nullable = false, updatable = false, precision = 21, scale = 6)
	private BigDecimal amount;

	/** 收款人 */
	@Length(max = 200)
	@Column(updatable = false)
	private String payee;

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

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
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
}
