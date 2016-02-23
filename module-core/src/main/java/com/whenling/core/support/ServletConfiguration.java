package com.whenling.core.support;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.whenling.core.support.config.ConfigurationPlaceholderConfigurer;
import com.whenling.core.support.config.ServletSupport;
import com.whenling.core.support.config.StaticConfigurationSupplier;

/**
 * serlvet配置
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年1月6日 上午12:00:05
 */
@Configuration
@ComponentScan(basePackages = { "com.whenling" }, useDefaultFilters = false, includeFilters = {
		@Filter({ Controller.class }), @Filter({ ServletSupport.class }) })
@EnableWebMvc
@EnableSpringDataWebSupport
public class ServletConfiguration {

	@Autowired
	private PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver;

	@Bean
	public static PlaceholderConfigurerSupport placeholderConfigurer() {
		return new ConfigurationPlaceholderConfigurer(StaticConfigurationSupplier.getConfiguration());
	}

	@PostConstruct
	public void init() {
		pageableHandlerMethodArgumentResolver.setPageParameterName("page");
		pageableHandlerMethodArgumentResolver.setOneIndexedParameters(true);
		pageableHandlerMethodArgumentResolver.setSizeParameterName("limit");
	}
}
