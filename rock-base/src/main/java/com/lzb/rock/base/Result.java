package com.lzb.rock.base;

import java.io.Serializable;
import java.util.Map;

import com.lzb.rock.base.busInterface.BusEnum;
import com.lzb.rock.base.common.ResultEnum;
import com.lzb.rock.base.util.UtilDate;
import com.lzb.rock.base.util.UtilJson;
import com.lzb.rock.base.util.UtilReflexObject;

import io.swagger.annotations.ApiModelProperty;

/**
 * 返回基类
 * 
 * @author lzb 2018年2月1日 下午3:44:20
 * @param <T>
 */
public class Result<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 返回码
	 */
	@ApiModelProperty(value = "返回码，成功为0")
	private String code;
	/**
	 * 错误消息
	 */
	@ApiModelProperty(value = "返回码说明")
	private String msg;
	/**
	 * 返回结果内容
	 */
	@ApiModelProperty(value = "返回内容对象")
	private T data;

	@ApiModelProperty(value = "时间戳")
	private String time = UtilDate.getFomtTimeByDateString();
	/**
	 * 自定义错误提醒
	 */
	@ApiModelProperty(value = "自定义错误提醒，逗号分隔")
	private StringBuffer errMsgs = new StringBuffer();

	/**
	 * 操作失败
	 * 
	 * @param busEnum 错误 enum
	 */
	public void error(BusEnum busEnum) {
		setEnum(busEnum);
	}

	/**
	 * 操作失败
	 * 
	 * @param msgEnum 错误 enum
	 * @param errMsg  自定义错误提醒
	 */
	public void error(BusEnum msgEnum, String errMsg) {
		setEnum(msgEnum);
		setErrMsgs(errMsg);
	}

	/**
	 * 
	 * @param msgEnum 错误 enum
	 * @param errMsg  自定义错误提醒
	 * @param data    内容
	 */
	public void error(BusEnum msgEnum, String errMsg, T data) {
		setEnum(msgEnum);
		setErrMsgs(errMsg);
		this.data = data;
	}

	/**
	 * 自定义错误消息
	 * 
	 * @param errMsg
	 */
	public void setErrMsgs(String errMsg) {
		if (errMsgs.length() > 0) {
			errMsgs.append(",").append(errMsg);
		} else {
			errMsgs.append(errMsg);
		}
	}

	/**
	 * 校验前一步是否错误 true 为正确
	 * 
	 * @return
	 */
	public Boolean check() {
		return ResultEnum.SUCCESS.getCode().equals(this.code);
	};

	/**
	 * 请求成功且返回值不为null
	 * 
	 * @return
	 */
	public Boolean checkAndNotNull() {
		if (ResultEnum.SUCCESS.getCode().equals(this.code) && this.data != null) {
			return true;
		}
		return false;
	};

	/**
	 * 操作成功
	 * 
	 * @param data 内容
	 */
	public void success(T data) {
		this.setCode(ResultEnum.SUCCESS.getCode());
		this.setMsg(ResultEnum.SUCCESS.getMsg());
		this.data = data;
	}

	/**
	 * 操作成功
	 * 
	 */
	public void success() {
		this.setCode(ResultEnum.SUCCESS.getCode());
		this.setMsg(ResultEnum.SUCCESS.getMsg());
	}

	/**
	 * 操作成功
	 */
	public Result() {
		success();
	};

	public Result(BusEnum msgEnum, T data) {
		setEnum(msgEnum);

		this.setData(data);
	}

	/**
	 * 成功
	 * 
	 * @param data
	 */
	public Result(T data) {
		this.setCode(ResultEnum.SUCCESS.getCode());
		this.setMsg(ResultEnum.SUCCESS.getMsg());
		this.setData(data);
	}

	public Result(BusEnum msgEnum) {
		this.setCode(msgEnum.getCode());
		this.setMsg(msgEnum.getMsg());
	}

	/**
	 * 通过反射获取枚举
	 * 
	 * @param msgEnum
	 */
	public void setEnum(BusEnum msgEnum) {
		Map<String, Object> map = UtilReflexObject.getKeyAndValue(msgEnum);
		this.setCode(map.get("code").toString());
		this.setMsg(map.get("msg").toString());

	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public StringBuffer getErrMsgs() {
		return errMsgs;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setErrMsgs(StringBuffer errMsgs) {
		this.errMsgs = errMsgs;
	}

	@Override
	public String toString() {
		return UtilJson.getStr(this);
	}
}
