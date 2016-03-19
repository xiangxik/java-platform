package com.whenling.module.web.method;

import java.net.URLDecoder;
import java.util.List;

import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.QuerydslBindingsFactory;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.querydsl.binding.QuerydslPredicateBuilder;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;
import org.springframework.data.web.querydsl.QuerydslPredicateArgumentResolver;
import org.springframework.util.ClassUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mysema.query.types.Predicate;
import com.whenling.module.web.controller.EntityController;

public class DefaultPredicateArgumentResolver implements HandlerMethodArgumentResolver {

	private final QuerydslBindingsFactory bindingsFactory;
	private final QuerydslPredicateBuilder predicateBuilder;

	/**
	 * Creates a new {@link QuerydslPredicateArgumentResolver} using the given
	 * {@link ConversionService}.
	 * 
	 * @param factory
	 * @param conversionService
	 *            defaults to {@link DefaultConversionService} if
	 *            {@literal null}.
	 */
	public DefaultPredicateArgumentResolver(QuerydslBindingsFactory factory, ConversionService conversionService) {

		this.bindingsFactory = factory;
		this.predicateBuilder = new QuerydslPredicateBuilder(
				conversionService == null ? new DefaultConversionService() : conversionService,
				factory.getEntityPathResolver());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.method.support.HandlerMethodArgumentResolver#
	 * supportsParameter(org.springframework.core.MethodParameter)
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {

		if (Predicate.class.equals(parameter.getParameterType())) {
			return true;
		}

		if (parameter.hasParameterAnnotation(QuerydslPredicate.class)) {
			throw new IllegalArgumentException(
					String.format("Parameter at position %s must be of type Predicate but was %s.",
							parameter.getParameterIndex(), parameter.getParameterType()));
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.method.support.HandlerMethodArgumentResolver#
	 * resolveArgument(org.springframework.core.MethodParameter,
	 * org.springframework.web.method.support.ModelAndViewContainer,
	 * org.springframework.web.context.request.NativeWebRequest,
	 * org.springframework.web.bind.support.WebDataBinderFactory)
	 */
	@Override
	public Predicate resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();

		String filterString = webRequest.getParameter("filter");
		if (!Strings.isNullOrEmpty(filterString)) {
			filterString = URLDecoder.decode(filterString, "UTF-8");
			Object filters = JSON.parse(filterString);

			if (filters instanceof JSONArray) {
				for (Object filter : (JSONArray) filters) {
					if (filter instanceof JSONObject) {

						String property = ((JSONObject) filter).getString("property");

						Object value = ((JSONObject) filter).get("value");
						List<String> valueList = null;
						if (value instanceof JSONArray) {
							valueList = Lists.transform((JSONArray) value, (jsonObject) -> {
								return (String) jsonObject;
							});
						} else {
							if (value != null) {
								valueList = Lists.newArrayList(value.toString());
							}
						}

						parameters.put(property, valueList);
					}
				}
			}

		}

		QuerydslPredicate annotation = parameter.getParameterAnnotation(QuerydslPredicate.class);
		TypeInformation<?> domainType = extractTypeInfo(parameter).getActualType();

		@SuppressWarnings("rawtypes")
		Class<? extends QuerydslBinderCustomizer> customizer = annotation == null ? null : annotation.bindings();
		QuerydslBindings bindings = bindingsFactory.createBindingsFor(customizer, domainType);

		return predicateBuilder.getPredicate(domainType, parameters, bindings);
	}

	/**
	 * Obtains the domain type information from the given method parameter. Will
	 * favor an explicitly registered on through
	 * {@link QuerydslPredicate#root()} but use the actual type of the method's
	 * return type as fallback.
	 * 
	 * @param parameter
	 *            must not be {@literal null}.
	 * @return
	 */
	static TypeInformation<?> extractTypeInfo(MethodParameter parameter) {

		QuerydslPredicate annotation = parameter.getParameterAnnotation(QuerydslPredicate.class);

		if (annotation != null && !Object.class.equals(annotation.root())) {
			return ClassTypeInformation.from(annotation.root());
		}

		Class<?> containingClass = parameter.getContainingClass();
		if (ClassUtils.isAssignable(EntityController.class, containingClass)) {
			ResolvableType resolvableType = ResolvableType.forClass(containingClass);
			return ClassTypeInformation.from(resolvableType.as(EntityController.class).getGeneric(0).resolve());
		}

		return detectDomainType(ClassTypeInformation.fromReturnTypeOf(parameter.getMethod()));
	}

	private static TypeInformation<?> detectDomainType(TypeInformation<?> source) {

		if (source.getTypeArguments().isEmpty()) {
			return source;
		}

		TypeInformation<?> actualType = source.getActualType();

		if (source != actualType) {
			return detectDomainType(actualType);
		}

		if (source instanceof Iterable) {
			return source;
		}

		return detectDomainType(source.getComponentType());
	}
}