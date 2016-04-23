package com.whenling.centralize.support.config;

import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("setting")
public class ConfigurationFunction implements Function {

	@Autowired
	private Map<String, Configuration> configurations;

	@Override
	public Object call(Object[] paras, Context ctx) {
		if (paras.length > 0) {
			String parameter = (String) paras[0];
			String db = StringUtils.substringBefore(parameter, ".");
			String key = StringUtils.substringAfter(parameter, ".");

			if (paras.length > 1) {
				configurations.get(db).getString(key, (String) paras[1]);
			} else {
				return configurations.get(db).getString(key);
			}

		}

		return null;
	}

}
