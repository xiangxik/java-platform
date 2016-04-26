package com.whenling.plugin.payment.service;

import org.springframework.context.ApplicationEvent;

import com.whenling.plugin.payment.model.Payment;

public class PaymentEvent extends ApplicationEvent {

	private static final long serialVersionUID = 5657119922551999433L;
	private Payment payment;

	public PaymentEvent(Object source, Payment payment) {
		super(source);
		this.payment = payment;
	}

	public Payment getPayment() {
		return payment;
	}

}
