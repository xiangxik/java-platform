package com.whenling.core.support.json;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.whenling.core.support.entity.Result;
import com.whenling.core.support.json.serializers.ExtjsResultSerializer;
import com.whenling.core.support.json.serializers.JodaDateTimeSerializer;
import com.whenling.core.support.json.serializers.PageSerializer;

@Configuration
public class JsonConfiguration {

	@Bean
	public SerializeConfig serializeConfig() {
		SerializeConfig serializeConfig = SerializeConfig.getGlobalInstance();
		serializeConfig.put(PageImpl.class, PageSerializer.instance);
		serializeConfig.put(DateTime.class, JodaDateTimeSerializer.instance);
		serializeConfig.put(Result.class, ExtjsResultSerializer.instance);
		return serializeConfig;
	}

	@Bean
	public ParserConfig parserConfig() {
		ParserConfig parserConfig = ParserConfig.getGlobalInstance();
		return parserConfig;
	}

	@Bean
	public WebMvcConfigurer jsonWebMvcConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
				FastjsonHttpMessageConverter fastjsonMessageConverter = new FastjsonHttpMessageConverter(
						serializeConfig(), parserConfig());
				converters.add(fastjsonMessageConverter);
			}
		};
	}

}
