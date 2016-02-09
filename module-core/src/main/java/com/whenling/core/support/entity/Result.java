package com.whenling.core.support.entity;

public class Result {

	public final static int CODE_SUCCESS = 0;
	public final static int CODE_VALIDATE_FAILURE = 1;
	public final static int CODE_PERMISSION_DENIED = 2;
	public final static int CODE_NOT_LOGIN = 3;
	public final static int CODE_EXCEPTION = 4;
	public final static int CODE_UNKNOWN = 5;

	public static Result success() {
		return new Result().setCode(CODE_SUCCESS);
	}

	public static Result validateFailure(Object data) {
		return new Result().setCode(CODE_VALIDATE_FAILURE).setData(data);
	}

	public static Result permissionDenide() {
		return new Result().setCode(CODE_PERMISSION_DENIED);
	}

	public static Result notLogin() {
		return new Result().setCode(CODE_NOT_LOGIN);
	}

	public static Result exception() {
		return new Result().setCode(CODE_EXCEPTION);
	}

	public static Result unknown() {
		return new Result().setCode(CODE_UNKNOWN);
	}

	private int code = CODE_SUCCESS;
	private String codeDes;
	private Object data;

	public int getCode() {
		return code;
	}

	public Result setCode(int code) {
		this.code = code;
		return this;
	}

	public String getCodeDes() {
		return codeDes;
	}

	public void setCodeDes(String codeDes) {
		this.codeDes = codeDes;
	}

	public Object getData() {
		return data;
	}

	public Result setData(Object data) {
		this.data = data;
		return this;
	}

	// public static Result success() {
	// return new
	// Result().setReturnCode(RETURN_SUCCESS).setResultCode(RESULT_SUCCESS);
	// }
	//
	// public static Result failure() {
	// return new
	// Result().setReturnCode(RETURN_SUCCESS).setResultCode(RESULT_FAILURE);
	// }
	//
	// public static Result interrupt() {
	// return new
	// Result().setReturnCode(RETURN_FAILURE).setResultCode(RESULT_FAILURE);
	// }

}
