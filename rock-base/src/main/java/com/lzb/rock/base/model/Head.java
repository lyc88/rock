package com.lzb.rock.base.model;

import lombok.Data;

/**
 * 请求头部
 * @author lzb
 *
 */
@Data
public class Head {

	String numberId;
	/**
	 * token
	 */
	String token;
	/**
	 * 失效时间
	 */
	Long invalidTime;
	/**
	 * 上次刷新时间
	 */
	Long refTime;
	/**
	 * 签名值
	 */
	String sign;
	/**
	 * 请求来源 wx,android,ios
	 */
	String source;
}
