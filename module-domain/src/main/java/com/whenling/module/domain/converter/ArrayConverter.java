package com.whenling.module.domain.converter;

import javax.persistence.AttributeConverter;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;

public class ArrayConverter implements AttributeConverter<Object, String> {

	private static final String separator = "@#@";

	private static final Splitter splitter = Splitter.on(separator);
	private static final Joiner joiner = Joiner.on(separator);

	private AttributeConverter<Object, String> elementConverter;

	public ArrayConverter(AttributeConverter<Object, String> elementConverter) {
		this.elementConverter = elementConverter;
	}

	@Override
	public String convertToDatabaseColumn(Object attribute) {
		if (attribute == null) {
			return null;
		}

		String[] values = new String[] {};
		if (attribute.getClass().isArray()) {
			for (Object element : (Object[]) attribute) {
				ArrayUtils.add(values, elementConverter.convertToDatabaseColumn(element));
			}
		}
		return joiner.join(values);
	}

	@Override
	public Object convertToEntityAttribute(String dbData) {
		if (Strings.isNullOrEmpty(dbData)) {
			return null;
		}

		Object[] values = new Object[] {};
		for (String value : splitter.split(dbData)) {
			ArrayUtils.add(values, elementConverter.convertToEntityAttribute(value));
		}
		return values;
	}

}
