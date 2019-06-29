package com.lzb.rock.base;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.lzb.rock.base.common.ResultEnum;
import com.lzb.rock.base.exception.RockException;
import com.lzb.rock.base.exception.UtilExceptionStackTrace;
import com.lzb.rock.base.util.HttpKit;

@ControllerAdvice
public abstract class BaseControllerExceptionAdvice {
	protected Logger log = LoggerFactory.getLogger(BaseControllerExceptionAdvice.class);

	/**
	 * 处理自定义业务异常
	 * 
	 * @param ex
	 * @param request
	 */
	@ExceptionHandler(RockException.class)
	@ResponseBody
	// @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Result<String> rockException(RockException ex) {
		Result<String> result = new Result<String>();
		result.setData(ex.getData());
		result.setCode(ex.getCode());
		result.setMsg(ex.getMessage());

		log.warn("uri:{},{}", HttpKit.getRequestURI(), result);
		log.warn(UtilExceptionStackTrace.getStackTrace(ex));
		return result;
	}

	/**
	 * 处理运行时异常
	 * 
	 * @param ex
	 * @param request
	 */
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public Result<String> runtimeException(RuntimeException ex) {
		Result<String> result = new Result<String>();
		result.error(ResultEnum.RUNTIME_ERR, ex.getMessage());
		log.error("uri:{},{}", HttpKit.getRequestURI(), result);
		log.error(UtilExceptionStackTrace.getStackTrace(ex));
		return result;
	}

	/**
	 * 处理参数校验异常
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public Result<String> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
		String uri = HttpKit.getRequestURI();
		BindingResult bindingResult = ex.getBindingResult();
		List<ObjectError> errors = bindingResult.getAllErrors();
		StringBuffer sb = new StringBuffer();
		boolean flag1 = false;
		for (ObjectError objectError : errors) {

			if (flag1) {
				sb.append(";");
			} else {
				flag1 = true;
			}
			sb.append(objectError.getDefaultMessage()).append(",");

			// 获取报错的值
			if (objectError instanceof FieldError) {
				FieldError fieldError = (FieldError) objectError;
				sb.append(fieldError.getRejectedValue()).append(",");
			}
			// 获取错误信息
			String[] codes = objectError.getCodes();
			if (codes.length > 0) {
				sb.append(codes[0]);
			}
			Object[] arguments = objectError.getArguments();
			if (arguments != null && arguments.length > 2) {
				sb.append(",").append(arguments[1]).append(",").append(arguments[2]);
			}
		}
		Result<String> result = new Result<String>();
		result.error(ResultEnum.PAEAM_ERR, sb.toString());
		log.debug("{}", result);
		log.debug("uri={}", uri);
		log.debug(UtilExceptionStackTrace.getStackTrace(ex));
		return result;
	}

	/**
	 * 参数类型不匹配
	 * 
	 * @param ex
	 * @param request
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseBody
	public Result<String> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
		Result<String> result = new Result<String>();
		result.error(ResultEnum.PAEAM_ERR, ex.getMessage());
		log.info("uri:{},{}", HttpKit.getRequestURI(), result);
		log.info(UtilExceptionStackTrace.getStackTrace(ex));
		return result;
	}

	/**
	 * 处理系统异常
	 * 
	 * @param ex
	 * @param request
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Result<String> exception(Exception ex) {
		String uri = HttpKit.getRequestURI();
		Result<String> result = new Result<String>();
		result.error(ResultEnum.SYSTTEM_ERR, ex.getMessage());
		log.error("uri:{},{}", HttpKit.getRequestURI(), result);
		log.error(UtilExceptionStackTrace.getStackTrace(ex));
		return result;
	}

}
