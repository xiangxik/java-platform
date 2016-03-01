package com.whenling.extension.cms.support.template;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.beetl.core.resource.CompositeResourceLoader;
import org.beetl.core.resource.StartsWithMatcher;
import org.beetl.core.resource.WebAppResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import com.google.common.collect.Lists;
import com.whenling.module.web.ServletSupport;

/**
 * CMS配置
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午4:34:07
 */
@Configuration
@ServletSupport
public class TemplateWebConfiguration {

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private ApplicationContext applicationContext;

	@Value("${template.location}")
	private String templateLocation;

	@Autowired
	private CompositeResourceLoader compositeResourceLoader;

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

	@PostConstruct
	public void init() {
		try {
			compositeResourceLoader.addResourceLoader(new StartsWithMatcher(templateLocation), new WebAppResourceLoader(
					new ServletContextResource(servletContext, templateLocation).getFile().getAbsolutePath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
