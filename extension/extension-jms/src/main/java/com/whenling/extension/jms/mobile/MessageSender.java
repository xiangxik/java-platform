package com.whenling.extension.jms.mobile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class MessageSender {

	@Value("${jms.mobileCaptchaUrl?:http://120.24.167.205/msg/HttpSendSM}")
	private String mobileCaptchaUrl;
	
	@Value("${jms.mobileCaptchaAccount?:gzdlkj_iplucky}")
	private String mobileCaptchaAccount;
	
	@Value("${jms.mobileCaptchaPassword?:GZdlkj}")
	private String mobileCaptchaPassword;

	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

	public Result send(String content, String... phoneNumbers) {
		Assert.notEmpty(phoneNumbers);

		StringBuffer buffer = new StringBuffer();
		for (String phoneNumber : phoneNumbers) {
			buffer.append(phoneNumber).append(",");
		}

		HttpUriRequest request = RequestBuilder.post(mobileCaptchaUrl)
				.setCharset(Charset.forName("utf-8")).addParameter("account", mobileCaptchaAccount)
				.addParameter("pswd", mobileCaptchaPassword)
				.addParameter("mobile", StringUtils.removeEndIgnoreCase(buffer.toString(), ","))
				.addParameter("msg", content).addParameter("needstatus", "true").build();
		try {
			String response = HttpClientBuilder.create().build().execute(request, new BasicResponseHandler());

			Result result = new Result();
			String[] tmps = response.split("\n");
			String[] dateAndCode = tmps[0].split(",");
			try {
				result.setResponseDate(simpleDateFormat.parse(dateAndCode[0]));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			result.setResponseCode(dateAndCode[1]);
			if ("0".equals(dateAndCode[1])) {
				result.setMessageId(tmps[1]);
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		throw new RuntimeException();
	}

	public static class Result {
		private Date responseDate;
		private String responseCode;
		private String messageId;

		public Date getResponseDate() {
			return responseDate;
		}

		public void setResponseDate(Date responseDate) {
			this.responseDate = responseDate;
		}

		public String getResponseCode() {
			return responseCode;
		}

		public void setResponseCode(String responseCode) {
			this.responseCode = responseCode;
		}

		public String getMessageId() {
			return messageId;
		}

		public void setMessageId(String messageId) {
			this.messageId = messageId;
		}

	}
}
