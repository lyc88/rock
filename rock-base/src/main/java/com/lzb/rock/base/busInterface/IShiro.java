/**
 * Copyright (c) 2015-2017, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lzb.rock.base.busInterface;

import java.util.List;
import java.util.Set;

import com.lzb.rock.base.model.LogUser;
import com.lzb.rock.base.model.ShiroAuthz;
import com.lzb.rock.base.model.ShiroMenu;
import com.lzb.rock.base.model.ShiroRole;
import com.lzb.rock.base.model.ShiroUser;

/**
 * 获取用户登录信息，以及校验
 * 
 * @author admin
 *
 */
public interface IShiro {
	static final String NAMES_DELIMETER = ",";

	/**
	 * 获取shiro的认证信息 根据账号查询用户信息
	 */
	ShiroUser getShiroAuthByUserAccount(String userAccount);

	/**
	 * 根据角色查询用户菜单按钮字段权限
	 * 
	 * @param userId
	 * @return
	 */
	public Set<ShiroAuthz> getShiroAuthzByShiroRoles(List<ShiroRole> roles);

	/**
	 * 根据用户ID查询用户角色集
	 * 
	 * @param userId
	 * @return
	 */
	public List<ShiroRole> getShiroRolesByUserId(Long userId);

	/**
	 * 获取封装的 ShiroUser
	 *
	 * @return ShiroUser
	 */
	public LogUser getUser();

	// ====================================
//
//	/**
//	 * 获取权限列表通过角色id
//	 *
//	 * @param roleId 角色id
//	 */
//	List<String> findPermissionsByRoleId(Integer roleId);

//	/**
//	 * 根据角色id获取角色名称
//	 *
//	 * @param roleId 角色id
//	 */
//	String findRoleNameByRoleId(Integer roleId);
//
//	// ---------------
//
//	/**
//	 * 获取封装的 ShiroUser
//	 *
//	 * @return ShiroUser
//	 */
//	public ShiroUser getUser();
//
//	/**
//	 * 验证当前用户是否属于该角色？,使用时与lacksRole 搭配使用
//	 *
//	 * @param roleName 角色名
//	 * @return 属于该角色：true，否则false
//	 */
//	public boolean hasRole(String roleName);
//
//	/**
//	 * 与hasRole标签逻辑相反，当用户不属于该角色时验证通过。
//	 *
//	 * @param roleName 角色名
//	 * @return 不属于该角色：true，否则false
//	 */
//	public boolean lacksRole(String roleName);
//
//	/**
//	 * 验证当前用户是否属于以下任意一个角色。
//	 *
//	 * @param roleNames 角色列表
//	 * @return 属于:true,否则false
//	 */
//	public boolean hasAnyRoles(String roleNames);
//
//	/**
//	 * 验证当前用户是否属于以下所有角色。
//	 *
//	 * @param roleNames 角色列表
//	 * @return 属于:true,否则false
//	 */
//	public boolean hasAllRoles(String roleNames);
//
//	/**
//	 * 验证当前用户是否拥有指定权限,使用时与lacksPermission 搭配使用
//	 *
//	 * @param permission 权限名
//	 * @return 拥有权限：true，否则false
//	 */
//	public boolean hasPermission(String permission);
//
//	/**
//	 * 与hasPermission标签逻辑相反，当前用户没有制定权限时，验证通过。
//	 *
//	 * @param permission 权限名
//	 * @return 拥有权限：true，否则false
//	 */
//	public boolean lacksPermission(String permission);
//
//	/**
//	 * 已认证通过的用户。不包含已记住的用户，这是与user标签的区别所在。与notAuthenticated搭配使用
//	 *
//	 * @return 通过身份验证：true，否则false
//	 */
//	public boolean authenticated();
//
//	/**
//	 * 未认证通过用户，与authenticated标签相对应。与guest标签的区别是，该标签包含已记住用户。。
//	 *
//	 * @return 没有通过身份验证：true，否则false
//	 */
//	public boolean notAuthenticated();
//
//	/**
//	 * 认证通过或已记住的用户。与guset搭配使用。
//	 *
//	 * @return 用户：true，否则 false
//	 */
//	public boolean isUser();
//
//	/**
//	 * 验证当前用户是否为“访客”，即未认证（包含未记住）的用户。用user搭配使用
//	 *
//	 * @return 访客：true，否则false
//	 */
//	public boolean isGuest();
//
//	/**
//	 * 输出当前用户信息，通常为登录帐号信息。
//	 *
//	 * @return 当前用户信息
//	 */
//	public String principal();
}
