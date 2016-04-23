package com.whenling.module.base;

public class KeyValueDisplay {
	private Object key;
	private Object value;
	private String display;

	public KeyValueDisplay(String display, Object key, Object value) {
		this.display = display;
		this.key = key;
		this.value = value;
	}

	public String getDisplay() {
		return display;
	}

	public Object getKey() {
		return key;
	}

	public Object getValue() {
		return value;
	}

}
