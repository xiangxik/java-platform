package com.whenling.core.support.json.serializers;

import java.io.IOException;
import java.lang.reflect.Type;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.whenling.core.support.entity.Result;

public class ExtjsResultSerializer implements ObjectSerializer {

	public static ExtjsResultSerializer instance = new ExtjsResultSerializer();

	public static final String FIELD_SUCCESS = "success";
	public static final String FIELD_MESSAGE = "msg";

	public static final String FIELD_DATA = "data";

	@Override
	public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
			throws IOException {

		SerializeWriter out = serializer.getWriter();
		if (object == null) {
			out.writeNull();
			return;
		}

		Result result = (Result) object;

		out.write('{');

		out.writeFieldName(FIELD_SUCCESS);
		out.write(result.getCode() == 0);
		out.write(',');

		out.writeFieldName(FIELD_MESSAGE);
		out.writeString(result.getCodeDes());
		out.write(',');

		out.writeFieldName(FIELD_DATA);
		serializer.write(result.getData());

		out.write('}');
	}

}
