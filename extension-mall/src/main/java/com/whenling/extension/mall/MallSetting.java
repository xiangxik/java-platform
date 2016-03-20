package com.whenling.extension.mall;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.apache.commons.configuration.Configuration;
import org.springframework.stereotype.Component;

import com.whenling.centralize.Application;

@Component
public class MallSetting {

	public static final String KEY_IS_TAX_PRICE_ENABLED = "isTaxPriceEnabled";
	public static final String KEY_TAX_RATE = "taxRate";
	public static final String KEY_PRICE_ROUND_TYPE = "priceRoundType";
	public static final String KEY_PRICE_SCALE = "priceScale";

	@Resource(name = "mallConfig")
	private Configuration mallConfig;

	public Boolean getIsTaxPriceEnabled() {
		return mallConfig.getBoolean(KEY_IS_TAX_PRICE_ENABLED);
	}

	public Double getTaxRate() {
		return mallConfig.getDouble(KEY_TAX_RATE);
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
		return RoundType.valueOf(mallConfig.getString(KEY_PRICE_ROUND_TYPE));
	}

	public Integer getPriceScale() {
		return mallConfig.getInt(KEY_PRICE_SCALE);
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
