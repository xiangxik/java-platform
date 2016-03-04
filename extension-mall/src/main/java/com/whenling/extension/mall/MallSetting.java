package com.whenling.extension.mall;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.apache.commons.configuration.Configuration;
import org.springframework.stereotype.Component;

import com.whenling.centralize.Application;

@Component
public class MallSetting {

	@Resource(name = "mallConfig")
	private Configuration configuration;

	public Boolean getIsTaxPriceEnabled() {
		return configuration.getBoolean("isTaxPriceEnabled");
	}

	public Double getTaxRate() {
		return configuration.getDouble("taxRate");
	}

	/**
	 * 小数位精确方式
	 */
	public enum RoundType {

		/** 四舍五入 */
		roundHalfUp,

		/** 向上取整 */
		roundUp,

		/** 向下取整 */
		roundDown
	}

	public RoundType getPriceRoundType() {
		return RoundType.valueOf(configuration.getString("priceRoundType"));
	}

	public Integer getPriceScale() {
		return configuration.getInt("priceScale");
	}

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
		int roundingMode;
		if (getPriceRoundType() == RoundType.roundUp) {
			roundingMode = BigDecimal.ROUND_UP;
		} else if (getPriceRoundType() == RoundType.roundDown) {
			roundingMode = BigDecimal.ROUND_DOWN;
		} else {
			roundingMode = BigDecimal.ROUND_HALF_UP;
		}
		return amount.setScale(getPriceScale(), roundingMode);
	}

	public static MallSetting get() {
		return Application.getBean(MallSetting.class);
	}
}
