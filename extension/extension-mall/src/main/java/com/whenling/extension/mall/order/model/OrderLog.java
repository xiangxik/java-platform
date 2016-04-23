package com.whenling.extension.mall.order.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.whenling.module.domain.model.BaseEntity;

/**
 * 订单日志
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月2日 下午4:20:42
 */
@Entity
@Table(name = "mall_order_log")
public class OrderLog extends BaseEntity<Long> {

	private static final long serialVersionUID = -1890792953018301184L;

	/**
	 * 类型
	 */
	public enum Type {

		/** 订单创建 */
		create,

		/** 订单修改 */
		modify,

		/** 订单确认 */
		confirm,

		/** 订单支付 */
		payment,

		/** 订单退款 */
		refunds,

		/** 订单发货 */
		shipping,

		/** 订单退货 */
		returns,

		/** 订单完成 */
		complete,

		/** 订单取消 */
		cancel,

		/** 其它 */
		other
	};

	/** 类型 */
	@Column(nullable = false, updatable = false)
	private Type type;

	/** 操作员 */
	@Column(updatable = false)
	private String operator;

	/** 内容 */
	@Column(updatable = false)
	private String content;

	/** 订单 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orders", nullable = false, updatable = false)
	private Order order;

	public OrderLog() {
	}

	public OrderLog(Type type, String operator) {
		this.type = type;
		this.operator = operator;
	}

	public OrderLog(Type type, String operator, String content) {
		this.type = type;
		this.operator = operator;
		this.content = content;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}
