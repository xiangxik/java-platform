package com.whenling.plugin.payment.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.whenling.module.web.controller.BaseController;
import com.whenling.plugin.payment.model.Payment;
import com.whenling.plugin.payment.model.PaymentPlugin;
import com.whenling.plugin.payment.model.PaymentPlugin.NotifyMethod;
import com.whenling.plugin.payment.service.PaymentService;

@Controller
@RequestMapping("/payment")
public class PaymentController extends BaseController {

	@Autowired
	private PaymentService paymentService;

	@RequestMapping(value = "/notify/{notifyMethod}/{sn}", method = { RequestMethod.PUT, RequestMethod.GET, RequestMethod.POST })
	public String notify(@PathVariable NotifyMethod notifyMethod, @PathVariable String sn, Model model, HttpServletRequest request) {
		Payment payment = paymentService.findBySn(sn);
		if (payment != null) {
			PaymentPlugin paymentPlugin = paymentService.getPaymentPlugin(payment.getPaymentPluginId());
			if (paymentPlugin != null) {
				if (paymentPlugin.verifyNotify(sn, payment.getAmount(), notifyMethod, request)) {
					paymentService.handle(payment);
				}
				model.addAttribute("notifyMessage", paymentPlugin.getNotifyMessage(sn, notifyMethod, request));
			}
			model.addAttribute("payment", payment);
		}

		return classpath("/payment/notify");
	}

}
