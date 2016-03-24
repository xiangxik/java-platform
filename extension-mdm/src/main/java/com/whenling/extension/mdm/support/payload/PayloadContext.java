package com.whenling.extension.mdm.support.payload;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class PayloadContext extends HashMap<String, Object> implements Payload {

	private static final long serialVersionUID = 859903789968502861L;

	public String getString(String key) {
		return (String) get(key);
	}

	public Double getDouble(String key) {
		return (Double) get(key);
	}

	public PayloadContext getPayload(String key) {
		return (PayloadContext) get(key);
	}

	public Integer getInteger(String key) {
		return (Integer) get(key);
	}

	public Boolean getBoolean(String key) {
		return (Boolean) get(key);
	}

	public Date getDate(String key) {
		return (Date) get(key);
	}

	public TextData getText(String key) {
		return (TextData) get(key);
	}

	public <T> T[] getArray(String key, Class<T> clazz) {
		Object[] objs = (Object[]) get(key);
		if (objs == null) {
			return null;
		}

		@SuppressWarnings("unchecked")
		T[] array = (T[]) Array.newInstance(clazz, objs.length);
		return Arrays.asList(objs).toArray(array);
	}
}
