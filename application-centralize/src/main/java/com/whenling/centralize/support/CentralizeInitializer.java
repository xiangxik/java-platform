package com.whenling.centralize.support;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;

import com.whenling.module.base.config.StaticConfigurationSupplier;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class CentralizeInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		StaticConfigurationSupplier.prepend("/com/whenling/centralize/config.properties");
	}

}
