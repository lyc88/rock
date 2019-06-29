package com.lzb.rock.mybaits.mybatisplus;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor;
import com.lzb.rock.base.properties.RockProperties;
import com.lzb.rock.base.util.UtilString;
import com.lzb.rock.mybaits.properties.MutiDatasource;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据源配置
 * 
 */
@Configuration
@Slf4j
@EnableTransactionManagement(order = 2) // 由于引入多数据源，所以让spring事务的aop要在多数据源切换aop的后面
public class BaseDataSourceConfig {

	@Autowired
	DruidProperties druidProperties;

	@Autowired
	RockProperties rockProperties;
	@Autowired
	MutiDatasource mutiDatasource;

	public static HashMap<Object, Object> dataSourceMap = new HashMap<Object, Object>();

	/**
	 * 数据源配置，可能多个数据源 当前使用mycat，每一线程独立使用一个数据源 此处创建多个数据源
	 * 
	 * @return
	 */
	@Bean
	public DataSource dataSource() {

		if (rockProperties.mutiDatasourceOnOff) {// 多数据源

			DynamicDataSource dynamicDataSource = new DynamicDataSource();
			Map<String, String> dataSourcePro = mutiDatasource.getDatasource();
			log.info("====================>启用多数据源初始化");
			for (int i = 0; i < 20; i++) {
				String key = "";
				if (i > 0) {
					key = key + i;
				}
				String datasourcename = dataSourcePro.get("datasourcename" + key);
				String url = dataSourcePro.get("url" + key);
				String username = dataSourcePro.get("username" + key);
				String password = dataSourcePro.get("password" + key);
				String filters = dataSourcePro.get("filters" + key);
				if (UtilString.isNotBlank(datasourcename) && UtilString.isNotBlank(url) && UtilString.isNotBlank(username) && UtilString.isNotBlank(password) && UtilString.isNotBlank(filters)) {
					DruidDataSource dataSource = new DruidDataSource();
					druidProperties.setUrl(url);
					druidProperties.setUsername(username);
					druidProperties.setPassword(password);
					druidProperties.setFilters(filters);

					druidProperties.config(dataSource);
					dataSourceMap.put(datasourcename, dataSource);
					if (i < 1) {
						dynamicDataSource.setDefaultTargetDataSource(dataSource);
					}
					log.info("初始化数据源;datasourcename={};username={};url={}", datasourcename, username, url);
				}
			}
			dynamicDataSource.setTargetDataSources(dataSourceMap);
			// dynamicDataSource.setResolvedDataSources(dataSourceMap);

			return dynamicDataSource;
		} else {// 单数据源

			log.info("====================>启用单数据源");
			DruidDataSource dataSource = new DruidDataSource();
			druidProperties.config(dataSource);
			return dataSource;
		}

	}

	// 配置事物管理器
	@Bean(name = "transactionManager")
	public DataSourceTransactionManager transactionManager() {

		return new DataSourceTransactionManager(dataSource());
	}

	/**
	 * 乐观锁mybatis插件
	 */
	@Bean
	public OptimisticLockerInterceptor optimisticLockerInterceptor() {
		return new OptimisticLockerInterceptor();
	}
}
