package com.lzb.rock.base.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.lzb.rock.base.ssl.MySSLProtocolSocketFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * HTTP工具类 发送http/https协议get/post请求，发送map，json，xml，txt数据
 * 
 * @author happyqing 2016-5-20
 */
@Slf4j
public final class UtilHttp {

	/**
	 * 发送get请求
	 * 
	 * @param url
	 * @return
	 */
	public static String sendGet(String url) {

		return doGet(url, null, "utf-8", false, null, null);
	}

	/**
	 * 执行一个http/https get请求，返回请求响应的文本数据
	 * 
	 * @param url     请求的URL地址
	 * @param params  请求的查询参数,key value 或者凭借字符串
	 * @param charset 字符集
	 * @param pretty  是否美化
	 * @return 返回请求响应的文本数据
	 */
	public static String doGet(String url, String params, String charset, boolean pretty, HttpClient client, Map<String, String> header) {
		StringBuffer response = new StringBuffer();
		if (client == null) {
			client = new HttpClient();
		}
		if (UtilString.isNotBlank(charset)) {
			charset = "UTF-8";
		}

		if (url.startsWith("https")) {
			// https请求
			Protocol myhttps = new Protocol("https", new MySSLProtocolSocketFactory(), 443);
			Protocol.registerProtocol("https", myhttps);
		}
		HttpMethod method = new GetMethod(url);
		if (header != null) {
			for (Entry<String, String> entry : header.entrySet()) {
				method.addRequestHeader(entry.getKey(), entry.getValue());
			}
		}

		try {
			if (StringUtils.isNotBlank(params)) {
				// 判断是否拼接字符串
				if (params.indexOf("=") > -1 || params.indexOf("&") > -1) {
					/**
					 * 对get请求参数编码，汉字编码后，就成为%式样的字符串
					 */
					method.setQueryString(URIUtil.encodeQuery(params));
				}
				if (UtilJson.isJSONObject(params)) {
					JSONObject obj = UtilJson.getJSONObject(params);
					List<NameValuePair> nameValuePairs = new ArrayList<>();
					for (Entry<String, Object> entry : obj.entrySet()) {
						String key = entry.getKey();
						Object value = entry.getValue();
						if (UtilString.isNotBlank(key) && value != null) {
							NameValuePair nameValuePair = new NameValuePair();
							nameValuePair.setName(key);
							nameValuePair.setValue(value.toString());
							nameValuePairs.add(nameValuePair);
						}
					}
					NameValuePair[] query = new NameValuePair[nameValuePairs.size()];
					nameValuePairs.toArray(query);
					method.setQueryString(query);
				}
			}

			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));
				String line;
				while ((line = reader.readLine()) != null) {
					if (pretty) {
						response.append(line).append(System.getProperty("line.separator"));
					} else {
						response.append(line);
					}
				}
				reader.close();
			}
		} catch (URIException e) {
			log.error("执行Get请求时，参数“" + params + "”发生异常！", e);
		} catch (IOException e) {
			log.error("执行Get请求" + url + "时，发生异常！", e);
		} finally {
			method.releaseConnection();
		}
		return response.toString();
	}

	/**
	 * post 请求 param 传参数
	 * 
	 * @param url     请求地址
	 * @param params  key value 数据
	 * @param charset 编码
	 * @param pretty  是否优化
	 * @return
	 */
	public static String postParam(String url, String params, String charset, boolean pretty, HttpClient client, Map<String, String> header) {
		StringBuffer response = new StringBuffer();
		if (client == null) {
			client = new HttpClient();
		}
		if (UtilString.isNotBlank(charset)) {
			charset = "UTF-8";
		}
		if (url.startsWith("https")) {
			// https请求
			Protocol myhttps = new Protocol("https", new MySSLProtocolSocketFactory(), 443);
			Protocol.registerProtocol("https", myhttps);
		}
		PostMethod method = new PostMethod(url);

		if (header != null) {
			for (Entry<String, String> entry : header.entrySet()) {
				method.addRequestHeader(entry.getKey(), entry.getValue());
			}
		}
		// 设置参数的字符集
		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
		// 设置post数据
		if (params != null) {
			JSONObject obj = UtilJson.getJSONObject(params);
			for (Entry<String, Object> entry : obj.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (UtilString.isNotBlank(key) && value != null) {
					method.setParameter(key, value.toString());
				}
			}
		}
		try {
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));
				String line;
				while ((line = reader.readLine()) != null) {
					if (pretty) {
						response.append(line).append(System.getProperty("line.separator"));
					} else {
						response.append(line);
					}
				}
				reader.close();
			} else {
				log.error("请求失败，错误码:", method.getStatusCode());
			}
		} catch (IOException e) {
			log.error("执行Post请求" + url + "时，发生异常！", e);
		} finally {
			method.releaseConnection();
		}
		return response.toString();

	}

	/**
	 * 执行一个http/https post请求， 直接写数据 json,xml,txt
	 * 
	 * @param url     请求的URL地址
	 * @param params  请求的查询参数,可以为null
	 * @param charset 字符集
	 * @param pretty  是否美化
	 * @return 返回请求响应的文本数据
	 */
	public static String postBody(String url, String content, String charset, boolean pretty, HttpClient client, Map<String, String> header) {
		StringBuffer response = new StringBuffer();
		if (client == null) {
			client = new HttpClient();
		}
		if (UtilString.isNotBlank(charset)) {
			charset = "UTF-8";
		}

		if (url.startsWith("https")) {
			// https请求
			Protocol myhttps = new Protocol("https", new MySSLProtocolSocketFactory(), 443);
			Protocol.registerProtocol("https", myhttps);
		}

		PostMethod method = new PostMethod(url);

		if (header != null) {
			for (Entry<String, String> entry : header.entrySet()) {
				method.addRequestHeader(entry.getKey(), entry.getValue());
			}
		}
		try {
			// 设置请求头部类型参数
			// method.setRequestHeader("Content-Type","text/plain;
			// charset=utf-8");//application/json,text/xml,text/plain
			// method.setRequestBody(content); //InputStream,NameValuePair[],String
			// RequestEntity是个接口，有很多实现类，发送不同类型的数据
			RequestEntity requestEntity = new StringRequestEntity(content, "application/json", charset);// application/json,text/xml,text/plain
			method.setRequestEntity(requestEntity);
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));
				String line;
				while ((line = reader.readLine()) != null) {
					if (pretty) {
						response.append(line).append(System.getProperty("line.separator"));
					} else {
						response.append(line);
					}
				}
				reader.close();
			} else {
				log.error("请求失败，错误码:", method.getStatusCode());
			}
		} catch (Exception e) {
			log.error("执行Post请求" + url + "时，发生异常！", e);
		} finally {
			method.releaseConnection();
		}
		return response.toString();
	}

	/**
	 * 返回二进制数据
	 * 
	 * @param url
	 * @param content
	 * @param charset
	 * @return
	 */
	public static byte[] writePost(String url, String content, String charset, String contentType) {
		StringBuffer response = new StringBuffer();
		HttpClient client = new HttpClient();
		if (url.startsWith("https")) {
			// https请求
			Protocol myhttps = new Protocol("https", new MySSLProtocolSocketFactory(), 443);
			Protocol.registerProtocol("https", myhttps);
		}
		PostMethod method = new PostMethod(url);
		byte[] b = null;
		try {
			// 设置请求头部类型参数
			// method.setRequestHeader("Content-Type","text/plain;
			// charset=utf-8");//application/json,text/xml,text/plain
			// method.setRequestBody(content); //InputStream,NameValuePair[],String
			// RequestEntity是个接口，有很多实现类，发送不同类型的数据
			if (UtilString.isBlank(contentType)) {
				contentType = "text/xml";
			}
			RequestEntity requestEntity = new StringRequestEntity(content, contentType, charset);// application/json,text/xml,text/plain
			method.setRequestEntity(requestEntity);
			client.executeMethod(method);

			if (method.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream bytestream = new ByteArrayOutputStream();

				// IOUtils.readFully(input, length)
				InputStream in = method.getResponseBodyAsStream();
				Integer ch = 0;
				while ((ch = in.read()) != -1) {
					bytestream.write(ch);
				}
				b = bytestream.toByteArray();
				bytestream.close();
				in.close();
			}
		} catch (Exception e) {
			log.error("执行Post请求" + url + "时，发生异常！", e);
		} finally {
			method.releaseConnection();
		}
		return b;
	}

//	public static void main(String[] args) {
//
//		String y = doGet("http://video.sina.com.cn/life/tips.html", null, "GBK", true, null);
//		System.out.println(y);
//		String url = "https://www.baidu.com/baidu?wd=张三&tn=monline_7_dg&ie=utf-8";
//		JSONObject obj = new JSONObject();
//		obj.put("wd", "张三");
//		obj.put("tn", "monline_7_dg");
//		obj.put("ie", "utf-8");
//		y = doGet("http://video.sina.com.cn/life/tips.html", obj.toJSONString(), "GBK", true, null);
//		System.out.println(y);
//	}

}
