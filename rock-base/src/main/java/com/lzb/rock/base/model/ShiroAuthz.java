package com.lzb.rock.base.model;

import java.util.Objects;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.AllPermission;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 菜单表
 * </p>
 * 
 * @author lzb123
 * @since 2019-05-16
 */
public class ShiroAuthz extends AllPermission implements Permission {

	/**
	 * @author Administrator
	 *
	 * 
	 *         2019年5月27日 下午10:33:04
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 权限ID
	 */
	@ApiModelProperty(value = "权限ID")
	private Long authzId;
	/**
	 * 权限编码
	 */
	@ApiModelProperty(value = "权限编码")
	private String authzCode;
	/**
	 * 权限父编码
	 */
	@ApiModelProperty(value = "权限父编码")
	private String authzPcode;
	/**
	 * 权限名称
	 */
	@ApiModelProperty(value = "权限名称")
	private String authzName;
	/**
	 * 权限图片，菜单权限专用
	 */
	@ApiModelProperty(value = "权限图片，菜单权限专用")
	private String authzIcon;

	/**
	 * 权限url地址，菜单权限专用
	 */
	@ApiModelProperty(value = "权限url地址，菜单权限专用")
	private String authzUrl;

	/**
	 * 权限排序，升序
	 */
	@ApiModelProperty(value = "权限排序，升序")
	private Integer authzSort;

	/**
	 * 权限类型，0菜单，1按钮2字段
	 */
	@ApiModelProperty(value = "权限类型，0菜单，1按钮，2字段")
	private Integer authzType;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String authzTips;

	/**
	 * 是否打开: 1:打开 0:不打开；菜单权限专用
	 */
	@ApiModelProperty(value = "是否打开:    1:打开   0:不打开；菜单权限专用")
	private Integer authzIsOpen;

	public int hashCode() {

		return Objects.hash(this.authzId);
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ShiroAuthz shiroAuthz = (ShiroAuthz) o;

		return shiroAuthz.getAuthzCode().equals(this.authzCode);
	}

	@Override
	public boolean implies(Permission p) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @return the authzId
	 */
	public Long getAuthzId() {
		return authzId;
	}

	/**
	 * @param authzId the authzId to set
	 */
	public void setAuthzId(Long authzId) {
		this.authzId = authzId;
	}

	/**
	 * @return the authzCode
	 */
	public String getAuthzCode() {
		return authzCode;
	}

	/**
	 * @param authzCode the authzCode to set
	 */
	public void setAuthzCode(String authzCode) {
		this.authzCode = authzCode;
	}

	/**
	 * @return the authzPcode
	 */
	public String getAuthzPcode() {
		return authzPcode;
	}

	/**
	 * @param authzPcode the authzPcode to set
	 */
	public void setAuthzPcode(String authzPcode) {
		this.authzPcode = authzPcode;
	}

	/**
	 * @return the authzName
	 */
	public String getAuthzName() {
		return authzName;
	}

	/**
	 * @param authzName the authzName to set
	 */
	public void setAuthzName(String authzName) {
		this.authzName = authzName;
	}

	/**
	 * @return the authzIcon
	 */
	public String getAuthzIcon() {
		return authzIcon;
	}

	/**
	 * @param authzIcon the authzIcon to set
	 */
	public void setAuthzIcon(String authzIcon) {
		this.authzIcon = authzIcon;
	}

	/**
	 * @return the authzUrl
	 */
	public String getAuthzUrl() {
		return authzUrl;
	}

	/**
	 * @param authzUrl the authzUrl to set
	 */
	public void setAuthzUrl(String authzUrl) {
		this.authzUrl = authzUrl;
	}

	/**
	 * @return the authzSort
	 */
	public Integer getAuthzSort() {
		return authzSort;
	}

	/**
	 * @param authzSort the authzSort to set
	 */
	public void setAuthzSort(Integer authzSort) {
		this.authzSort = authzSort;
	}

	/**
	 * @return the authzType
	 */
	public Integer getAuthzType() {
		return authzType;
	}

	/**
	 * @param authzType the authzType to set
	 */
	public void setAuthzType(Integer authzType) {
		this.authzType = authzType;
	}

	/**
	 * @return the authzTips
	 */
	public String getAuthzTips() {
		return authzTips;
	}

	/**
	 * @param authzTips the authzTips to set
	 */
	public void setAuthzTips(String authzTips) {
		this.authzTips = authzTips;
	}

	/**
	 * @return the authzIsOpen
	 */
	public Integer getAuthzIsOpen() {
		return authzIsOpen;
	}

	/**
	 * @param authzIsOpen the authzIsOpen to set
	 */
	public void setAuthzIsOpen(Integer authzIsOpen) {
		this.authzIsOpen = authzIsOpen;
	}

}
