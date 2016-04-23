package com.whenling.plugin.payment.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whenling.plugin.payment.model.PaymentPlugin;

@Controller
@RequestMapping("/admin/payment/plugin")
public class PaymentPluginController {

	@Autowired
	private List<PaymentPlugin> paymentPlugins = new ArrayList<PaymentPlugin>();

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Page<PaymentPlugin> list(Pageable pageable) {
		return new PageImpl<>(paymentPlugins);
	}
}
