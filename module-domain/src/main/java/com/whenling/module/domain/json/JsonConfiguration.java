package com.whenling.module.domain.json;

import org.joda.time.DateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.whenling.module.domain.json.serializer.JodaDateTimeSerializer;
import com.whenling.module.domain.json.serializer.TreeSerializer;
import com.whenling.module.domain.model.TreeImpl;

/**
 * json配置
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午4:52:56
 */
@Configuration
public class JsonConfiguration {

	@Bean
	public SerializeConfig serializeConfig() {
		SerializeConfig serializeConfig = SerializeConfig.getGlobalInstance();
		serializeConfig.put(DateTime.class, JodaDateTimeSerializer.instance);
		serializeConfig.put(TreeImpl.class, TreeSerializer.instance);
		return serializeConfig;
	}

	@Bean
	public ParserConfig parserConfig() {
		ParserConfig parserConfig = ParserConfig.getGlobalInstance();
		return parserConfig;
	}

}
