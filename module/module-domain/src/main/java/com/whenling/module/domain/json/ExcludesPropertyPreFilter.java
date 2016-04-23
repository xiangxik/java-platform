package com.whenling.module.domain.json;

import java.util.Set;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;

public class ExcludesPropertyPreFilter implements PropertyPreFilter {

	private final Class<?> clazz;
	private final Set<String> excludes;

	public ExcludesPropertyPreFilter(Class<?> clazz, Set<String> excludes) {
		this.clazz = clazz;
		this.excludes = excludes;
	}

	@Override
	public boolean apply(JSONSerializer serializer, Object source, String name) {
		if (source == null) {
			return true;
		}

		if (clazz != null && !clazz.isInstance(source)) {
			return true;
		}

		if (excludes.size() == 0 || !excludes.contains(name)) {
			return true;
		}

		return false;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public Set<String> getExcludes() {
		return excludes;
	}

}
