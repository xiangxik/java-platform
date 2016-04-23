package com.whenling.extension.cms;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;

import com.whenling.module.base.config.StaticConfigurationSupplier;

/**
 * CMS初始化器
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午4:35:11
 */
@Order(0)
public class CmsInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		StaticConfigurationSupplier.append("/com/whenling/extension/cms/cms.properties");
	}

}
