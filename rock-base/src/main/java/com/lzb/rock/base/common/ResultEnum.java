package com.lzb.rock.base.common;

import com.google.common.collect.ImmutableMap;
import com.lzb.rock.base.busInterface.BusEnum;

/**
 * 状态枚举类
 * 
 * @author lzb 2018年2月1日 下午3:50:38
 */
public enum ResultEnum implements BusEnum{

	// 系统级别错误码,系统级别错误码小于1000
	SUCCESS("0", "操作成功"),

	NET_WORK_ERR("1001", "网络异常"),

	PAEAM_ERR("1002", "参数异常"),

	INSERT_ERR("1003", "插入失败"),

	UPDATE_ERR("1004", "更新失败"),

	SELECT_ERR("1005", "查询失败"),

	SIGN_ERR("1006", "签名异常"),

	JSON_ERR("1007", "JSON异常"),

	WECHAT_TOKEN_ERR("1008", "获取token异常"),

	TYPE_ERR("1009", "类型异常"),
	MANY_ERR("1010", "已到达最大数量"), 
	DELETE_ERR("1011", "删除失败"),

	SMS_SEND_ERR("1012", "验证码发送失败"), 
	SMS_SIGN_ERR("1013", "短信签名不存在"),
	SMS_TEXT_ERR("1014", "短信内容不符合规定"),
	DATA_REPEAT_ERR("1015", "数据已存在"),
	FILE_UPLOAD_ERR("1016", "文件上传失败"),
	
	SYSTTEM_ERR("-1", "未知异常"),

	RUNTIME_ERR("-2", "未知的运行时异常"),
	REST_ERR("-3", "远程调用异常"),
	ENUM_ERR("-4", "枚举异常"),
	AOP_ERR("-5", "AOP异常"),
	STATUS_ERR("-6", "请求码异常"), 
	
	/**
	 * 字典
	 */
	DICT_EXISTED("400","字典已经存在"),
	ERROR_CREATE_DICT("500","创建字典失败"),
	ERROR_WRAPPER_FIELD("500","包装字典属性失败"),

	/**
	 * 文件上传
	 */
	FILE_READING_ERROR("400","FILE_READING_ERROR!"),
	FILE_NOT_FOUND("400","FILE_NOT_FOUND!"),
	UPLOAD_ERROR("500","上传图片出错"),

	/**
	 * 权限和数据问题
	 */
	DB_RESOURCE_NULL("400","数据库中没有该资源"),
	NO_PERMITION("405", "权限异常"),
	REQUEST_INVALIDATE("400","请求数据格式不正确"),
	INVALID_KAPTCHA("400","验证码不正确"),
	CANT_DELETE_ADMIN("600","不能删除超级管理员"),
	CANT_FREEZE_ADMIN("600","不能冻结超级管理员"),
	CANT_CHANGE_ADMIN("600","不能修改超级管理员角色"),

	/**
	 * 账户问题
	 */
	USER_ALREADY_REG("401","该用户已经注册"),
	NO_THIS_USER("400","没有此用户"),
	USER_NOT_EXISTED("400", "没有此用户"),
	ACCOUNT_FREEZED("401", "账号被冻结"),
	OLD_PWD_NOT_RIGHT("402", "原密码不正确"),
	TWO_PWD_NOT_MATCH("405", "两次输入密码不一致"),
	TREE_ADD_ERR("410","已达到最大节点层数"),
	/**
	 * 错误的请求
	 */
	MENU_PCODE_COINCIDENCE("400","菜单编号和副编号不能一致"),
	EXISTED_THE_MENU("400","菜单编号重复，不能添加"),
	DICT_MUST_BE_NUMBER("400","字典的值必须为数字"),
	REQUEST_NULL("400", "请求有错误"),
	SESSION_TIMEOUT("400", "会话超时"),
	SERVER_ERROR("500", "服务器异常");

	private String code;
	private String msg;

	ResultEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	/**
	 * 枚举类型的判断和获取
	 * 
	 * @param code 错误码
	 * @return 返回错误码对应的枚举信息
	 */
	public static ResultEnum statusOf(String code) {
		for (ResultEnum resultEnum : values()) {
			if (resultEnum.getCode().equals(code)) {
				return resultEnum;
			}
		}
		return null;
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
		this.msg = msg == null ? "" : msg.trim();
	}

	/**
	 * 枚举转换为MAP
	 * 
	 * @return
	 */
	public ImmutableMap<String, String> toMap() {
		return ImmutableMap.<String, String>builder().put("msg", msg).put("code", code).build();
	}
}
