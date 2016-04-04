package com.whenling.module.cache;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.cache.interceptor.SimpleKeyGenerator;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.whenling.module.domain.model.BaseEntity;
import com.whenling.module.domain.service.BaseService;

/**
 * 缓存的key生成器
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午4:50:03
 */
public class BaseKeyGenerator extends SimpleKeyGenerator {

	@Override
	public Object generate(Object target, Method method, Object... params) {
		if (target instanceof BaseService) {
			if (params.length == 0) {
				return SimpleKey.EMPTY;
			}
			Object[] newParams = new Object[params.length];
			for (int i = 0; i < params.length; i++) {
				newParams[i] = transformer(params[i]);
			}

			return new SimpleKey(newParams);
		}

		return super.generate(target, method, params);
	}

	public static Object transformer(Object param) {
		if (param instanceof BaseEntity) {
			return ((BaseEntity<?>) param).getId();
		}

		if (param instanceof Iterable) {
			return Lists.newArrayList(Iterables.transform((Iterable<?>) param, BaseKeyGenerator::transformer));
		}

		return param;
	}
}
