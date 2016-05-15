package com.whenling.centralize;

import java.util.List;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
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
		List<Filter> applicationFilters = Application.getFilters();
		Filter[] filters = new Filter[applicationFilters.size()];
		for (int i = 0; i < applicationFilters.size(); i++) {
			filters[i] = applicationFilters.get(i);
		}
		return filters;
	}

	@Override
	protected void customizeRegistration(Dynamic registration) {
		super.customizeRegistration(registration);

		registration.setMultipartConfig(new MultipartConfigElement("/tmp"));
	}

}
