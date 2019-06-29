package com.lzb.rock.base.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzb.rock.base.aop.annotion.AppSign;
import com.lzb.rock.base.common.ResultEnum;
import com.lzb.rock.base.exception.RockException;
import com.lzb.rock.base.model.Head;
import com.lzb.rock.base.util.UtilClass;
import com.lzb.rock.base.util.UtilSign;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

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

/**
 * 校验签名，添加签名值 校验签名的值必须为参数，签名值参数默认为sgin不可更改,类型为字符串
 * 
 * @author SEELE
 *
 */
@Aspect
@Component
@Order(2)
public class AppSignAop {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${sgin.app.check.key:}")
	String baseCheckSignKey;// 校验签名秘钥
	@Value("${sgin.app.return.key:}")
	String baseSignKey;// 返回值签名秘钥

	@Pointcut(value = "@annotation(com.lzb.rock.base.aop.annotion.AppSign)")
	public void appSign() {
	}

	@Around("appSign()")
	private Object handle(ProceedingJoinPoint point) throws Throwable {
		// 获取拦截的方法名
		Signature sig = point.getSignature();
		if (!(sig instanceof MethodSignature)) {
			throw new RockException(ResultEnum.AOP_ERR, "该注解只能用于方法");
		}
		// 获取注解参数
		MethodSignature msig = null;
		String classType = point.getTarget().getClass().getName();
		msig = (MethodSignature) sig;
		Object target = point.getTarget();
		Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
		// 方法名
		String methodName = currentMethod.getName();
		AppSign signAnnotation = currentMethod.getAnnotation(AppSign.class);
		String signKey = signAnnotation.signKey();
		String checkSignKey = signAnnotation.checkSignKey();
		boolean checkSign = signAnnotation.checkSign();
		boolean sign = signAnnotation.sign();
		String[] checkNames = signAnnotation.checkNames();

		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest req = attributes.getRequest();
		String headStr = req.getHeader("head");
		if (StringUtils.isBlank(headStr)) {
			throw new RockException(ResultEnum.SIGN_ERR, "请求头为空");
		}
		Head head = JSONObject.toJavaObject(JSON.parseObject(headStr), Head.class);

		// 签名
		if (checkSign) {
			// 获取签名秘钥
			if (StringUtils.isBlank(checkSignKey)) {
				if (StringUtils.isNotBlank(baseCheckSignKey)) {
					checkSignKey = baseCheckSignKey;
				}
			}
			if (StringUtils.isBlank(checkSignKey)) {
				throw new RockException(ResultEnum.SIGN_ERR, "验证秘钥为空");
			}
			// 参数值列表
			Object[] params = point.getArgs();
			// 参数类型
			Parameter[] parameters = currentMethod.getParameters();
			// 获取参数注解
			// Annotation[] annotations = parameters[0].getDeclaredAnnotations();
			// 获取方法参数列表
			// String[] names = UtilClass.getFieldsName(classType, methodName);
			String[] names = ((CodeSignature) point.getSignature()).getParameterNames();
			// 获取签名值,参数名称必须为sgin,类型必须为string

			/**
			 * 签名值
			 */
			String sginValue = head.getSign();
			head.setSign(null);
			if (StringUtils.isBlank(sginValue)) {
				throw new RockException(ResultEnum.SIGN_ERR, "签名值为空");
			}
			// 签名只签body数据

			// 获取签名参数
			Object obj = null;
			if (names.length < 1) {
				throw new RockException(ResultEnum.SIGN_ERR, "签名参数不够");
			} else {
				Integer sginInndex2 = 0;
				for (String name : names) {
					// 判断是不是body对象
					Parameter parameter = parameters[sginInndex2];
					RequestBody[] requestBodys = parameter.getAnnotationsByType(RequestBody.class);
					if (requestBodys.length > 0) {
						obj = params[sginInndex2];
						break;
					}
					sginInndex2++;
				}
				// 未指定参数
			}
			// 签名校验
			if (!UtilSign.check(obj, checkSignKey, sginValue)) {
				logger.debug("obj={},key={},sgin={}", obj, checkSignKey, sginValue);
				throw new RockException(ResultEnum.SIGN_ERR, "签名不匹配");
			}
		}

		// 执行方法
		Object rs = point.proceed();
		if (sign) {
			// 获取签名秘钥
			if (StringUtils.isBlank(signKey)) {
				if (StringUtils.isNotBlank(baseSignKey)) {
					signKey = baseSignKey;
				}
			}
			if (StringUtils.isBlank(signKey)) {
				throw new RockException(ResultEnum.SIGN_ERR, "签名秘钥为空");
			}
			HttpServletResponse resp = attributes.getResponse();
			String sgin = UtilSign.sign(rs, signKey);
			head.setSign(sgin);

			resp.setHeader("head", JSON.toJSONString(head));
		}
		return rs;
	}
}
