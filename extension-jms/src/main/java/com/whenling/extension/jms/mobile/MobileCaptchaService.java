package com.whenling.extension.jms.mobile;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.google.common.base.Objects;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@Component
public class MobileCaptchaService {

	private Cache<String, String> CACHE = CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.HOURS).build();

	private Map<String, Integer> DAILY_RECORD = new LinkedHashMap<>();
	
	@Autowired
	private MessageSender messageSender;

	public void sendCaptchaCode(String phoneNumber) throws MobileCaptchaException {
		Integer times = getTimesAndIncrease(phoneNumber);
		if (times > 8) {
			throw new ExceedMaxDailyTimesException();
		}

		String captchaCode = RandomStringUtils.randomNumeric(4);
		messageSender.send(MessageFormat.format("验证码为{0}，2个小时内有效。", captchaCode), phoneNumber);
		CACHE.put(phoneNumber, captchaCode);
	}

	public boolean validateResponse(String phoneNumber, String captchaCode) {
		Assert.notNull(phoneNumber);
		Assert.notNull(captchaCode);

		return Objects.equal(getCaptchaCode(phoneNumber), captchaCode);
	}

	public String getCaptchaCode(String phoneNumber) {
		return CACHE.getIfPresent(phoneNumber);
	}

	public Integer getTimes(String phoneNumber) {
		Integer times = DAILY_RECORD.get(phoneNumber);
		if (times == null) {
			times = 0;
		}
		return times;
	}

	public Integer getTimesAndIncrease(String phoneNumber) {
		Integer times = getTimes(phoneNumber);
		times = times + 1;
		DAILY_RECORD.put(phoneNumber, times);
		return times;
	}

	@Scheduled(cron = "0 0 0 * * ?")
	public void clearDailyRecord() {
		DAILY_RECORD.clear();
	}
}
