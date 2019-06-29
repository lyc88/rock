package com.lzb.rock.base.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.lzb.rock.base.aop.annotion.Beetl;

import lombok.Data;

/**
 * 全局项目配置
 * 
 * @author lzb
 * 
 *         2019年3月31日 下午6:58:43
 */
@PropertySource(value = "classpath:rock.properties", ignoreResourceNotFound = true, encoding = "UTF-8")
@ConfigurationProperties(prefix = RockProperties.PREFIX)
@Configuration
@Beetl(name = "rockProperties")
@Data
public class RockProperties {

	public static final String PREFIX = "rock";

	/**
	 * 验证码 开关 false 关闭
	 */
	private Boolean kaptchaOnOff = false;
	/**
	 * swagger日志，false 关闭
	 */
	private Boolean swaggerOnOff = false;
	/**
	 * 文件上传路径
	 */
	private String fileUploadPath;

	/**
	 * session 失效时间（默认为30分钟 单位：秒）
	 */
	private Integer sessionTimeout = 30 * 60;

	/**
	 * session 验证失效时间（默认为15分钟 单位：秒）
	 */
	private Integer sessionValidationInterval = 15 * 60;

	/**
	 * 系统简称
	 */
	public String sysSimpleName = "rock";
	/**
	 * 系统全称
	 */
	public String sysWholeName = "rock管理系统";
	/**
	 * 是否开启多数据源 false 不开启
	 */
	public boolean mutiDatasourceOnOff = false;
	
	/**
	 * admin 使用的数据源
	 */
	public String sysDataSourceKey="rock-system";

}
