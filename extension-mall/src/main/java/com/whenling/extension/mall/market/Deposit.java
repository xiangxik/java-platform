package com.whenling.extension.mall.market;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import com.whenling.centralize.model.User;
import com.whenling.extension.mall.order.model.Order;
import com.whenling.extension.mall.order.model.Payment;
import com.whenling.module.domain.model.BaseEntity;

@Entity
@Table(name = "xx_deposit")
public class Deposit extends BaseEntity<Long> {

	private static final long serialVersionUID = -2680443359995313570L;

	/**
	 * 类型
	 */
	public enum Type {

		/** 会员充值 */
		memberRecharge,

		/** 会员支付 */
		memberPayment,

		/** 后台充值 */
		adminRecharge,

		/** 后台扣费 */
		adminChargeback,

		/** 后台支付 */
		adminPayment,

		/** 后台退款 */
		adminRefunds
	}

	/** 类型 */
	@Column(nullable = false, updatable = false)
	private Type type;

	/** 收入金额 */
	@Column(nullable = false, updatable = false, precision = 21, scale = 6)
	private BigDecimal credit;

	/** 支出金额 */
	@Column(nullable = false, updatable = false, precision = 21, scale = 6)
	private BigDecimal debit;

	/** 当前余额 */
	@Column(nullable = false, updatable = false, precision = 21, scale = 6)
	private BigDecimal balance;

	/** 操作员 */
	@Column(updatable = false)
	private String operator;

	/** 备注 */
	@Length(max = 200)
	@Column(updatable = false)
	private String memo;

	/** 会员 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private User user;

	/** 订单 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orders")
	private Order order;

	/** 收款单 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Payment payment;

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public BigDecimal getCredit() {
		return credit;
	}

	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}

	public BigDecimal getDebit() {
		return debit;
	}

	public void setDebit(BigDecimal debit) {
		this.debit = debit;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
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

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

}
