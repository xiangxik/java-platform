package com.whenling.centralize;

import javax.annotation.Resource;

import org.apache.commons.configuration.Configuration;
import org.springframework.stereotype.Component;

@Component
public class MainSetting {

	@Resource(name = "mainConfig")
	private Configuration configuration;

}
