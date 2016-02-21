package com.whenling.example.web.support;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class ExampleWebConfiguration extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("", "/index.html");
		registry.addRedirectViewController("/", "/index.html");
	}
}
