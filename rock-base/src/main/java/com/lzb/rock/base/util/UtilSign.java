package com.lzb.rock.base.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lzb.rock.base.common.ResultEnum;
import com.lzb.rock.base.exception.RockException;
import com.lzb.rock.base.model.Head;

import lombok.extern.slf4j.Slf4j;

/**
 * 调用签名
 * 
 * @author lzb
 *
 */
@Slf4j
@Component
public class UtilSign {
	/**
	 * 默认编码
	 */
	public static String defaultCharsetName = "UTF-8";

	/**
	 * 判断是否符合签名
	 * 
	 * @param obj  签名对象
	 * @param key  秘钥
	 * @param sign 原始签名
	 * @return
	 */
	public static boolean check(Object obj, String key, String sign) {

		if (StringUtils.isBlank(sign)) {
			throw new RockException(ResultEnum.PAEAM_ERR, "签名值不能为空");
		}

		if (StringUtils.isBlank(key)) {
			throw new RockException(ResultEnum.PAEAM_ERR, "签名秘钥不能为空");
		}
		if (obj == null) {
			throw new RockException(ResultEnum.PAEAM_ERR, "签名对象不能为空");
		}

		String newsign = sign(obj, key);

		if (StringUtils.isBlank(newsign)) {
			throw new RockException(ResultEnum.SYSTTEM_ERR, "签名发生未知异常");
		}
		boolean flag = newsign.equals(sign);
		if (!flag) {
			log.warn("newsign={};key={};sign={};obj={},class={}", newsign, key, sign, obj, obj.getClass());
		}
		return flag;
	}

	/**
	 * 签名
	 * 
	 * @param json 只能List集合
	 * @param key  秘钥
	 * @return
	 */
	public static String sign(List<?> list, String key) {
		if (StringUtils.isBlank(key)) {
			throw new RockException(ResultEnum.PAEAM_ERR, "签名秘钥不能为空");
		}
		if (list == null || list.size() == 0) {
			throw new RockException(ResultEnum.PAEAM_ERR, "签名对象不能为空");
		}
		JSONArray arr = (JSONArray) JSONObject.toJSON(list);
		return sign(arr, key);
	}

	/**
	 * 签名
	 * 
	 * @param json 只能List集合
	 * @param key  秘钥
	 * @return
	 */
	public static String sign(Object obj, String key) {

		if (StringUtils.isBlank(key)) {
			throw new RockException(ResultEnum.PAEAM_ERR, "签名秘钥不能为空");
		}
		if (obj == null) {
			throw new RockException(ResultEnum.PAEAM_ERR, "签名对象不能为空");
		}
		if (obj instanceof String) {
			// 判断是不是json
			if (obj.toString().startsWith("{") && obj.toString().endsWith("}")) {
				JSONObject jsonObj = (JSONObject) JSON.parse(obj.toString());
				return sign(jsonObj, key);
			} else if (obj.toString().startsWith("[") && obj.toString().endsWith("]")) {
				JSONArray jsonObj = (JSONArray) JSON.parse(obj.toString());
				return sign(jsonObj, key);
			} else {
				return sign(obj.toString(), key);
			}
		} else {
			JSONObject jsonObj = (JSONObject) JSONObject.toJSON(obj);
			return sign(jsonObj, key);
		}

	}

	/**
	 * 签名
	 * 
	 * @param json 只能map集合
	 * @param key  秘钥
	 * @return
	 */
	public static String sign(Map<?, ?> map, String key) {
		if (StringUtils.isBlank(key)) {
			throw new RockException(ResultEnum.PAEAM_ERR, "签名秘钥不能为空");
		}
		if (map == null) {
			throw new RockException(ResultEnum.PAEAM_ERR, "签名对象不能为空");
		}
		JSONObject jsonObj = (JSONObject) JSONObject.toJSON(map);

		return sign(jsonObj, key);
	}

	/**
	 * 签名
	 * 
	 * @param json 只能JSONObject对象
	 * @param key  秘钥
	 * @return
	 */
	public static String sign(JSONObject json, String key) {
		if (StringUtils.isBlank(key)) {
			throw new RockException(ResultEnum.PAEAM_ERR, "签名秘钥不能为空");
		}
		if (json == null) {
			throw new RockException(ResultEnum.PAEAM_ERR, "签名对象不能为空");
		}
		// 移除签名
		json.remove("sign");
		// JSON排序
		String str = JSONObject.toJSONString(json, SerializerFeature.SortField, SerializerFeature.MapSortField);
		str = key + str;
		return sign(str, key);
	}

	/**
	 * 签名
	 * 
	 * @param json 只能JSONArray对象
	 * @param key  秘钥
	 * @return
	 */
	private static String sign(JSONArray json, String key) {
		if (StringUtils.isBlank(key)) {
			throw new RockException(ResultEnum.PAEAM_ERR, "签名秘钥不能为空");
		}
		if (json == null || json.size() == 0) {
			throw new RockException(ResultEnum.PAEAM_ERR, "签名对象不能为空");
		}
		// JSON排序
		String str = JSONArray.toJSONString(json, SerializerFeature.SortField, SerializerFeature.MapSortField);
		str = key + str;
		return sign(str, key);
	}

	/**
	 * 签名基类，不可修改
	 * 
	 * @param obj         json对象或者java对象
	 * @param key         签名前后添加秘钥
	 * @param charsetName 编码格式
	 * @return
	 */
	private static String sign(String str, String key) {
		log.debug("key:{},value:{}", key, str);
		byte[] by = null;
		try {
			by = str.getBytes(defaultCharsetName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String sg = DigestUtils.md5Hex(by);
		return sg;
	}

	//
	public static void main(String[] args) {
		String json = "{\"memberId\":100303,\"circleId\":100306}";
		Object obj = JSONObject.parse(json);
		String key = "ruanjie2018@JiaHong?3782oOuyi!00RJ";
		List<Head> list = new ArrayList<>();
		Head head = new Head();
		head.setNumberId("123456");
		list.add(head);
		String aa = UtilSign.sign(list, key);

		System.out.println(aa);
	}

}
