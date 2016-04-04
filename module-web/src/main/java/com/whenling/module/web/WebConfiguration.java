package com.whenling.module.web;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.xml.transform.Source;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.geo.format.DistanceFormatter;
import org.springframework.data.geo.format.PointFormatter;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.querydsl.binding.QuerydslBindingsFactory;
import org.springframework.data.repository.support.DomainClassConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.ProxyingHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.annotation.ModelAttributeMethodProcessor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.whenling.module.domain.service.EntityService;
import com.whenling.module.web.converter.FastjsonHttpMessageConverter;
import com.whenling.module.web.method.DefaultPredicateArgumentResolver;
import com.whenling.module.web.method.EntityModelAttributeMethodProcessor;

/**
 * web配置
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午7:10:42
 */
@ServletSupport
@Component
public class WebConfiguration extends WebMvcConfigurerAdapter implements InitializingBean {

	@Autowired
	private SerializeConfig serializeConfig;

	@Autowired
	private ParserConfig parserConfig;

	@Autowired
	private ApplicationContext context;

	@Autowired
	@Qualifier("mvcConversionService")
	private ObjectFactory<ConversionService> conversionService;

	@Autowired
	private ObjectFactory<EntityService> entityService;

	@Autowired
	private RequestMappingHandlerAdapter handlerAdapter;

	@Bean
	public MultipartResolver multipartResolver() {
		StandardServletMultipartResolver resolver = new StandardServletMultipartResolver();
		resolver.setResolveLazily(true);
		return resolver;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("locale");
		return localeChangeInterceptor;
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(new Locale("zh_CN"));
		return localeResolver;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/admin").setViewName("classpath:/admin/index");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/assets/**").addResourceLocations("/assets/", "classpath:/META-INF/assets/")
				.setCachePeriod(60 * 60 * 24 * 30);

		registry.addResourceHandler("/upload/**").addResourceLocations("/upload/");
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		stringConverter.setWriteAcceptCharset(false);

		converters.add(new ByteArrayHttpMessageConverter());
		converters.add(stringConverter);
		converters.add(new ResourceHttpMessageConverter());
		converters.add(new SourceHttpMessageConverter<Source>());
		converters.add(new AllEncompassingFormHttpMessageConverter());
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		FastjsonHttpMessageConverter fastjsonMessageConverter = new FastjsonHttpMessageConverter(serializeConfig,
				parserConfig);
		converters.add(fastjsonMessageConverter);
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(DistanceFormatter.INSTANCE);
		registry.addFormatter(PointFormatter.INSTANCE);

		if (!(registry instanceof FormattingConversionService)) {
			return;
		}

		FormattingConversionService conversionService = (FormattingConversionService) registry;

		DomainClassConverter<FormattingConversionService> converter = new DomainClassConverter<FormattingConversionService>(
				conversionService);
		converter.setApplicationContext(context);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(sortResolver());
		argumentResolvers.add(pageableResolver());

		ProxyingHandlerMethodArgumentResolver resolver = new ProxyingHandlerMethodArgumentResolver(
				conversionService.getObject());
		resolver.setBeanFactory(context);
		resolver.setResourceLoader(context);

		argumentResolvers.add(resolver);

		argumentResolvers.add(0, querydslPredicateArgumentResolver());
	}

	@Bean
	public PageableHandlerMethodArgumentResolver pageableResolver() {
		PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver = new PageableHandlerMethodArgumentResolver(
				sortResolver());
		pageableHandlerMethodArgumentResolver.setPageParameterName("page");
		pageableHandlerMethodArgumentResolver.setOneIndexedParameters(true);
		pageableHandlerMethodArgumentResolver.setSizeParameterName("limit");
		return pageableHandlerMethodArgumentResolver;
	}

	@Bean
	public SortHandlerMethodArgumentResolver sortResolver() {
		return new SortHandlerMethodArgumentResolver();
	}

	@Lazy
	@Bean
	public DefaultPredicateArgumentResolver querydslPredicateArgumentResolver() {
		return new DefaultPredicateArgumentResolver(querydslBindingsFactory(), conversionService.getObject());
	}

	@Lazy
	@Bean
	public QuerydslBindingsFactory querydslBindingsFactory() {
		QuerydslBindingsFactory querydslBindingsFactory = new QuerydslBindingsFactory(
				SimpleEntityPathResolver.INSTANCE);
		querydslBindingsFactory.setApplicationContext(context);
		return querydslBindingsFactory;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		List<HandlerMethodArgumentResolver> argumentResolvers = handlerAdapter.getArgumentResolvers();
		List<HandlerMethodArgumentResolver> newArgumentResolvers = new ArrayList<>(argumentResolvers);
		int indexArg = findIndexOfModelAttributeMethodProcessor(argumentResolvers);
		newArgumentResolvers.set(indexArg, new EntityModelAttributeMethodProcessor(entityService.getObject(),
				conversionService.getObject(), false));
		handlerAdapter.setArgumentResolvers(newArgumentResolvers);

		List<HandlerMethodArgumentResolver> initBinderArgumentResolvers = handlerAdapter
				.getInitBinderArgumentResolvers();
		List<HandlerMethodArgumentResolver> newInitBinderArgumentResolvers = new ArrayList<>(
				initBinderArgumentResolvers);
		int indexBinder = findIndexOfModelAttributeMethodProcessor(handlerAdapter.getInitBinderArgumentResolvers());
		newInitBinderArgumentResolvers.set(indexBinder, new EntityModelAttributeMethodProcessor(
				entityService.getObject(), conversionService.getObject(), false));
		handlerAdapter.setInitBinderArgumentResolvers(newInitBinderArgumentResolvers);
	}

	private int findIndexOfModelAttributeMethodProcessor(List<HandlerMethodArgumentResolver> resolvers) {
		for (int i = 0; i < resolvers.size(); i++) {
			HandlerMethodArgumentResolver resolver = resolvers.get(i);
			if (resolver instanceof ModelAttributeMethodProcessor) {
				return i;
			}
		}

		return -1;
	}

}
