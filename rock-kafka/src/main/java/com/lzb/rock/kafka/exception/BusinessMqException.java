package com.lzb.rock.kafka.exception;

import org.apache.kafka.common.errors.RetriableException;

/**
 * 异常，用语消息回滚
 * 
 * @author lzb
 *
 *         2018年12月20日 上午11:06:51
 */
public class BusinessMqException extends RetriableException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusinessMqException(String message) {
		super(message);
	}
	public BusinessMqException() {
	}
}
