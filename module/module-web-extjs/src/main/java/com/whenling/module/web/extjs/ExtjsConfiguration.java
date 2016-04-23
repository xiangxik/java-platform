package com.whenling.module.web.extjs;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.whenling.module.domain.model.Node;
import com.whenling.module.domain.model.Result;
import com.whenling.module.web.extjs.json.serializer.NodeSerializer;
import com.whenling.module.web.extjs.json.serializer.PageSerializer;
import com.whenling.module.web.extjs.json.serializer.ResultSerializer;

/**
 * extjs配置
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午7:11:01
 */
@Configuration
public class ExtjsConfiguration extends WebMvcConfigurerAdapter {

	@Autowired
	private SerializeConfig serializeConfig;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/app/**").addResourceLocations("classpath:/META-INF/app/")
				.setCachePeriod(60 * 60 * 24 * 30);
	}

	@PostConstruct
	public void init() {
		serializeConfig.put(Result.class, ResultSerializer.instance);
		serializeConfig.put(PageImpl.class, PageSerializer.instance);
		serializeConfig.put(Node.class, new NodeSerializer(serializeConfig));
	}
}
