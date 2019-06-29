package com.lzb.rock.base.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lzb.rock.base.common.ResultEnum;
import com.lzb.rock.base.exception.RockException;

/**
 * 反射工具类
 * 
 * @author lzb
 *
 */
public class UtilClass {
	/**
	 * 使用javassist来获取方法参数名称
	 * 
	 * @param class_name  类名
	 * @param method_name 方法名
	 * @return
	 * @throws Exception
	 */
	public static String[] getFieldsName(String class_name, String method_name) throws Exception {
//		Class<?> clazz = Class.forName(class_name);
//		String clazz_name = clazz.getName();
//		ClassPool pool = ClassPool.getDefault();
//		ClassClassPath classPath = new ClassClassPath(clazz);
//		pool.insertClassPath(classPath);
//
//		CtClass ctClass = pool.get(clazz_name);
//		CtMethod ctMethod = ctClass.getDeclaredMethod(method_name);
//		MethodInfo methodInfo = ctMethod.getMethodInfo();
//		CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
//		LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
//		if (attr == null) {
//			return null;
//		}
//		String[] paramsArgsName = new String[ctMethod.getParameterTypes().length];
//		int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;
//		for (int i = 0; i < paramsArgsName.length; i++) {
//			paramsArgsName[i] = attr.variableName(i + pos);
//		}
		return getFieldsNameBySpring(class_name, method_name);
	}

	/**
	 * 通过spring的LocalVariableTableParameterNameDiscoverer
	 * 
	 * @param class_name
	 * @param method_name
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static String[] getFieldsNameBySpring(String class_name, String method_name) throws ClassNotFoundException {
		Class<?> clazz = Class.forName(class_name);
		LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			if (method_name.equals(method.getName())) {
				String[] params = u.getParameterNames(method);
				return params;
			}
		}
		return null;
	}

	/**
	 * 通过Java8的Parameter类 保留参数名这一选项由编译开关javac -parameters打开，则：参数名称被编译进了class文件。
	 * 
	 * 保留参数名这一选项由编译开关javac -parameters关闭，则：参数名称是无意义的org0、org1...
	 * 
	 * 但是，javac -parameters默认是关闭的，在eclipse中可以在 Preferences -- compiler -- store
	 * information about method parameters(usable via reflection) 打开。
	 * --------------------- 作者：付蛋糕的小笔记 来源：CSDN
	 * 原文：https://blog.csdn.net/qq_34169240/article/details/78340275
	 * 版权声明：本文为博主原创文章，转载请附上博文链接！
	 * 
	 * @param clazz
	 * @param methodName
	 * @return
	 */
	public static List<String> getParameterNameJava8(Class clazz, String methodName) {
		List<String> paramterList = new ArrayList<>();
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			if (methodName.equals(method.getName())) {
				Parameter[] params = method.getParameters();
				for (Parameter parameter : params) {
					paramterList.add(parameter.getName());
				}
			}
		}
		return paramterList;
	}

	/**
	 * 对象中String空字符串转换null，以免数据库更新 泛型不可更新
	 * 
	 * @param obj
	 * @param targetClass
	 * @return
	 */
	public static <T> T setNull(T obj) {

		Class<? extends Object> clazz = obj.getClass();
		// 获取实体类的所有属性，返回Field数组
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			// 可访问私有变量
			field.setAccessible(true);
			// 获取属性类型
			String type = field.getGenericType().toString();
			// 如果type是类类型，则前面包含"class "，后面跟类名
			if ("class java.lang.String".equals(type)) {
				// 将属性的首字母大写
				String methodName = field.getName().replaceFirst(field.getName().substring(0, 1), field.getName().substring(0, 1).toUpperCase());
				try {
					Method methodGet = clazz.getMethod("get" + methodName);
					// 调用getter方法获取属性值
					String str = (String) methodGet.invoke(obj);
					if (StringUtils.isBlank(str)) {
						field.set(obj, null);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return obj;
	}

	/**
	 * 字符串转换对应对象
	 * 
	 * @param str
	 * @param type
	 * @return
	 */
	public static Object getJavaBeanByString(String str, Type type) {
		if (str == null) {
			return null;
		}
		// 判断是不是json字符串
		boolean isJson = UtilJson.isJSONObject(str);
		// JSON字符串
		if (isJson) {
			Object jsonObj = JSON.parse(str);
			if (jsonObj instanceof JSONObject) {
				JSONObject obj = (JSONObject) jsonObj;
				return obj.toJavaObject(type);
			} else if (jsonObj instanceof JSONArray) {
				JSONArray obj = (JSONArray) jsonObj;
				return obj.toJavaObject(type);
			}
		}
		// 非JSON字符串
		if (!isJson) {
			String typeName = type.getTypeName();
			if (typeName.equals("int") || typeName.equals("java.lang.Integer")) {
				if (StringUtils.isNotBlank(str)) {
					return Integer.valueOf(str);
				} else {
					return null;
				}
			} else if (typeName.equals("java.lang.String")) {
				return str;
			} else if (typeName.equals("java.lang.Double") || typeName.equals("double")) {
				if (StringUtils.isNotBlank(str)) {
					return Double.valueOf(str);
				} else {
					return null;
				}
			} else if (typeName.equals("java.lang.Long") || typeName.equals("long")) {
				if (StringUtils.isNotBlank(str)) {
					return Long.valueOf(str);
				} else {
					return null;
				}
			} else if (typeName.equals("java.lang.Short") || typeName.equals("short")) {
				if (StringUtils.isNotBlank(str)) {
					return Short.valueOf(str);
				} else {
					return null;
				}
			} else if (typeName.equals("java.lang.Byte") || typeName.equals("byte")) {
				if (StringUtils.isNotBlank(str)) {
					return Byte.valueOf(str);
				} else {
					return null;
				}
			} else if (typeName.equals("java.lang.Float") || typeName.equals("float")) {
				if (StringUtils.isNotBlank(str)) {
					return Float.valueOf(str);
				} else {
					return null;
				}
			} else if (typeName.equals("java.lang.Boolean") || typeName.equals("boolean")) {
				if (StringUtils.isNotBlank(str)) {
					return Boolean.valueOf(str);
				} else {
					return false;
				}
			} else if (typeName.equals("java.math.BigDecimal")) {
				if (StringUtils.isNotBlank(str)) {
					return new BigDecimal(str);
				} else {
					return null;
				}
			} else {
				throw new RockException(ResultEnum.TYPE_ERR, "类型异常" + typeName);
			}
		}
		throw new RockException(ResultEnum.TYPE_ERR, "类型异常" + type.getTypeName());
	}

	public static void main(String[] args) throws Exception {
		String[] aa = UtilClass.getFieldsName("com.jiahong.base.util.UtilCopy", "javaBean");
		for (String string : aa) {
			System.out.println(string);
		}

	}
}
