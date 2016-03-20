package com.whenling.extension.mall;

import javax.sql.DataSource;

import org.apache.commons.configuration.DatabaseConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.whenling.centralize.support.config.DefaultDatabaseConfiguration;

@Configuration
public class MallConfiguration {

	@Autowired
	private DataSource dataSource;

	@Bean
	public DatabaseConfiguration mallConfig() {
		return new DefaultDatabaseConfiguration(dataSource, "mall", "商城配置");
	}

}
