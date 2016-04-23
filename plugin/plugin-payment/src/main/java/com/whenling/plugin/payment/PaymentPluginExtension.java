package com.whenling.plugin.payment;

import org.springframework.stereotype.Component;

import com.whenling.centralize.Application;
import com.whenling.centralize.Extension;

@Component
public class PaymentPluginExtension extends Extension {

	@Override
	public void init(Application app, boolean isNew, boolean isUpdate, Integer historyVersion) {
		if (isNew) {
			app.addMenu("支付插件", "paymentplugin", "Brick", "app.view.plugin.PaymentPluginList", null, null);
		}
	}

	@Override
	public Integer getVersion() {
		return 1;
	}

	@Override
	public String getAuthor() {
		return "ken";
	}

}
