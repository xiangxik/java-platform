package com.whenling.module.domain.json;

import java.util.Set;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;

public class IncludesPropertyPreFilter implements PropertyPreFilter {

	private final Class<?> clazz;
	private final Set<String> includes;

	public IncludesPropertyPreFilter(Class<?> clazz, Set<String> includes) {
		this.clazz = clazz;
		this.includes = includes;
	}

	@Override
	public boolean apply(JSONSerializer serializer, Object source, String name) {
		if (source == null) {
			return true;
		}

		if (clazz != null && !clazz.isInstance(source)) {
			return true;
		}

		if (includes.size() == 0 || includes.contains(name)) {
			return true;
		}

		return false;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public Set<String> getIncludes() {
		return includes;
	}

}
