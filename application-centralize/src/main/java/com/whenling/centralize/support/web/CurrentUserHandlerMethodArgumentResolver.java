package com.whenling.centralize.support.web;

import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.whenling.centralize.model.User;
import com.whenling.centralize.service.UserService;

/**
 * 当前用户方法参数解析器
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午7:06:38
 */
public class CurrentUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	private UserService userService;

	public CurrentUserHandlerMethodArgumentResolver(UserService userService) {
		this.userService = userService;
	}

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
