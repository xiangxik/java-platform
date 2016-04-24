package com.whenling.plugin.payment.repo;

import com.whenling.module.domain.repository.BaseRepository;
import com.whenling.plugin.payment.model.Payment;

public interface PaymentRepository extends BaseRepository<Payment, Long> {

	Payment findBySn(String sn);
}
