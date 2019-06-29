/**
 * @author Administrator
 *
 * 
 *2019年5月26日 上午11:10:21
 */
package com.lzb.rock.login.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lzb
 * 
 *         2019年5月26日 上午11:10:21
 */
@Slf4j
public class RockAuthcFilter extends FormAuthenticationFilter {
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		log.debug("onAccessDenied     ===============>");

		return super.onAccessDenied(request, response, mappedValue);
	}
}
