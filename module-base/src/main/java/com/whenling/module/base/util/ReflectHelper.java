package com.whenling.module.base.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 反射工具
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午4:48:51
 */
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
