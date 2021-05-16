package com.iams.common.exception;


/**
 *  @author: Wei yz
 *  @Date: 2021/2/1 16:57
 *  @Description: 操作异常类
 */
public class OperationException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public OperationException(String message) {
		
		super(message);
	}

	
}
