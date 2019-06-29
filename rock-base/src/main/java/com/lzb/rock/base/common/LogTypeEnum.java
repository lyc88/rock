package com.lzb.rock.base.common;

import com.lzb.rock.base.util.UtilString;

/**
 * 日志类型
 * 
 * @author lzb
 * 
 *         2019年6月9日 下午10:25:51
 */
public enum LogTypeEnum {

	BUSSINESS("1", "业务日志"), EXCEPTION("2", "异常日志"), LOGIN("3", "登陆日志"), LOGIN_FAIL("4", "登陆失败日志"), EXIT("5", "退出日志");

	String code;
	String msg;

	LogTypeEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static String statusOf(String code) {
		if (UtilString.isBlank(code)) {
			return null;
		}

		for (LogTypeEnum bizLogType : LogTypeEnum.values()) {
			if (bizLogType.getCode().equals(code)) {
				return bizLogType.getMsg();
			}
		}

		return null;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
