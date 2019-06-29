package com.lzb.rock.base.busInterface;

/**
 * 
 * 若自定义，自己实现该接口
 * @author admin
 *
 */
public interface IDictFormatter {
	/**
	 * 根据keys 获取value值 value1-value2-value3
	 * 
	 * @param keys
	 * @return
	 */
	public String getValue(String code,String keys);

	/**
	 * 根据key 获取value值
	 * 
	 * @param key
	 * @return
	 */
	public String getValue(String code,Long key);

}
