package com.whenling.module.web.extjs.json.serializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.google.common.base.Objects;
import com.whenling.module.domain.model.Result;
import com.whenling.module.domain.model.Result.Code;

/**
 * extj结果的解析器
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午4:51:13
 */
public class ResultSerializer implements ObjectSerializer {

	public static ResultSerializer instance = new ResultSerializer();

	public static final String FIELD_SUCCESS = "success";
	public static final String FIELD_MESSAGE = "msg";
	public static final String FIELD_CODE = "code";
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

		Map<String, Object> extraProperties = result.getExtraProperties();
		for (Entry<String, Object> entry : extraProperties.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();

			out.writeFieldName(key);
			serializer.write(value);
			out.write(',');
		}

		out.writeFieldName(FIELD_SUCCESS);
		out.write(Objects.equal(result.getCode(), Code.success));
		out.write(',');

		out.writeFieldName(FIELD_CODE);
		out.writeString(result.getCode().name());
		out.write(',');

		out.writeFieldName(FIELD_MESSAGE);
		out.writeString(result.getMessage());
		out.write(',');

		out.writeFieldName(FIELD_DATA);
		serializer.write(result.getData());

		out.write('}');
	}

}
