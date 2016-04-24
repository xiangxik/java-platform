package com.whenling.plugin.payment.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whenling.module.domain.service.BaseService;
import com.whenling.plugin.payment.model.Payment;
import com.whenling.plugin.payment.model.Payment.Status;
import com.whenling.plugin.payment.repo.PaymentRepository;

@Service
public class PaymentService extends BaseService<Payment, Long> {

	@Autowired
	private PaymentRepository paymentRepository;

	public Payment findBySn(String sn) {
		return paymentRepository.findBySn(sn);
	}

	public void handle(Payment payment) {
		if (payment != null && payment.getStatus() == Status.wait) {
			payment.setStatus(Status.success);
			payment.setPaymentDate(new Date());
			save(payment);
		}
	}
}
