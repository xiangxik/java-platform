package com.whenling.core.support;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * serlvet配置
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年1月6日 上午12:00:05
 */
@Configuration
@ComponentScan(basePackages = { "com.whenling" }, useDefaultFilters = false, includeFilters = {
		@Filter({ Controller.class }) })
@EnableWebMvc
@EnableSpringDataWebSupport
public class ServletConfiguration {

}
