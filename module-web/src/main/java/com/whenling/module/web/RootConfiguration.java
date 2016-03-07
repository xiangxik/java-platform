package com.whenling.module.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 根配置
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年1月5日 下午11:59:47
 */
@Configuration
@ComponentScan(basePackages = { "com.whenling" }, excludeFilters = {
		@ComponentScan.Filter(value = Controller.class, type = FilterType.ANNOTATION),
		@ComponentScan.Filter(value = EnableWebMvc.class, type = FilterType.ANNOTATION),
		@ComponentScan.Filter(value = ServletSupport.class, type = FilterType.ANNOTATION) })
public class RootConfiguration {

	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(5);
		threadPoolTaskExecutor.setMaxPoolSize(50);
		threadPoolTaskExecutor.setQueueCapacity(1000);
		threadPoolTaskExecutor.setKeepAliveSeconds(60);
		return threadPoolTaskExecutor;
	}

}
