package com.lzb.rock.base.xss;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XssFilter implements Filter {

	FilterConfig filterConfig = null;

	private List<String> urlExclusion = null;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	public void destroy() {
		this.filterConfig = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String servletPath = httpServletRequest.getServletPath();

		if (servletPath.startsWith("/static/")) {
			chain.doFilter(request, response);
		} else if (urlExclusion != null && urlExclusion.contains(servletPath)) {
			chain.doFilter(request, response);
		} else {
			// log.debug("XssFilter.doFilter==>{}", servletPath);
			chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);
		}
	}

	public List<String> getUrlExclusion() {
		return urlExclusion;
	}

	public void setUrlExclusion(List<String> urlExclusion) {
		this.urlExclusion = urlExclusion;
	}
}