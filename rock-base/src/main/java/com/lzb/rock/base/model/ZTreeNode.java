/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
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
package com.lzb.rock.base.model;

/**
 * jquery ztree 插件的节点
 *
 * @author fengshuonan
 * @date 2017年2月17日 下午8:25:14 此地不可以使用@data注解，会有大坑
 *       https://segmentfault.com/a/1190000015634321
 */
public class ZTreeNode {

	/**
	 * 节点id
	 */
	private Long id;

	/**
	 * 父节点id
	 */
	private Long pId;

	/**
	 * 节点名称
	 */
	private String name;

	/**
	 * 是否打开节点
	 */
	private Boolean open;

	/**
	 * 是否被选中
	 */
	private Boolean checked;

	/**
	 * 节点图标 single or group
	 */
	private String iconSkin;

	/**
	 * 创建ztree的父级节点
	 *
	 * @author fengshuonan
	 * @Date 2018/12/23 4:51 PM
	 */
	public static ZTreeNode createParent() {
		ZTreeNode zTreeNode = new ZTreeNode();
		zTreeNode.setChecked(true);
		zTreeNode.setId(0L);
		zTreeNode.setName("顶级");
		zTreeNode.setOpen(true);
		zTreeNode.setpId(-1L);
		return zTreeNode;
	}
	
	public static ZTreeNode createParent(Boolean checked,String name) {
		ZTreeNode zTreeNode = new ZTreeNode();
		zTreeNode.setChecked(checked);
		zTreeNode.setId(0L);
		zTreeNode.setName(name);
		zTreeNode.setOpen(true);
		zTreeNode.setpId(-1L);
		return zTreeNode;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the pId
	 */
	public Long getpId() {
		return pId;
	}

	/**
	 * @param pId the pId to set
	 */
	public void setpId(Long pId) {
		this.pId = pId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the open
	 */
	public Boolean getOpen() {
		return open;
	}

	/**
	 * @param open the open to set
	 */
	public void setOpen(Boolean open) {
		this.open = open;
	}

	/**
	 * @return the checked
	 */
	public Boolean getChecked() {
		return checked;
	}

	/**
	 * @param checked the checked to set
	 */
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	/**
	 * @return the iconSkin
	 */
	public String getIconSkin() {
		return iconSkin;
	}

	/**
	 * @param iconSkin the iconSkin to set
	 */
	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}

}
