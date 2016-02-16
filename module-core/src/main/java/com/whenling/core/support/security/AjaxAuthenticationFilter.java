package com.whenling.core.support.security;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Strings;
import com.whenling.core.support.base.WebHelper;
import com.whenling.core.support.entity.Result;

public class AjaxAuthenticationFilter extends FormAuthenticationFilter {

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	@Autowired
	private SerializeConfig serializeConfig;

	@Override
	protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
		if (WebHelper.isAjax((HttpServletRequest) request)) {
			((HttpServletResponse) response).setStatus(HttpStatus.UNAUTHORIZED.value());
			writeObject(request, response, Result.notLogin());
		} else {
			super.saveRequestAndRedirectToLogin(request, response);
		}
	}

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		if (WebHelper.isAjax((HttpServletRequest) request)) {
			writeObject(request, response, Result.success());
			return false;
		}
		return super.onLoginSuccess(token, subject, request, response);
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		if (WebHelper.isAjax((HttpServletRequest) request)) {
			try {
				writeObject(request, response, Result.failure());
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
			return false;
		}
		return super.onLoginFailure(token, e, request, response);
	}

	protected void writeObject(ServletRequest request, ServletResponse response, Object result) throws IOException {
		String characterEncoding = ((HttpServletRequest) request).getCharacterEncoding();
		Charset charset = Strings.isNullOrEmpty(characterEncoding) ? DEFAULT_CHARSET
				: Charset.forName(characterEncoding);
		OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream(), charset);
		SerializeWriter writer = new SerializeWriter(out);
		JSONSerializer serializer = new JSONSerializer(writer, this.serializeConfig);
		serializer.config(SerializerFeature.DisableCircularReferenceDetect, true);
		serializer.write(result);
		writer.flush();
		out.close();
	}
}
