package com.lzb.rock.base.model;

import lombok.Data;

/**
 * 日志保存用户对象
 * 
 * @author admin
 *
 */
@Data
public class LogUser {
	public Long userId; // 主键ID，必填
	public String userAccount; // 账号，必填
	public String userName; // 姓名，必填
}
