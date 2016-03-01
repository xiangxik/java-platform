package com.whenling.centralize;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.whenling.module.web.RootConfiguration;
import com.whenling.module.web.ServletConfiguration;

/**
 * Web初始化器
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年1月5日 下午11:07:04
 */
@Order(Ordered.LOWEST_PRECEDENCE)
public class SpringWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { RootConfiguration.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { ServletConfiguration.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Filter[] getServletFilters() {
		DelegatingFilterProxy shiroFilter = new DelegatingFilterProxy("shiroFilter");
		shiroFilter.setTargetFilterLifecycle(true);

		DelegatingFilterProxy captchaFilter = new DelegatingFilterProxy("captchaFilter");
		captchaFilter.setTargetFilterLifecycle(true);

		return new Filter[] { new CharacterEncodingFilter("UTF-8", true), new RequestContextFilter(), captchaFilter,
				shiroFilter };
	}

	@Override
	protected void customizeRegistration(Dynamic registration) {
		super.customizeRegistration(registration);

		registration.setMultipartConfig(new MultipartConfigElement("/tmp"));
	}

}
