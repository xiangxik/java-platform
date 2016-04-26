package com.whenling.plugin.payment.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.whenling.centralize.model.User;
import com.whenling.module.domain.service.BaseService;
import com.whenling.module.web.view.ViewConfiguration;
import com.whenling.plugin.payment.model.Payment;
import com.whenling.plugin.payment.model.Payment.Method;
import com.whenling.plugin.payment.model.Payment.Status;
import com.whenling.plugin.payment.model.Payment.Type;
import com.whenling.plugin.payment.model.PaymentPlugin;
import com.whenling.plugin.payment.repo.PaymentRepository;

@Service
public class PaymentService extends BaseService<Payment, Long> implements ApplicationEventPublisherAware {

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private Map<String, PaymentPlugin> paymentPluginMap = new HashMap<>();

	private ApplicationEventPublisher applicationEventPublisher;

	public PaymentPlugin getPaymentPlugin(String paymentPluginId) {
		return paymentPluginMap.get(paymentPluginId);
	}

	public Payment findBySn(String sn) {
		return paymentRepository.findBySn(sn);
	}

	public String submitToView(String sn, String paymentPluginId, BigDecimal amount, String description, User member, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		PaymentPlugin paymentPlugin = getPaymentPlugin(paymentPluginId);
		Payment payment = newEntity();
		payment.setSn(sn);
		payment.setType(Type.payment);
		payment.setMethod(Method.online);
		payment.setStatus(Status.wait);
		payment.setPaymentMethod(paymentPlugin.getPaymentName());
		payment.setFee(paymentPlugin.calculateFee(amount));
		payment.setAmount(paymentPlugin.calculateAmount(amount));
		payment.setPaymentPluginId(paymentPluginId);
		payment.setExpire(paymentPlugin.getTimeout() != null ? DateUtils.addMinutes(new Date(), paymentPlugin.getTimeout()) : null);
		payment.setMember(member);
		save(payment);

		model.addAttribute("requestUrl", paymentPlugin.getRequestUrl());
		model.addAttribute("requestMethod", paymentPlugin.getRequestMethod());
		model.addAttribute("requestCharset", paymentPlugin.getRequestCharset());
		model.addAttribute("parameterMap", paymentPlugin.getParameterMap(sn, amount, description, request));
		if (StringUtils.isNotEmpty(paymentPlugin.getRequestCharset())) {
			response.setContentType("text/html; charset=" + paymentPlugin.getRequestCharset());
		}
		return ViewConfiguration.VIEW_PREFIX_CLASSPATH + "/payment/submit";
	}

	public void handle(Payment payment) {
		if (payment != null && payment.getStatus() == Status.wait) {
			payment.setStatus(Status.success);
			payment.setPaymentDate(new Date());
			save(payment);
			applicationEventPublisher.publishEvent(new PaymentEvent(this, payment));
		}
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
}
