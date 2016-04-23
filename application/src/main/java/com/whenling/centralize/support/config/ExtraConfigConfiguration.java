package com.whenling.centralize.support.config;

import javax.sql.DataSource;

import org.apache.commons.configuration.DatabaseConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExtraConfigConfiguration {

	@Autowired
	private DataSource dataSource;

	@Bean
	public DatabaseConfiguration mainConfig() {
		return new DefaultDatabaseConfiguration(dataSource, "main", "基础配置");
	}

}
