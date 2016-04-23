package com.whenling.module.domain.model;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Strings;

/**
 * 结果
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午7:01:52
 */
public class Result {

	public static Result success() {
		return new Result().code(Code.success);
	}

	public static Result failure() {
		return new Result().code(Code.failure);
	}

	public static Result validateError(Object data) {
		return new Result().code(Code.validateError).data(data);
	}

	public static Result permissionDenide() {
		return new Result().code(Code.permissionDenide);
	}

	public static Result notLogin() {
		return new Result().code(Code.notLogin);
	}

	public static Result exception() {
		return new Result().code(Code.exception);
	}

	public static Result captchaError() {
		return new Result().code(Code.captchaError);
	}

	public static Result unknown() {
		return new Result().code(Code.unknown);
	}

	private Code code = Code.success;
	private String message;
	private Object data;

	private Map<String, Object> extraProperties = new HashMap<>();

	public Code getCode() {
		return code;
	}

	public Result code(Code code) {
		this.code = code;
		return this;
	}

	public String getMessage() {
		return Strings.isNullOrEmpty(message) ? this.code.getMessage() : message;
	}

	public Result message(String message) {
		this.message = message;
		return this;
	}

	public Object getData() {
		return data;
	}

	public Result data(Object data) {
		this.data = data;
		return this;
	}

	public Map<String, Object> getExtraProperties() {
		return extraProperties;
	}

	public Result extraProperties(Map<String, Object> extraProperties) {
		this.extraProperties = extraProperties;
		return this;
	}

	public Result addExtraProperties(String key, Object value) {
		this.extraProperties.put(key, value);
		return this;
	}

	public enum Code {
		success("操作成功"),

		failure("操作失败"),

		validateError("验证错误"),

		permissionDenide("无权限访问"),

		notLogin("未登录"),

		exception("系统异常"),

		captchaError("验证码错误"),

		unknown("未知情况");

		private final String message;

		Code(final String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}

}
