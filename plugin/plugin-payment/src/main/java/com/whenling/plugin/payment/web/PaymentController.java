package com.whenling.plugin.payment.web;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.whenling.centralize.model.User;
import com.whenling.centralize.support.web.CurrentUser;
import com.whenling.module.redis.service.SnService;
import com.whenling.module.web.controller.BaseController;
import com.whenling.plugin.payment.model.Payment;
import com.whenling.plugin.payment.model.Payment.Method;
import com.whenling.plugin.payment.model.Payment.Status;
import com.whenling.plugin.payment.model.Payment.Type;
import com.whenling.plugin.payment.model.PaymentPlugin;
import com.whenling.plugin.payment.model.PaymentPlugin.NotifyMethod;
import com.whenling.plugin.payment.service.PaymentService;

@Controller
@RequestMapping("/center/payment")
public class PaymentController extends BaseController {

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private Map<String, PaymentPlugin> paymentPluginMap = new HashMap<>();

	@Autowired
	private SnService snService;

	private static final String SN_TYPE = "payment";

	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public String submit(String paymentPluginId, Integer amount, String description, @CurrentUser User user, HttpServletRequest request,
			HttpServletResponse response, Model model) {

		PaymentPlugin paymentPlugin = paymentPluginMap.get(paymentPluginId);

		long nextValue = snService.nextValue(SN_TYPE);
		String sn = String.valueOf(10000000000l + nextValue);

		Payment payment = paymentService.newEntity();
		payment.setSn(sn);
		payment.setType(Type.payment);
		payment.setMethod(Method.online);
		payment.setStatus(Status.wait);
		payment.setPaymentMethod(paymentPlugin.getPaymentName());
		payment.setFee(paymentPlugin.calculateFee(new BigDecimal(amount)));
		payment.setAmount(paymentPlugin.calculateAmount(new BigDecimal(amount)));
		payment.setPaymentPluginId(paymentPluginId);
		payment.setExpire(paymentPlugin.getTimeout() != null ? DateUtils.addMinutes(new Date(), paymentPlugin.getTimeout()) : null);
		payment.setMember(user);
		paymentService.save(payment);

		model.addAttribute("requestUrl", paymentPlugin.getRequestUrl());
		model.addAttribute("requestMethod", paymentPlugin.getRequestMethod());
		model.addAttribute("requestCharset", paymentPlugin.getRequestCharset());
		model.addAttribute("parameterMap", paymentPlugin.getParameterMap(sn, new BigDecimal(amount), "充值", request));
		if (StringUtils.isNotEmpty(paymentPlugin.getRequestCharset())) {
			response.setContentType("text/html; charset=" + paymentPlugin.getRequestCharset());
		}

		return classpath("/payment/submit");
	}

	@RequestMapping(value = "/notify/{notifyMethod}/{sn}", method = { RequestMethod.GET, RequestMethod.POST })
	public String notify(@PathVariable NotifyMethod notifyMethod, @PathVariable String sn, Model model, HttpServletRequest request) {
		Payment payment = paymentService.findBySn(sn);
		if (payment != null) {
			PaymentPlugin paymentPlugin = paymentPluginMap.get(payment.getPaymentPluginId());
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
