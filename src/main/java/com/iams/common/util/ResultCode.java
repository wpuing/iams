package com.iams.common.util;


/**
 *  @author: Wei yz
 *  @Date: 2021/2/1 17:14
 *  @Description: 错误码
 */
public enum ResultCode {
	SUCCESS(200), // 成功
	FAIL(400), // 失败
	UNAUTHORIZED(401), // 未认证（签名错误）
	NOT_FOUND(404), // 接口不存在
	INTERNAL_SERVER_ERROR(500),// 服务器内部错误
	ACCESS_PERMISSION_ERROR(300), //访问权限异常
	NOT_PERMISSION(301); //没有权限

	public int code;

	ResultCode(int code) {
		this.code = code;
	}
}
