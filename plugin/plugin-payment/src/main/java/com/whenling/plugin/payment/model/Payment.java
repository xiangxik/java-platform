package com.whenling.plugin.payment.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.whenling.centralize.model.User;
import com.whenling.module.domain.model.BizEntity;

@Entity
@Table(name = "plugin_payment")
public class Payment extends BizEntity<User, Long> {

	private static final long serialVersionUID = 7711763561875201385L;

	/**
	 * 类型
	 */
	public enum Type {

		/** 订单支付 */
		payment,

		/** 预存款充值 */
		recharge
	}

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

	/**
	 * 状态
	 */
	public enum Status {

		/** 等待支付 */
		wait,

		/** 支付成功 */
		success,

		/** 支付失败 */
		failure
	}

	/** 编号 */
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	private String sn;

	/** 类型 */
	@Column(nullable = false, updatable = false)
	private Type type;

	/** 方式 */
	@NotNull
	@Column(nullable = false, updatable = false)
	private Method method;

	/** 状态 */
	@Column(nullable = false)
	private Status status;

	/** 支付方式 */
	@Column(updatable = false)
	private String paymentMethod;

	/** 支付手续费 */
	@Column(nullable = false, precision = 21, scale = 6)
	private BigDecimal fee;

	/** 付款金额 */
	@NotNull
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(nullable = false, updatable = false, precision = 21, scale = 6)
	private BigDecimal amount;

	/** 付款人 */
	@Length(max = 200)
	private String payer;

	/** 操作员 */
	@Column(updatable = false)
	private String operator;

	/** 付款日期 */
	private Date paymentDate;

	/** 备注 */
	@Length(max = 200)
	private String memo;

	/** 支付插件ID */
	@JoinColumn(updatable = false)
	private String paymentPluginId;

	/** 到期时间 */
	@JoinColumn(updatable = false)
	private Date expire;

	/** 会员 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false)
	private User member;

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPaymentPluginId() {
		return paymentPluginId;
	}

	public void setPaymentPluginId(String paymentPluginId) {
		this.paymentPluginId = paymentPluginId;
	}

	public Date getExpire() {
		return expire;
	}

	public void setExpire(Date expire) {
		this.expire = expire;
	}

	public User getMember() {
		return member;
	}

	public void setMember(User member) {
		this.member = member;
	}

}
