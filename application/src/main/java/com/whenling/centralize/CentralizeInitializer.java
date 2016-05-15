package com.whenling.centralize;

import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.RequestContextFilter;

/**
 * 主应用初始化器
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午4:30:31
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CentralizeInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		Map<String, String> filterChainDefinitionMap = Application.getSecurityFilterChainDefinitionMap();
		filterChainDefinitionMap.put("/admin", DefaultFilter.authc.name());
		filterChainDefinitionMap.put("/admin/logout", DefaultFilter.logout.name());
		filterChainDefinitionMap.put("/admin/**", DefaultFilter.authc.name());

		List<Filter> filters = Application.getFilters();

		DelegatingFilterProxy shiroFilter = new DelegatingFilterProxy("shiroFilter");
		shiroFilter.setTargetFilterLifecycle(true);
		filters.add(shiroFilter);

		DelegatingFilterProxy captchaFilter = new DelegatingFilterProxy("captchaFilter");
		captchaFilter.setTargetFilterLifecycle(true);
		filters.add(captchaFilter);

		filters.add(new RequestContextFilter());
		filters.add(new CharacterEncodingFilter("UTF-8", true));
	}

}
