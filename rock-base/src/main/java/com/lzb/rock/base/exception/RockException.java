package com.lzb.rock.base.exception;

import com.lzb.rock.base.busInterface.BusEnum;

import lombok.Data;

/**
 * 封装rock的异常
 *
 * @author fengshuonan
 * @Date 2017/12/28 下午10:32
 */
@Data
public class RockException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String code;

	private String message;
	
	String data;

	public RockException(BusEnum serviceExceptionEnum) {
		this.code = serviceExceptionEnum.getCode();
		this.message = serviceExceptionEnum.getMsg();
	}
	
	public RockException(BusEnum serviceExceptionEnum,String data) {
		this.code = serviceExceptionEnum.getCode();
		this.message = serviceExceptionEnum.getMsg();
		this.data=data;
	}

	public RockException(BusEnum serviceExceptionEnum, String message,String data) {
		this.code = serviceExceptionEnum.getCode();
		this.message = message;
		this.data=data;
	}

}
