package com.whenling.extension.mdm.support;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.whenling.extension.mdm.support.openssl.OpenSSLExecutor;
import com.whenling.extension.mdm.support.plist.PayloadStream;
import com.whenling.extension.mdm.support.web.PayloadHttpMessageConverter;

@Configuration
public class MovingCastleConfiguration {

	@Autowired
	private ServletContext servletContext;

	@Bean
	public PayloadStream payloadStream() {
		PayloadStream payloadStream = new PayloadStream();
		return payloadStream;
	}

	@Bean
	public WebMvcConfigurerAdapter movingCastleWebMvcConfigurerAdapter() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
				converters.add(new PayloadHttpMessageConverter(payloadStream()));
			}
		};
	}

	@Bean
	public OpenSSLExecutor openSSLExecutor() throws IOException {
		File baseDir = new ServletContextResource(servletContext, "/tmp").getFile();
		if (!baseDir.exists()) {
			baseDir.mkdir();
		}

		return new OpenSSLExecutor(baseDir);
	}

}
