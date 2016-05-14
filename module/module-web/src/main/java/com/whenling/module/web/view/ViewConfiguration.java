package com.whenling.module.web.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.beetl.core.Function;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.CompositeResourceLoader;
import org.beetl.core.resource.StartsWithMatcher;
import org.beetl.core.resource.WebAppResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.servlet.ViewResolver;

import com.google.common.collect.Maps;

/**
 * 视图配置
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午7:10:08
 */
@Configuration
public class ViewConfiguration {

	@Autowired
	private ServletContext servletContext;

	@Value("${template.location?:/template/}")
	private String templateLocation;

	@Value("${viewConfigFile?:com/whenling/module/web/view/beetl.properties}")
	private String viewConfigFileLocation;

	@Autowired(required = false)
	private ObjectFactory<Map<String, Function>> functionsFactory;

	@Autowired(required = false)
	private ObjectFactory<Map<String, FunctionPackage>> functionPackagesFactory;

	public final static String VIEW_PREFIX_CLASSPATH = "classpath:";
	public final static String VIEW_PREFIX_TEMPLATE = "template:";

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

		Map<String, Function> functions = functionsFactory.getObject();
		if (functions != null) {
			beetlConfig.setFunctions(functions);
		}

		Map<String, FunctionPackage> functionPackages = functionPackagesFactory.getObject();
		if (functionPackages != null) {
			beetlConfig.setFunctionPackages(Maps.transformValues(functionPackages, (functionPackage) -> {
				return functionPackage;
			}));
		}

		// Map<String, Format> formats = new HashMap<>();
		// formats.put(DateTime.class.getName(), new DateTimeFormat());
		// beetlConfig.setFormats(formats);

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
		compositeResourceLoader.addResourceLoader(new StartsWithMatcher(VIEW_PREFIX_CLASSPATH).withoutPrefix(),
				new ClasspathResourceLoader("/views"));
		try {
			compositeResourceLoader.addResourceLoader(new StartsWithMatcher(VIEW_PREFIX_TEMPLATE),
					new WebAppResourceLoader(new ServletContextResource(servletContext, templateLocation).getFile().getAbsolutePath()));

			compositeResourceLoader.addResourceLoader(new StartsWithMatcher("/WEB-INF").withPrefix(),
					new WebAppResourceLoader(servletContext.getRealPath(".")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return compositeResourceLoader;
	}

}
