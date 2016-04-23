package com.whenling.centralize.support.config;

public class ConfigModel {

	private String config;
	private String display;
	private String key;
	private Object value;

	private ValueType valueType = ValueType.String;

	public ConfigModel(String config, String display, String key, Object value) {
		this.config = config;
		this.display = display;
		this.key = key;
		this.value = value;
	}

	public ConfigModel(String config, String display, String key, Object value, ValueType valueType) {
		this.config = config;
		this.display = display;
		this.key = key;
		this.value = value;
		this.valueType = valueType;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public ValueType getValueType() {
		return valueType;
	}

	public void setValueType(ValueType valueType) {
		this.valueType = valueType;
	}

	public enum ValueType {
		String, Integer, Boolean, Enumeration, Double
	}
}
