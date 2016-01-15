package com.whenling.core.support.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectHelper {

	@SuppressWarnings("unchecked")
	public static <T> Class<T> findParameterizedType(Class<?> clazz, int index) {
		Type parameterizedType = clazz.getGenericSuperclass();
		if (!(parameterizedType instanceof ParameterizedType)) {
			parameterizedType = clazz.getSuperclass().getGenericSuperclass();
		}
		if (!(parameterizedType instanceof ParameterizedType)) {
			return null;
		}
		Type[] actualTypeArguments = ((ParameterizedType) parameterizedType).getActualTypeArguments();
		if (actualTypeArguments == null || actualTypeArguments.length == 0) {
			return null;
		}
		return (Class<T>) actualTypeArguments[index];
	}
}
