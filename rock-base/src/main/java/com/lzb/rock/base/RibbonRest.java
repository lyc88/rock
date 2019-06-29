package com.lzb.rock.base;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.lzb.rock.base.common.ResultEnum;
import com.lzb.rock.base.exception.UtilExceptionStackTrace;
import com.lzb.rock.base.util.UtilJson;
import com.lzb.rock.base.util.UtilSign;

/**
 * 远程服务调用公共类
 * 
 * @author lzb 2018年1月12日 上午10:50:27
 */
public class RibbonRest {
	private Logger log = LoggerFactory.getLogger(RibbonRest.class);
	@Autowired
	RestTemplate restTemplate;

	/**
	 * @param serviceName 服务名称
	 * @param path        服务路径
	 * @param parms       map对象
	 * @param targetClass
	 * @return
	 */
	public <T> Result<T> post(String serviceName, String path, String sginKey, Object parms, Class<T> targetClass) {
		String json = JSON.toJSONString(parms);
		String result = basePost(serviceName, path, sginKey, json);
		Result<T> rs = JSON.parseObject(result, new TypeReference<Result<T>>() {
		});
		return rs;
	}

	/**
	 * @param serviceName 服务名称
	 * @param path        服务路径
	 * @param parms       map对象
	 * @param targetClass
	 * @return
	 */
	public <T> Result<T> post(String serviceName, String path, String sginKey, JSONObject parms, Class<T> targetClass) {

		String json = parms.toJSONString();
		String result = basePost(serviceName, path, sginKey, json);
		Result<T> rs = JSON.parseObject(result, new TypeReference<Result<T>>() {
		});
		return rs;
	}

	/**
	 * 
	 * @param serviceName 服务名称
	 * @param path        相对路径
	 * @param sginKey     签名秘钥
	 * @param parms       参数
	 * @return
	 */
	private <T> String post(String serviceName, String path, String sginKey, T parms) {
		return basePost(serviceName, path, sginKey, parms);
	}

	/**
	 * 远程调用基类方法，不可修改
	 * 
	 * @param <T>
	 * 
	 * @param serviceName 服务名称
	 * @param path        服务路径
	 * @param parms       json格式字符串
	 * @return JSONObject
	 */
	private <T> String basePost(String serviceName, String path, String sginKey, T parms) {
		String sign = null;
		if (parms != null) {
			sign = UtilSign.sign(parms, sginKey);
		}
		Result<Object> result = new Result<Object>();

		if (StringUtils.isBlank(serviceName)) {
			result.error(ResultEnum.PAEAM_ERR, "服务名称不能为空");
		}
		if (StringUtils.isBlank(path)) {
			result.error(ResultEnum.PAEAM_ERR, "路徑不能为空");
		}

		String resultJSON = null;
		if (result.check()) {
			String url = "http://" + serviceName + path;

			HttpHeaders headers = new HttpHeaders();
			MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
			headers.setContentType(type);
			headers.add("Accept", MediaType.APPLICATION_JSON.toString());
			if (parms != null) {
				headers.add("sign", sign);
			}
			HttpEntity<T> formEntity = null;
			if (parms == null) {
				formEntity = new HttpEntity<T>(headers);
			} else {
				formEntity = new HttpEntity<T>(parms, headers);
			}
			try {
				resultJSON = restTemplate.postForObject(url, formEntity, String.class);
			} catch (Exception e) {
				log.error("url={},parms={}", url, parms);
				log.error(UtilExceptionStackTrace.getStackTrace(e));

				result.error(ResultEnum.REST_ERR, e.getMessage());
				resultJSON = UtilJson.getStr(result);
			}

		} else {
			resultJSON = JSONObject.toJSONString(result);
		}

		return resultJSON;
	}

	/**
	 * 拼接url
	 * 
	 * @param serviceName
	 * @param path
	 * @return
	 */
	private String getUrl(String serviceName, String path) {
		StringBuffer sb = new StringBuffer();
		sb.append("http://");
		sb.append(serviceName);
		if (sb.toString().endsWith("/")) {
			if (path.startsWith("/")) {
				sb.append(path.substring(1));
			} else {
				sb.append(path);
			}
		} else {
			if (path.startsWith("/")) {
				sb.append(path);
			} else {
				sb.append("/").append(path);
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		RibbonRest rest = new RibbonRest();
		System.out.println(rest.getUrl("serviceName", "/a/b"));
	}
}
