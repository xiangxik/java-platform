package com.whenling.core.support.web;

import java.util.List;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class WebConfiguration extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/core").setViewName("/index");
		registry.addViewController("/core/dashboard").setViewName("/dashboard");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

	}

}
