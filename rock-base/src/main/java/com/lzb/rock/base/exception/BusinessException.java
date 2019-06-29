package com.lzb.rock.base.exception;

import com.lzb.rock.base.busInterface.BusEnum;

/**
 * 业务运行时异常类
 * 
 * @author lzb 2018年2月1日 下午3:25:52
 */
public class BusinessException extends RuntimeException {

	BusEnum resultEnum;
	
	String data;

	/**
	 * 
	 * @param resultEnum
	 *            返回枚举
	 * @param message
	 */

	public BusinessException(BusEnum resultEnum) {
		
		this.resultEnum = resultEnum;
	}
	public BusinessException(BusEnum resultEnum, String message) {
		super(message);
		this.resultEnum = resultEnum;
	}
	public BusinessException(BusEnum resultEnum, String data,String message) {
		super(message);
		this.resultEnum = resultEnum;
		this.data=data;
	}

	public BusEnum getResultEnum() {
		return resultEnum;
	}

	public void setResultEnum(BusEnum resultEnum) {
		this.resultEnum = resultEnum;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	
}
