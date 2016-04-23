package com.whenling.plugin.payment.model;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.whenling.module.base.SpringContext;

@Component
public class PaymentPluginSetting {

	/**
	 * 设置精度
	 * 
	 * @param amount
	 *            数值
	 * @return 数值
	 */
	public BigDecimal setScale(BigDecimal amount) {
		if (amount == null) {
			return null;
		}
		return amount.setScale(2, BigDecimal.ROUND_UP);
	}

	public static PaymentPluginSetting get() {
		return SpringContext.getBean(PaymentPluginSetting.class);
	}

}
