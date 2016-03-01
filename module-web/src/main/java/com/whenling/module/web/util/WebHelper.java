package com.whenling.module.web.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.util.WebUtils;

/**
 * web工具
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午4:49:04
 */
public class WebHelper {

	static String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i" + "|windows (phone|ce)|blackberry"
			+ "|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp" + "|laystation portable)|nokia|fennec|htc[-_]"
			+ "|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
	static String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser" + "|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

	// 移动设备正则匹配：手机端、平板
	static Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);
	static Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);

	public static boolean isAjax(HttpServletRequest request) {
		return (request.getHeader("X-Requested-With") != null
				&& "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString()));
	}

	/**
	 * 判断是否是手机访问
	 */
	public static boolean isMobileAccess(HttpServletRequest request) {
		boolean isFromMobile = false;
		// 检查是否已经记录访问方式（移动端或pc端）
		Object ua = WebUtils.getSessionAttribute(request, "ua");
		if (null == ua) {
			try {
				String userAgent = request.getHeader("USER-AGENT").toLowerCase();
				if (null == userAgent) {
					userAgent = "";
				}
				isFromMobile = checkUserAgent(userAgent);
				// 判断是否为移动端访问
				if (isFromMobile) {
					WebUtils.setSessionAttribute(request, "ua", "mobile");
				} else {
					WebUtils.setSessionAttribute(request, "ua", "pc");
				}
			} catch (Exception e) {
			}
		} else {
			isFromMobile = ua.equals("mobile");
		}

		return isFromMobile;
	}

	private static boolean checkUserAgent(String userAgent) {
		if (null == userAgent) {
			userAgent = "";
		}
		// 匹配
		Matcher matcherPhone = phonePat.matcher(userAgent);
		Matcher matcherTable = tablePat.matcher(userAgent);
		if (matcherPhone.find() || matcherTable.find()) {
			return true;
		} else {
			return false;
		}
	}
}
