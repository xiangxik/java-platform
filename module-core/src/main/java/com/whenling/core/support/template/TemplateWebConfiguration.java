package com.whenling.core.support.template;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import com.google.common.collect.Lists;
import com.whenling.core.support.config.ServletSupport;

@Configuration
@ServletSupport
public class TemplateWebConfiguration {

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private ApplicationContext applicationContext;

	@Value("${template.location}")
	private String templateLocation;

	@Bean
	public HandlerMapping templateHandlerMapping() {
		Map<String, HttpRequestHandler> urlMap = new LinkedHashMap<String, HttpRequestHandler>();
		urlMap.put("/**", templateHttpRequestHandler());

		SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
		handlerMapping.setOrder(Ordered.LOWEST_PRECEDENCE);
		handlerMapping.setUrlMap(urlMap);
		return handlerMapping;

	}

	@Bean
	public TemplateHttpRequestHandler templateHttpRequestHandler() {
		TemplateHttpRequestHandler handler = new TemplateHttpRequestHandler();
		handler.setLocations(Lists.newArrayList(applicationContext.getResource(templateLocation)));
		handler.setServletContext(servletContext);
		handler.setApplicationContext(applicationContext);
		return handler;
	}
}
