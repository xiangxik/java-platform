package com.whenling.centralize.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.whenling.centralize.Application;
import com.whenling.centralize.support.config.ConfigModel;
import com.whenling.centralize.support.config.DefaultDatabaseConfiguration;
import com.whenling.module.base.KeyValueDisplay;
import com.whenling.module.domain.model.Result;

@Controller
@RequestMapping("/admin/setting")
public class SettingController {

	private static final String PRE_CONFIG = "_cf_";
	private static final Joiner JOINER_PARAMETER = Joiner.on(",");

	@Autowired(required = false)
	private Map<String, Configuration> configurations;

	@Autowired
	private Application app;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<KeyValueDisplay> get() {

		List<ConfigModel> settings = app.getSettings();
		List<KeyValueDisplay> configs = new ArrayList<>();

		settings.forEach((setting) -> {

			Configuration config = configurations.get(setting.getConfig());
			KeyValueDisplay configPair = null;
			if (config instanceof DefaultDatabaseConfiguration) {
				configPair = findConfig(((DefaultDatabaseConfiguration) config).getName(), configs);
				if (configPair == null) {
					configPair = new KeyValueDisplay(((DefaultDatabaseConfiguration) config).getDisplayName(),
							((DefaultDatabaseConfiguration) config).getName(), new ArrayList<KeyValueDisplay>());
					configs.add(configPair);
				}
			} else {
				configPair = findConfig(setting.getConfig(), configs);
				if (configPair == null) {
					configPair = new KeyValueDisplay(setting.getConfig(), setting.getConfig(),
							new ArrayList<KeyValueDisplay>());
					configs.add(configPair);
				}
			}

			@SuppressWarnings("unchecked")
			List<KeyValueDisplay> list = (List<KeyValueDisplay>) configPair.getValue();

			String key = setting.getKey();
			key = PRE_CONFIG + setting.getConfig() + "." + key;
			Object value = config == null ? setting.getValue() : config.getProperty(setting.getKey());

			list.add(new KeyValueDisplay(setting.getDisplay(), key, value));
		});

		return configs;
	}

	private KeyValueDisplay findConfig(String configName, List<KeyValueDisplay> configPairs) {
		for (KeyValueDisplay configPair : configPairs) {
			if (Objects.equal(configName, configPair.getKey())) {
				return configPair;
			}
		}
		return null;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Result post(HttpServletRequest request) {
		request.getParameterMap().forEach((name, value) -> {
			if (StringUtils.startsWith(name, PRE_CONFIG)) {
				name = StringUtils.removeStart(name, PRE_CONFIG);
				String dbName = StringUtils.substringBefore(name, ".");
				name = StringUtils.substringAfter(name, ".");
				Configuration config = configurations.get(dbName);
				config.setProperty(name, JOINER_PARAMETER.join(value));
			}
		});

		return Result.success();
	}

}
