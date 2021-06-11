package com.iams.common.interceptor;

import com.iams.common.exception.OperationException;
import com.iams.common.exception.ParameterException;
import com.iams.common.util.Result;
import com.iams.common.util.ResultCode;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.ui.Model;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 *  @author: Wei yz
 *  @Date: 2021/2/1 17:13
 *  @Description: 自定义异常拦截器
 */
@ControllerAdvice
public class ExceptionInterceptor {

	/**
	 * 权限异常拦截
	 * @param e
	 * @return
	 */
	@ExceptionHandler(UnauthorizedException.class)
	public String unauthorizedException(UnauthorizedException e, Model model) {
		Result result = new Result()
				.setCode(ResultCode.NOT_PERMISSION)
				.setMessage("您没有权限哦！信息=> " + e.getMessage());
		model.addAttribute("result",result);
		return "unauthorized";
	}

	/**
	 * @Title: operationException
	 * @Description: 操作异常处理
	 * @param e
	 * @return
	 */
	@ExceptionHandler(OperationException.class)
	public String operationException(OperationException e, Model model) {
		Result result = new Result()
				.setCode(ResultCode.INTERNAL_SERVER_ERROR)
				.setMessage(e.getMessage());
		model.addAttribute("result",result);
		return "operationException";
	}

	/**
	 * @Title: parameterException
	 * @Description: 参数异常处理
	 * @param e
	 * @return
	 */
	@ExceptionHandler(ParameterException.class)
	@ResponseBody
	public Result parameterException(ParameterException e) {
		return new Result()
				.setCode(ResultCode.FAIL)
				.setMessage(e.getMessage());
	}


	@ExceptionHandler(AuthorizationException.class)
	@ResponseBody
	public Result authorizationException(AuthorizationException e) {
		return new Result()
				.setCode(ResultCode.UNAUTHORIZED)
				.setMessage( "对不起，权限认证失败！"+e.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public Result methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
		// 从异常对象中拿到ObjectError对象
		System.out.println("捕获异常： ");
		e.printStackTrace();
		ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
		// 然后提取错误提示信息进行返回
		return new Result()
				.setCode(ResultCode.INTERNAL_SERVER_ERROR)
				.setMessage(objectError.getDefaultMessage());
	}

	/**
	 * 
	 * @Description:  空指针
	 * @param e
	 * @return
	 */
	@ExceptionHandler(NullPointerException.class)
	@ResponseBody
	public Result nullPointerException(NullPointerException e) {
		return new Result()
				.setCode(ResultCode.INTERNAL_SERVER_ERROR)
				.setMessage("空指针异常, "+e.getMessage());
	}

	/**
	 * 拦截所有运行时的全局异常   
	 */
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public Result runtimeException(RuntimeException e) {
		return new Result()
				.setCode(ResultCode.INTERNAL_SERVER_ERROR)
				.setMessage(e.getMessage());
	}

	/**
	 * 系统异常捕获处理
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Result exception(Exception e) {
		return new Result()
				.setCode(ResultCode.INTERNAL_SERVER_ERROR)
				.setMessage(e.getMessage());
	}

}
