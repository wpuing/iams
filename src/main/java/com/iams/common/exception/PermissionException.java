package com.iams.common.exception;


/**
 *  @author: Wei yz
 *  @Date: 2021/2/1 16:58
 *  @Description: 权限异常类
 */
public class PermissionException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
    public PermissionException(String message) {
		super(message);
	}

}
