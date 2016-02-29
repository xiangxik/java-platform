package com.whenling.module.base.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySources;

@Configuration
public class ConfigConfiguration {

	@Autowired
	private static Environment env;

	@Bean
	public static PlaceholderConfigurerSupport placeholderConfigurer() {
		PropertySourcesPlaceholderConfigurer placeholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		placeholderConfigurer.setPropertySources(propertySources());
		return placeholderConfigurer;
	}

	@Bean
	public static PropertySources propertySources() {
		MutablePropertySources propertySources = new MutablePropertySources();
		if (env != null) {
			propertySources.addLast(new PropertySource<Environment>(
					PropertySourcesPlaceholderConfigurer.ENVIRONMENT_PROPERTIES_PROPERTY_SOURCE_NAME, env) {
				@Override
				public String getProperty(String key) {
					return this.source.getProperty(key);
				}
			});
		}

		PropertySource<?> localPropertySource = new PropertySource<org.apache.commons.configuration.Configuration>(
				PropertySourcesPlaceholderConfigurer.LOCAL_PROPERTIES_PROPERTY_SOURCE_NAME,
				StaticConfigurationSupplier.getConfiguration()) {
			@Override
			public Object getProperty(String name) {
				return this.source.getProperty(name);
			}
		};
		propertySources.addFirst(localPropertySource);
		return propertySources;
	}

}
