package com.whenling.core.support.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.whenling.core.model.User;
import com.whenling.core.service.UserService;

public class CurrentUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Autowired
	private UserService userService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(CurrentUser.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		CurrentUser currentUser = parameter.getParameterAnnotation(CurrentUser.class);
		boolean required = currentUser.required();

		User user = userService.getCurrentUser();
		if (user == null) {
			Assert.isTrue(!required);

			return null;
		}

		return user;
	}

}
