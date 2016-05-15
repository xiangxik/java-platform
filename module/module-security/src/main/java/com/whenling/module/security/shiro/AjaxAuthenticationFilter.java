package com.whenling.module.security.shiro;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.http.HttpStatus;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Strings;
import com.whenling.module.base.SpringContext;
import com.whenling.module.domain.model.Result;
import com.whenling.module.security.RobotPrevention;
import com.whenling.module.web.util.WebHelper;

/**
 * ajax认证过滤器
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午7:06:24
 */
public class AjaxAuthenticationFilter extends FormAuthenticationFilter {

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	private RobotPrevention robotPrevention;

	public void setRobotPrevention(RobotPrevention robotPrevention) {
		this.robotPrevention = robotPrevention;
	}

	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		if (this.robotPrevention != null) {
			if (this.robotPrevention.validateRequest(request, response)) {
				return super.executeLogin(request, response);
			} else {
				throw new AuthenticationException();
			}
		}
		return super.executeLogin(request, response);
	}

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
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
		if (WebHelper.isAjax((HttpServletRequest) request)) {
			writeObject(request, response, Result.success());
			return false;
		}
		return super.onLoginSuccess(token, subject, request, response);
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
		if (WebHelper.isAjax((HttpServletRequest) request)) {
			Result result = Result.failure();
			if (e instanceof IncorrectCredentialsException) {
				result.message("密码错误");
			} else if (e instanceof ExpiredCredentialsException) {
				result.message("密码已过期");
			} else if (e instanceof UnknownAccountException) {
				result.message("该账号不存在");
			} else if (e instanceof DisabledAccountException) {
				result.message("该账号已禁用");
			} else if (e instanceof LockedAccountException) {
				result.message("该账号已锁定");
			} else if (e instanceof AccountException) {
				result.message("账号错误");
			} else if (e instanceof CredentialsException) {
				result.message("密码错误");
			}
			try {
				writeObject(request, response, result);
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
			return false;
		}
		return super.onLoginFailure(token, e, request, response);
	}

	protected void writeObject(ServletRequest request, ServletResponse response, Object result) throws IOException {
		String characterEncoding = ((HttpServletRequest) request).getCharacterEncoding();
		Charset charset = Strings.isNullOrEmpty(characterEncoding) ? DEFAULT_CHARSET : Charset.forName(characterEncoding);
		OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream(), charset);
		SerializeWriter writer = new SerializeWriter(out);
		JSONSerializer serializer = new JSONSerializer(writer, SpringContext.getBean(SerializeConfig.class));
		serializer.config(SerializerFeature.DisableCircularReferenceDetect, true);
		serializer.write(result);
		writer.flush();
		out.close();
	}
}
