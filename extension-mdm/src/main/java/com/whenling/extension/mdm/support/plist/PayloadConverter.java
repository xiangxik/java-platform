package com.whenling.extension.mdm.support.plist;

import java.beans.PropertyDescriptor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.ClassUtils;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.whenling.extension.mdm.support.payload.BasePayload;
import com.whenling.extension.mdm.support.payload.PayloadContext;
import com.whenling.extension.mdm.support.payload.TextData;

public class PayloadConverter implements Converter {

	public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	@Override
	public boolean canConvert(@SuppressWarnings("rawtypes") Class type) {
		return ClassUtils.isAssignable(BasePayload.class, type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		if (source instanceof BasePayload) {
			BeanWrapperImpl wrapper = new BeanWrapperImpl(source);

			PropertyDescriptor[] propertyDescriptors = wrapper.getPropertyDescriptors();

			writer.startNode("dict");
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				String propertyName = propertyDescriptor.getName();
				if (Objects.equal("class", propertyName)) {
					continue;
				}
				String key = Objects.equal(propertyName, "iTunesStoreID") ? propertyName
						: StringUtils.capitalize(propertyName);
				Object value = wrapper.getPropertyValue(propertyName);
				if (value != null) {
					writer.startNode("key");
					writer.setValue(key);
					writer.endNode();

					writeValue(value, writer, context);
				}
			}
			writer.endNode();
		} else {
			writeValue(source, writer, context);
		}
	}

	protected void writeValue(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {

		if (value instanceof TextData) {
			writer.startNode("data");
			writer.setValue(((TextData) value).getData());
			writer.endNode();
		} else if (value instanceof String) {
			writer.startNode("string");
			writer.setValue((String) value);
			writer.endNode();
		} else if (value instanceof Date) {
			writer.startNode("date");
			writer.setValue(DateFormatUtils.format((Date) value, DATE_FORMAT));
			writer.endNode();
		} else if (value instanceof Boolean) {
			if ((Boolean) value) {
				writer.startNode("true");
			} else {
				writer.startNode("false");
			}
			writer.endNode();
		} else if (value instanceof Integer) {
			writer.startNode("integer");
			writer.setValue(String.valueOf(value));
			writer.endNode();
		} else if (value instanceof Long) {
			writer.startNode("integer");
			writer.setValue(String.valueOf(value));
			writer.endNode();
		} else if (value instanceof Double) {
			writer.startNode("real");
			writer.setValue(String.valueOf(value));
			writer.endNode();
		} else if (value instanceof BasePayload) {
			marshal(value, writer, context);
		} else if (value.getClass().isArray()) {
			writer.startNode("array");
			for (Object item : (Object[]) value) {
				marshal(item, writer, context);
			}
			writer.endNode();
		} else {
			throw new IllegalArgumentException();
		}

	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		if ("plist".equals(reader.getNodeName())) {
			reader.moveDown();
		}

		return readValue(reader);
	}

	private Object readValue(HierarchicalStreamReader reader) {

		Object retVal = null;
		switch (reader.getNodeName()) {
		case "dict":
			PayloadContext map = new PayloadContext();

			while (reader.hasMoreChildren()) {
				reader.moveDown();

				String key = reader.getValue();
				reader.moveUp();

				reader.moveDown();
				map.put(key, readValue(reader));
				reader.moveUp();
			}

			retVal = map;
			break;
		case "data":
			retVal = new TextData(reader.getValue());
			break;
		case "string":
			retVal = reader.getValue();
			break;
		case "integer":
			String value = reader.getValue();
			if (!Strings.isNullOrEmpty(value) && value.length() > 9) {
				retVal = Long.parseLong(value);
			} else {
				retVal = Integer.parseInt(reader.getValue());
			}
			break;
		case "real":
			retVal = Double.parseDouble(reader.getValue());
			break;
		case "true":
			retVal = true;
			break;
		case "false":
			retVal = false;
			break;
		case "date":
			SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
			try {
				retVal = dateFormat.parse(reader.getValue());
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
			break;
		case "array":
			List<Object> list = new ArrayList<>();

			while (reader.hasMoreChildren()) {
				reader.moveDown();
				list.add(readValue(reader));
				reader.moveUp();
			}

			retVal = list.toArray();
			break;
		default:
			throw new IllegalArgumentException();
		}

		return retVal;

	}
}
