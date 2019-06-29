package com.lzb.rock.base.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

/**
 * 对象复制 根据对象属性名称复制
 * 
 * @author lzb 2018年2月8日 下午5:09:03
 */
public class UtilObject {

	/**
	 * 复制对象,根据属性名称复制对象,属性名称必须保持一致
	 * 
	 * @param source
	 * @param targetClass
	 * @return
	 */
	public static <T> T javaBean(Object source, Class<T> targetClass) {
		T t = null;
		if (source != null) {
			String jsonStr = UtilJson.getStr(source);
			JSONObject obj = (JSONObject) JSON.parse(jsonStr);
			t = obj.toJavaObject(targetClass);
		}
		return t;
	}

	/**
	 * 复制对象
	 * 
	 * @param source
	 * @param type
	 * @return
	 */
	public static <T> T javaBean(Object source, TypeReference<T> type) {
		T t = null;
		if (source != null) {
			String jsonStr = UtilJson.getStr(source);
			t = JSON.parseObject(jsonStr, type);
		}
		return t;
	}

	/**
	 * 复制list，属性名称必须保持一致
	 * 
	 * @param source
	 * @param targetClass
	 * @return
	 */
	public static <T> List<T> javaList(Object source, Class<T> targetClass) {
		List<T> list = null;
		if (source != null) {
			String jsonStr = UtilJson.getStr(source);
			JSONArray obj = (JSONArray) JSON.parse(jsonStr);
			list = obj.toJavaList(targetClass);

		}
		return list;
	}

	/**
	 * 合并对象，目标对象有值，则不会覆盖，只能复制对象
	 * 
	 * @param target      目标对象
	 * @param source      复制源
	 * @param targetClass 返回值泛型
	 * @return
	 */
	public static <T> T mergeJavaObject(Object target, Object source, Class<T> targetClass) {
		JSONObject targetjSON = new JSONObject();
		JSONObject sourceJson = new JSONObject();
		if (target != null) {
			targetjSON = (JSONObject) JSONObject.toJSON(target);
		}
		if (source != null) {
			sourceJson = (JSONObject) JSONObject.toJSON(source);
		}
		Set<String> targetSet = targetjSON.keySet();
		for (String key : sourceJson.keySet()) {
			if (!targetSet.contains(key)) {
				targetjSON.put(key, sourceJson.get(key));
			} else {
				Object obj = targetjSON.get(key);
				// null覆盖 String 类型空字符串也覆盖
				if (obj == null) {
					targetjSON.put(key, sourceJson.get(key));
				} else if (obj instanceof String) {
					if (StringUtils.isBlank(obj.toString())) {
						targetjSON.put(key, sourceJson.get(key));
					}
				}

			}
		}
		return targetjSON.toJavaObject(targetClass);
	}

	/**
	 * 对象组中是否存在 Empty Object
	 *
	 * @param os 对象组
	 * @return
	 */
	public static boolean isOneEmpty(Object... os) {
		for (Object o : os) {
			if (isEmpty(o)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 对象是否为空
	 *
	 * @param obj String,List,Map,Object[],int[],long[]
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object o) {
		if (o == null) {
			return true;
		}
		if (o instanceof String) {
			if (o.toString().trim().equals("")) {
				return true;
			}
		} else if (o instanceof List) {
			if (((List) o).size() == 0) {
				return true;
			}
		} else if (o instanceof Map) {
			if (((Map) o).size() == 0) {
				return true;
			}
		} else if (o instanceof Set) {
			if (((Set) o).size() == 0) {
				return true;
			}
		} else if (o instanceof Object[]) {
			if (((Object[]) o).length == 0) {
				return true;
			}
		} else if (o instanceof int[]) {
			if (((int[]) o).length == 0) {
				return true;
			}
		} else if (o instanceof long[]) {
			if (((long[]) o).length == 0) {
				return true;
			}
		}
		return false;
	}
}
