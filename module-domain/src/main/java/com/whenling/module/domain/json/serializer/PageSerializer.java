package com.whenling.module.domain.json.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

import org.springframework.data.domain.Page;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

public class PageSerializer implements ObjectSerializer {

	public static PageSerializer instance = new PageSerializer();

	public static final String DEFAULT_TOTAL_FIELD = "total";
	public static final String DEFAULT_CURRENT_FIELD = "current";
	public static final String DEFAULT_ROWCOUNT_FIELD = "rowCount";
	public static final String DEFAULT_ROWS_FIELD = "rows";

	@Override
	public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
			throws IOException {
		SerializeWriter out = serializer.getWriter();
		if (object == null) {
			out.writeNull();
			return;
		}

		Page<?> page = (Page<?>) object;

		out.write('{');

		out.writeFieldName(DEFAULT_CURRENT_FIELD);
		out.writeInt(page.getNumber());
		out.write(',');

		out.writeFieldName(DEFAULT_ROWCOUNT_FIELD);
		out.writeInt(page.getSize());
		out.write(',');

		out.writeFieldName(DEFAULT_TOTAL_FIELD);
		out.writeLong(page.getTotalElements());
		out.write(',');

		out.writeFieldName(DEFAULT_ROWS_FIELD);
		serializer.write(page.getContent());

		out.write('}');
	}

}
