/**
 * @author lzb
 *
 * 
 *2019年4月3日 下午11:39:06
 */
package com.lzb.rock.base.common;

/**
 * 菜单类型枚举
 * 
 * @author lzb
 * 
 *         2019年4月3日 下午11:39:06
 */
public enum MenuTypeEnum {
	MENU(1, "菜单"), MENU_NOT(2, "按钮"), FIELLD_NOT(3, "字段");
	/**
	 * 编码
	 */
	private Integer code;
	/**
	 * 名称
	 */
	private String name;

	MenuTypeEnum(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	/**
	 * 枚举类型的判断和获取
	 * 
	 * @param code 错误码
	 * @return 返回错误码对应的枚举信息
	 */
	public static MenuTypeEnum statusOf(Integer code) {
		for (MenuTypeEnum resultEnum : values()) {
			if (resultEnum.getCode().equals(code)) {
				return resultEnum;
			}
		}
		return null;
	}

	/**
	 * 字典字符串，不能够包含逗号和分号
	 * 
	 * @return
	 */
	public static String dic() {
		StringBuffer sb = new StringBuffer();
		for (MenuTypeEnum resultEnum : values()) {
			sb.append(";").append(resultEnum.code).append(",").append(resultEnum.name);
		}
		return sb.substring(1);
	}

	public static String valueOf(Integer status) {
		if (status == null) {
			return "";
		} else {
			for (MenuTypeEnum s : MenuTypeEnum.values()) {
				if (s.getCode() == status) {
					return s.getName();
				}
			}
			return "";
		}
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? "" : name.trim();
		;
	}

}
