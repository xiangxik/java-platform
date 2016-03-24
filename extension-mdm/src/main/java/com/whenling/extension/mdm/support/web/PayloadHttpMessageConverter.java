package com.whenling.extension.mdm.support.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.ClassUtils;

import com.whenling.extension.mdm.support.payload.BasePayload;
import com.whenling.extension.mdm.support.payload.Payload;
import com.whenling.extension.mdm.support.payload.PayloadContext;
import com.whenling.extension.mdm.support.plist.PayloadStream;

public class PayloadHttpMessageConverter extends AbstractHttpMessageConverter<Payload> {

	private PayloadStream payloadStream;

	public PayloadHttpMessageConverter(PayloadStream payloadStream) {
		super(new MediaType("application", "x-apple-aspen-mdm-checkin", Charset.forName("UTF-8")),
				new MediaType("application", "x-apple-aspen-mdm", Charset.forName("UTF-8")));

		this.payloadStream = payloadStream;
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		return ClassUtils.isAssignable(Payload.class, clazz);
	}

	@Override
	protected Payload readInternal(Class<? extends Payload> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		InputStream inputStream = inputMessage.getBody();
		String xml = IOUtils.toString(inputStream);
		System.out.println(xml);
		return (PayloadContext) payloadStream.fromXML(xml);
	}

	@Override
	protected void writeInternal(Payload value, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		if (value == null) {
			value = new BasePayload();
		}
		String xml = payloadStream.toXML(value);
		System.out.println(xml);
		IOUtils.copy(new ByteArrayInputStream(xml.getBytes("UTF-8")), outputMessage.getBody());
	}

}
