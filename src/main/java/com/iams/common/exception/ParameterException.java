package com.iams.common.exception;


/**
 *  @author: Wei yz
 *  @Date: 2021/2/1 16:57
 *  @Description: 参数异常类
 */
public class ParameterException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ParameterException(String mesaage) {
		super(mesaage);
	}

}
