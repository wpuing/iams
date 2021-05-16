package com.iams.common.util;


/**
 *  @author: Wei yz
 *  @Date: 2021/2/1 17:14
 *  @Description: 统一返回数据
 */
public class Result {

	private int code;
	private String message;
	private Object data;

	public Result setCode(ResultCode resultCode) {
		this.code = resultCode.code;
		return this;
	}

	public int getCode() {
		return code;
	}

	public Result setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public Result setMessage(String message) {
		this.message = message;
		return this;
	}

	public Object getData() {
		return data;
	}

	public Result setData(Object data) {
		this.data = data;
		return this;
	}

}
