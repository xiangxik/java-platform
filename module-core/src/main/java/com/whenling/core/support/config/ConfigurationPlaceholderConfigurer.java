package com.whenling.core.support.config;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.util.StringValueResolver;

public class ConfigurationPlaceholderConfigurer extends PlaceholderConfigurerSupport {

	private Configuration configuration;

	public ConfigurationPlaceholderConfigurer(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
			throws BeansException {
		doProcessProperties(beanFactory, new StringValueResolver() {
			@Override
			public String resolveStringValue(String strVal) {
				return StrSubstitutor.replace(strVal, new PropertyMap(), placeholderPrefix, placeholderSuffix);
			}
		});
	}

	class PropertyMap extends AbstractMap<String, String> {

		@Override
		public String get(Object key) {
			return configuration.getString((String) key);
		}

		@SuppressWarnings("unchecked")
		@Override
		public Set<java.util.Map.Entry<String, String>> entrySet() {
			return Collections.EMPTY_SET;
		}
	}

}
