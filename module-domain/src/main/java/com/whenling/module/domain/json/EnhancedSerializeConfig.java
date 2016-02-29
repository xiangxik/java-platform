package com.whenling.module.domain.json;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;

public class EnhancedSerializeConfig extends SerializeConfig {

	private final static EnhancedSerializeConfig globalInstance = new EnhancedSerializeConfig();

	private Map<Class<?>, ObjectSerializer> assignableSerializerMap = new HashMap<>();

	@Override
	public ObjectSerializer getObjectWriter(Class<?> clazz) {
		ObjectSerializer writer = get(clazz);
		if (writer == null) {
			for (java.util.Map.Entry<Class<?>, ObjectSerializer> assignableSerializerEntry : assignableSerializerMap
					.entrySet()) {
				if (assignableSerializerEntry.getKey().isAssignableFrom(clazz)) {
					put(clazz, assignableSerializerEntry.getValue());
				}
			}
		}
		return super.getObjectWriter(clazz);
	}

	public static EnhancedSerializeConfig getGlobalInstance() {
		return globalInstance;
	}

	public void putAssignableTo(Class<?> clazz, ObjectSerializer objectSerializer) {
		assignableSerializerMap.put(clazz, objectSerializer);
	}

}
