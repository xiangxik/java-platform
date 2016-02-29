package com.whenling.module.web.view;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.CompositeResourceLoader;
import org.beetl.core.resource.StartsWithMatcher;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.ViewResolver;

@Configuration
public class ViewConfiguration {

	@Autowired
	private ServletContext servletContext;

	@Value("${viewConfigFile?:com/whenling/module/web/view/beetl.properties}")
	private String viewConfigFileLocation;

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

		beetlConfig.setResourceLoader(viewResourceLoader());
		beetlConfig.setConfigFileResource(new ClassPathResource(viewConfigFileLocation));

		Map<String, Object> sharedVars = new HashMap<>();
		sharedVars.put("base", servletContext.getContextPath());
		beetlConfig.setSharedVars(sharedVars);
		return beetlConfig;
	}

	@Bean
	public CompositeResourceLoader viewResourceLoader() {
		CompositeResourceLoader compositeResourceLoader = new CompositeResourceLoader();
		compositeResourceLoader.addResourceLoader(new StartsWithMatcher("/views"),
				new ClasspathResourceLoader("/views"));
		compositeResourceLoader.addResourceLoader(new StartsWithMatcher("/com/whenling"),
				new ClasspathResourceLoader("/com/whenling"));
		return compositeResourceLoader;
	}

}
