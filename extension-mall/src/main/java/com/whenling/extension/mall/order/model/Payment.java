package com.whenling.extension.mall.order.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.whenling.centralize.model.User;
import com.whenling.extension.mall.market.Deposit;
import com.whenling.module.domain.model.BaseEntity;

/**
 * 收款单
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月2日 下午4:20:51
 */
@Entity
@Table(name = "mall_payment")
public class Payment extends BaseEntity<Long> {

	private static final long serialVersionUID = -2758327971395017062L;

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
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, updatable = false)
	private Method method;

	/** 状态 */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status status;

	/** 支付方式 */
	@Column(updatable = false)
	private String paymentMethod;

	/** 收款银行 */
	@Length(max = 200)
	private String bank;

	/** 收款账号 */
	@Length(max = 200)
	private String account;

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

	/** 预存款 */
	@OneToOne(mappedBy = "payment", fetch = FetchType.LAZY)
	private Deposit deposit;

	/** 会员 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false)
	private User user;

	/** 订单 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orders", updatable = false)
	private Order order;

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

	public Deposit getDeposit() {
		return deposit;
	}

	public void setDeposit(Deposit deposit) {
		this.deposit = deposit;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	/**
	 * 获取有效金额
	 * 
	 * @return 有效金额
	 */
	@Transient
	public BigDecimal getEffectiveAmount() {
		BigDecimal effectiveAmount = getAmount().subtract(getFee());
		return effectiveAmount.compareTo(new BigDecimal(0)) > 0 ? effectiveAmount : new BigDecimal(0);
	}

	/**
	 * 判断是否已过期
	 * 
	 * @return 是否已过期
	 */
	@Transient
	public boolean hasExpired() {
		return getExpire() != null && new Date().after(getExpire());
	}

	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {
		if (getDeposit() != null) {
			getDeposit().setPayment(null);
		}
	}
}
