package com.lzb.rock.mybaits.properties;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

/**
 * 多数据源配置文件
 * 
 * @author admin
 *
 */
@PropertySource(value = "classpath:muti-datasource.properties", ignoreResourceNotFound = true, encoding = "UTF-8")
@Configuration
@ConfigurationProperties(prefix = "spring")
@EnableConfigurationProperties
@Data
public class MutiDatasource {
	  /**
     * 从配置文件中读取的datasource开头的数据
     * 注意：名称必须与配置文件中保持一致
     */
	Map<String, String> datasource = new HashMap<>();
}
