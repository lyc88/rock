package com.lzb.rock.mybaits.mybatisplus;

import java.sql.SQLException;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.alibaba.druid.pool.DruidDataSource;

import lombok.Data;

/**
 * 默认数据库数据源配置 有需要修改请在配置文件修改
 * 
 * @author lzb
 * 
 *         2019年3月10日 上午11:21:32
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource")
@Data
public class DruidProperties {

	private String url = "jdbc:mysql://127.0.0.1:3306/rock?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";

	private String username = "root";

	private String password = "root";
	// 数据库驱动
	private String driverClassName = "com.mysql.jdbc.Driver";
	
	// 数据源类型
	private String type = "com.alibaba.druid.pool.DruidDataSource";
	
	// 初始化大小，最小，最大
	private Integer initialSize = 1;

	private Integer minIdle = 30;

	private Integer maxActive = 200;
	// 连接等待超时时间
	private Integer maxWait = 10000;
	// 配置隔多久进行一次检测(检测可以关闭的空闲连接)
	private Integer timeBetweenEvictionRunsMillis = 60000;
	// 配置连接在池中的最小生存时间
	private Integer minEvictableIdleTimeMillis = 300000;
	// SQL查询,用来验证从连接池取出的连接,在将连接返回给调用者之前.如果指定,则查询必须是一个SQL SELECT并且必须返回至少一行记录
	private String validationQuery = "SELECT 1 FROM DUAL";
	// 指明连接是否被空闲连接回收器(如果有)进行检验.如果检测失败,则连接将被从池中去除.注意:
	// 设置为true后如果要生效,validationQuery参数必须设置为非空字符串
	private Boolean testWhileIdle = true;
	// 指明是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个.注意:
	// 设置为true后如果要生效,validationQuery参数必须设置为非空字符串
	private Boolean testOnBorrow = false;
	// 指明是否在归还到池中前进行检验注意: 设置为true后如果要生效,validationQuery参数必须设置为非空字符串
	private Boolean testOnReturn = false;
	// 打开PSCache，并且指定每个连接上PSCache的大小 开启池的prepared statement 池功能
	private Boolean poolPreparedStatements = true;
	// 打开PSCache，并且指定每个连接上PSCache的大小
	private Integer maxPoolPreparedStatementPerConnectionSize = 20;
	// 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
	private String filters = "log4j,wall,mergeStat";
	// 通过connectProperties属性来打开mergeSql功能；慢SQL记录
	private String connectionProperties = "druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000";
	// # 合并多个DruidDataSource的监控数据
	private Boolean useGlobalDataSourceStat = true;

	public void config(DruidDataSource dataSource) {

		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);

		dataSource.setDriverClassName(driverClassName);
		dataSource.setInitialSize(initialSize); // 定义初始连接数
		dataSource.setMinIdle(minIdle); // 最小空闲
		dataSource.setMaxActive(maxActive); // 定义最大连接数
		dataSource.setMaxWait(maxWait); // 最长等待时间

		dataSource.setConnectionProperties(connectionProperties);
		dataSource.setUseGlobalDataSourceStat(useGlobalDataSourceStat);
		// 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
		dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);

		// 配置一个连接在池中最小生存的时间，单位是毫秒
		dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		dataSource.setValidationQuery(validationQuery);
		dataSource.setTestWhileIdle(testWhileIdle);
		dataSource.setTestOnBorrow(testOnBorrow);
		dataSource.setTestOnReturn(testOnReturn);

		// 打开PSCache，并且指定每个连接上PSCache的大小
		dataSource.setPoolPreparedStatements(poolPreparedStatements);
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);

		try {
			dataSource.setFilters(filters);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
