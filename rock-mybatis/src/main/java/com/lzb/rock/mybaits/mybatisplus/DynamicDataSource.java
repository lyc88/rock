package com.lzb.rock.mybaits.mybatisplus;


import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据源切换类
 * 注意线程问题
 * 同时注意事物问题
 * @author lzb
 * 
 *2019年3月10日 上午11:20:45
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceContextHolder.getDataSourceKey();
	}

}
