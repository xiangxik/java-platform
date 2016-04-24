package com.whenling.plugin.payment.model;

import java.math.BigDecimal;

import com.whenling.plugin.model.PluginModel;
import com.whenling.plugin.payment.model.PaymentPlugin.FeeType;

public class PaymentPluginModel extends PluginModel {

	private String paymentName;
	private FeeType feeType;
	private BigDecimal fee;
	private String logo;
	private String description;

	public String getPaymentName() {
		return paymentName;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	public FeeType getFeeType() {
		return feeType;
	}

	public void setFeeType(FeeType feeType) {
		this.feeType = feeType;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
