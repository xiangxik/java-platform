package com.whenling.core.support.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.CompositeResourceLoader;
import org.beetl.core.resource.StartsWithMatcher;
import org.beetl.core.resource.WebAppResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.servlet.ViewResolver;

@Configuration
public class ViewConfiguration {

	@Autowired
	private ServletContext servletContext;

	@Value("${template.location}")
	private String templateLocation;

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
		CompositeResourceLoader compositeResourceLoader = new CompositeResourceLoader();

		try {
			compositeResourceLoader.addResourceLoader(new StartsWithMatcher(templateLocation), new WebAppResourceLoader(
					new ServletContextResource(servletContext, templateLocation).getFile().getAbsolutePath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		compositeResourceLoader.addResourceLoader(new StartsWithMatcher("/views"),
				new ClasspathResourceLoader("/views"));
		compositeResourceLoader.addResourceLoader(new StartsWithMatcher("/com/whenling"),
				new ClasspathResourceLoader("/com/whenling"));
		beetlConfig.setResourceLoader(compositeResourceLoader);
		beetlConfig.setConfigFileResource(new ClassPathResource("/com/whenling/core/support/view/beetl.properties"));

		Map<String, Object> sharedVars = new HashMap<>();
		sharedVars.put("base", servletContext.getContextPath());
		beetlConfig.setSharedVars(sharedVars);
		return beetlConfig;
	}

}
