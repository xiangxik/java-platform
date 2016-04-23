package com.whenling.module.domain.json.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

import org.joda.time.DateTime;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

/**
 * joda类型的时间解析器
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午4:51:25
 */
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
