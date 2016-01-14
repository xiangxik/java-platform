package com.whenling.core.support.view;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.ViewResolver;

@Configuration
public class ViewConfiguration {

	@Autowired
	private ServletContext servletContext;

	@Bean
	public ViewResolver beetlViewResolver() {
		BeetlSpringViewResolver viewResolver = new BeetlSpringViewResolver();
		viewResolver.setConfig(beetlConfig());
		viewResolver.setContentType("text/html;charset=UTF-8");
		viewResolver.setSuffix(".html");
		viewResolver.setCache(true);
		return viewResolver;
	}

	@Bean(initMethod = "init")
	public BeetlGroupUtilConfiguration beetlConfig() {
		BeetlGroupUtilConfiguration beetlConfig = new BeetlGroupUtilConfiguration();
		beetlConfig.setResourceLoader(new ClasspathResourceLoader());
		beetlConfig.setConfigFileResource(new ClassPathResource("/com/whenling/core/support/view/beetl.properties"));

		Map<String, Object> sharedVars = new HashMap<>();
		sharedVars.put("base", servletContext.getContextPath());
		beetlConfig.setSharedVars(sharedVars);
		return beetlConfig;
	}
}
