package com.lzb.rock.base.aop;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lzb.rock.base.Result;
import com.lzb.rock.base.aop.annotion.Log;
import com.lzb.rock.base.common.ResultEnum;
import com.lzb.rock.base.exception.RockException;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Order(1)
@Slf4j
public class LogAop {

	@Pointcut(value = "execution(* com.lzb.rock..*.controller..*.*(..))")
	public void controller() {
	}

//* com.xyz.service..*.*(..)
	@Pointcut(value = "execution(* com.lzb.rock..*.service..*.*(..))")
	public void service() {
	}

	@Pointcut(value = "@annotation(com.lzb.rock.base.aop.annotion.LogOut)")
	public void logOut() {
	}

	@Around("controller()")
	private Object controllerHandle(ProceedingJoinPoint point) throws Throwable {
		return handle(point);
	}

	@Around("service()")
	private Object serviceHandle(ProceedingJoinPoint point) throws Throwable {

		return handle(point);
	}

	@Around("logOut()")
	private Object logOutHandle(ProceedingJoinPoint point) throws Throwable {
		return handle(point);
	}

	private Object handle(ProceedingJoinPoint point) throws Throwable {
		JSONObject logStartJson = new JSONObject();
		// 获取拦截的方法名
		Signature sig = point.getSignature();
		MethodSignature msig = null;
		if (!(sig instanceof MethodSignature)) {
			throw new RockException(ResultEnum.AOP_ERR, "该注解只能用于方法");
		}
		// 类完整路径
		String classType = point.getTarget().getClass().getName();
		// 判断

		msig = (MethodSignature) sig;
		Object target = point.getTarget();
		Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		// 方法名
		String methodName = currentMethod.getName();
		// 完整方法路径
		String path = classType + "." + methodName;
		// 获取拦截方法的参数
		String className = point.getTarget().getClass().getName();
		Object[] params = point.getArgs();
		String[] names = ((CodeSignature) point.getSignature()).getParameterNames();
		logStartJson.put("path", path);
		// 获取注解参数
		Log logAnnotation = currentMethod.getAnnotation(Log.class);
		boolean before = true;
		boolean end = true;
		boolean endAll = true;
		String methodNameaAias = "";
		if (logAnnotation != null) {
			before = logAnnotation.before();
			end = logAnnotation.end();
			endAll = logAnnotation.endAll();
			methodNameaAias = logAnnotation.name();
			if (StringUtils.isNotBlank(methodNameaAias)) {
				logStartJson.put("methodNameaAias", methodNameaAias);
			}
		}
		// 判断是否打印入参
		if (before) {
			// 判断是否为action 或者controller,若是打印访问url
			if (classType != null) {
				String classType1 = classType.toLowerCase();
				if (attributes != null && (classType1.indexOf("action") > -1 || classType1.indexOf("controller") > -1)) {

					HttpServletRequest request = attributes.getRequest();
					String url = request.getRequestURL().toString();
					String method = request.getMethod();
					String addr = request.getRemoteAddr();
					String head = request.getHeader("head");
					// 记录下请求内容
					logStartJson.put("addr", addr);
					logStartJson.put("method", method);
					logStartJson.put("url", url);
					logStartJson.put("head", head);
					// 获取request的参数
				} else {
					logStartJson.put("className", className);
				}
				// 获取方法参数
				// String[] names = UtilClass.getFieldsName(classType, methodName);
				if (params != null) {
					int size = params.length;
					for (int i = 0; i < size; i++) {
						Object param = params[i];
						String name = names[i];
						if (param instanceof HttpServletRequest || param instanceof HttpServletResponse) {
							continue;
						}
						logStartJson.put(name, param);
					}
				}
			}
			// 打印参数
			log.info(logStartJson.toJSONString());
		}
		// 执行方法
		Object rs = point.proceed();
		// 完全不打印返回值
		if (endAll) {
			JSONObject logEndJson = new JSONObject();
			logEndJson.put("path", path);
			// 是否打印返回值
			if (end) {
				logEndJson.put("result", rs);
			} else if (rs != null && rs instanceof Result) {
				Result result = (Result) rs;
				JSONObject obj = new JSONObject();
				obj.put("code", result.getCode());
				obj.put("msg", result.getMsg());
				obj.put("errMsgs", result.getErrMsgs());
				obj.put("data", "不输出");
				logEndJson.put("result", obj);
			}
			logEndJson.put("path", path);
			if (attributes != null) {
				HttpServletResponse resp = attributes.getResponse();
				String head = resp.getHeader("head");
				logEndJson.put("head", head);

				// JSONObject.DEFFAULT_DATE_FORMAT="yyyy-MM-dd";//设置日期格式
				String jsonStr = JSONObject.toJSONString(logEndJson, SerializerFeature.WriteMapNullValue, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteDateUseDateFormat);
				log.info(jsonStr);
			}
		}

		return rs;
	}

}
