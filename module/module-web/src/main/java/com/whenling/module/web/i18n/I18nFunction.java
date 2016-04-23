package com.whenling.module.web.i18n;

import java.util.Arrays;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.view.AbstractTemplateView;

@Component("i18n")
public class I18nFunction implements Function {

	@Override
	public Object call(Object[] paras, Context ctx) {

		RequestContext requestContext = (RequestContext) ctx
				.getGlobal(AbstractTemplateView.SPRING_MACRO_REQUEST_CONTEXT_ATTRIBUTE);

		Assert.notNull(requestContext);
		Assert.notNull(paras);
		Assert.isTrue(paras.length > 0);

		String code = (String) paras[0];
		Object[] args = paras.length > 1 ? Arrays.copyOfRange(paras, 1, paras.length) : null;

		return requestContext.getMessage(code, args);
	}

}
