package com.whenling.module.security.captcha;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.octo.captcha.service.CaptchaService;
import com.whenling.module.domain.model.Result;

/**
 * 验证码过滤器
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午7:06:14
 */
@Component("captchaFilter")
public class CaptchaFilter extends OncePerRequestFilter {

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	public static final String PARAM_CAPTCHA = "captcha";

	private String[] distinguishPaths = new String[] { "/admin" };

	@Autowired
	private CaptchaService captchaService;

	@Autowired
	private SerializeConfig serializeConfig;

	@Autowired
	private ServletContext servletContext;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (isSubmission(request, response)) {
			String uri = request.getRequestURI();
			if (isDistinguishUri(uri)) {
				String captchaCode = request.getParameter(PARAM_CAPTCHA);

				boolean correct = !Strings.isNullOrEmpty(captchaCode) && captchaService
						.validateResponseForID(request.getSession(true).getId(), captchaCode.toUpperCase());
				if (!correct) {
					writeObject(request, response, Result.captchaError());
					return;
				}
			}
		}

		filterChain.doFilter(request, response);
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

	private boolean isDistinguishUri(String uri) {
		for (String distinguishPath : distinguishPaths) {
			if (Objects.equal(servletContext.getContextPath() + distinguishPath, uri)) {
				return true;
			}
		}
		return false;
	}

	private boolean isSubmission(HttpServletRequest request, HttpServletResponse response) {
		return request.getMethod().equalsIgnoreCase("POST");
	}

}
