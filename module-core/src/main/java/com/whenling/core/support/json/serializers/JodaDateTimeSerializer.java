package com.whenling.core.support.json.serializers;

import java.io.IOException;
import java.lang.reflect.Type;

import org.joda.time.DateTime;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

public class JodaDateTimeSerializer implements ObjectSerializer {

	public static JodaDateTimeSerializer instance = new JodaDateTimeSerializer();

	@Override
	public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
			throws IOException {
		SerializeWriter out = serializer.getWriter();
		if (object == null) {
			out.writeNull();
			return;
		}

		DateTime dateTime = (DateTime) object;
		serializer.write(dateTime.toDate());
	}

}
