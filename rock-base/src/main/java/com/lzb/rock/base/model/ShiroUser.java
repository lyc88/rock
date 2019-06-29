package com.lzb.rock.base.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import lombok.Data;

/**
 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息
 * 
 * @author lzb
 * 
 *         2019年4月3日 下午11:25:50
 */
@Data
public class ShiroUser implements Serializable {

	private static final long serialVersionUID = 1L;

	public Long userId; // 主键ID，必填
	public String userAccount; // 账号，必填
	public String userName; // 姓名，必填
	public String userPassword; // 密码，在初始化SimpleAuthenticationInfo时需要,shiro 中不会存储，必填
	public String userSalt;// 加密随机字符串，在初始化SimpleAuthenticationInfo时需要,shiro 中不会存储，必填
	public String avatarUrl;// 头像
	ShiroDept dept;// 部门
	List<ShiroRole> roles;// 角色集合
	List<ShiroMenu> menus;// 菜单集
	Set<ShiroAuthz> authzs;// 权限集合
}
