package com.whenling.module.base.util;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.google.common.base.Objects;

/**
 * 属性工具
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午4:48:43
 */
public class PropertyUtil {

	private static Map<Class<?>, PropertyDescriptor[]> propertyDescriptorCache = new HashMap<>();

	public static Class<?> getPropertyType(Class<?> beanClass, String propertyName) {
		PropertyDescriptor[] propertyDescriptors = propertyDescriptorCache.get(beanClass);
		if (propertyDescriptors == null) {
			propertyDescriptors = PropertyUtils.getPropertyDescriptors(beanClass);
			propertyDescriptorCache.put(beanClass, propertyDescriptors);
		}
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			if (Objects.equal(propertyDescriptor.getName(), propertyName)) {
				return propertyDescriptor.getPropertyType();
			}
		}

		return null;
	}

}
