package com.lzb.rock.web.properties;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.lzb.rock.base.util.UtilString;

/**
 * beetl配置(如果需要配置别的配置可参照这个形式自己添加)
 *
 * @author fengshuonan
 * @date 2017-05-24 20:37
 */
@Configuration
@ConfigurationProperties(prefix = WebProperties.BEETLCONF_PREFIX)
public class WebProperties {

	public static final String BEETLCONF_PREFIX = "beetl";

	/**
	 * beetl 脚本开始标签
	 */
	@Value("${delimiter-statement-start:@}")
	private String delimiterStatementStart;
	/**
	 * beetl 脚本结束标签
	 */
	@Value("${delimiter-statement-end:}")
	private String delimiterStatementEnd;
	/**
	 * beetl 模板所在路径
	 */
	@Value("${resource-tagroot:common/tags}")
	private String resourceTagroot;

	/**
	 * beetl 模板后缀
	 */
	@Value("${resource-tagsuffix:tag}")
	private String resourceTagsuffix;
	/**
	 * 是否检测文件变化,开发用true合适，但线上要改为false
	 */
	@Value("${resource-auto-check:false}")
	private String resourceAutoCheck;

	/**
	 * view 文件路径
	 */
	@Value("${spring.mvc.view.prefix:/WEB-INF/view}")
	private String prefix;

	public Properties getProperties() {
		Properties properties = new Properties();
		if (UtilString.isNotEmpty(delimiterStatementStart)) {
			if (delimiterStatementStart.startsWith("\\")) {
				delimiterStatementStart = delimiterStatementStart.substring(1);
			}
			properties.setProperty("DELIMITER_STATEMENT_START", delimiterStatementStart);
		}
		if (UtilString.isNotEmpty(delimiterStatementEnd)) {
			properties.setProperty("DELIMITER_STATEMENT_END", delimiterStatementEnd);
		} else {
			properties.setProperty("DELIMITER_STATEMENT_END", "null");
		}
		if (UtilString.isNotEmpty(resourceTagroot)) {
			properties.setProperty("RESOURCE.tagRoot", resourceTagroot);
		}
		if (UtilString.isNotEmpty(resourceTagsuffix)) {
			properties.setProperty("RESOURCE.tagSuffix", resourceTagsuffix);
		}
		if (UtilString.isNotEmpty(resourceAutoCheck)) {
			properties.setProperty("RESOURCE.autoCheck", resourceAutoCheck);
		}
		return properties;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getDelimiterStatementStart() {
		return delimiterStatementStart;
	}

	public void setDelimiterStatementStart(String delimiterStatementStart) {
		this.delimiterStatementStart = delimiterStatementStart;
	}

	public String getDelimiterStatementEnd() {
		return delimiterStatementEnd;
	}

	public void setDelimiterStatementEnd(String delimiterStatementEnd) {
		this.delimiterStatementEnd = delimiterStatementEnd;
	}

	public String getResourceTagroot() {
		return resourceTagroot;
	}

	public void setResourceTagroot(String resourceTagroot) {
		this.resourceTagroot = resourceTagroot;
	}

	public String getResourceTagsuffix() {
		return resourceTagsuffix;
	}

	public void setResourceTagsuffix(String resourceTagsuffix) {
		this.resourceTagsuffix = resourceTagsuffix;
	}

	public String getResourceAutoCheck() {
		return resourceAutoCheck;
	}

	public void setResourceAutoCheck(String resourceAutoCheck) {
		this.resourceAutoCheck = resourceAutoCheck;
	}
}
