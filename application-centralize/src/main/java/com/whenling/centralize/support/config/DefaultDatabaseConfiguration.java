package com.whenling.centralize.support.config;

import javax.sql.DataSource;

import org.apache.commons.configuration.DatabaseConfiguration;

public class DefaultDatabaseConfiguration extends DatabaseConfiguration {

	public static final String TABLE = "sys_config";
	public static final String NAME_COLUMN = "_name";
	public static final String KEY_COLUMN = "_key";
	public static final String VALUE_COLUMN = "_value";

	private String displayName;

	private String name;

	public DefaultDatabaseConfiguration(DataSource datasource, String name, String displayName) {
		super(datasource, TABLE, NAME_COLUMN, KEY_COLUMN, VALUE_COLUMN, name);

		this.name = name;
		this.displayName = displayName;
	}

	public String getName() {
		return name;
	}

	public String getDisplayName() {
		return displayName;
	}

}
