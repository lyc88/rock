package com.lzb.rock.mybaits.mybatisplus;

import lombok.extern.slf4j.Slf4j;

/**
 * datasource 上下文，用于设置线程中数据源
 * 
 * @author lzb
 * 
 *         2019年3月10日 上午11:21:48
 */
@Slf4j
public class DataSourceContextHolder {

	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	/**
	 * 设置数据源类型
	 *
	 * @param dataSourceKey 数据库类型
	 */
	public static void setDataSourceKey(String dataSourceKey) {
		log.debug("=====>setDataSourceKey:{}", dataSourceKey);
		contextHolder.set(dataSourceKey);
	}

	/**
	 * 获取数据源类型
	 */
	public static String getDataSourceKey() {
		String sourceKey = contextHolder.get();
		log.debug("=====>getDataSourceKey:{}", sourceKey);
		return sourceKey;
	}

	/**
	 * 清除数据源类型
	 */
	public static void clearDataSourceKey() {
		log.debug("=====>clearDataSourceKey}");
		contextHolder.remove();
	}
}
