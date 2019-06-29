package com.lzb.rock.base.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.lzb.rock.base.aop.annotion.Sign;
import com.lzb.rock.base.common.ResultEnum;
import com.lzb.rock.base.exception.RockException;
import com.lzb.rock.base.util.UtilClass;
import com.lzb.rock.base.util.UtilSign;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
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
public class SignAop {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${sgin.checkSignKey:}")
	String baseCheckSignKey;// 校验签名秘钥
	@Value("${sgin.signKey:}")
	String baseSignKey;// 返回值签名秘钥

	@Pointcut(value = "@annotation(com.lzb.rock.base.aop.annotion.Sign)")
	public void sign() {
	}

	@Around("sign()")
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
		Sign signAnnotation = currentMethod.getAnnotation(Sign.class);
		String signKey = signAnnotation.signKey();
		String checkSignKey = signAnnotation.checkSignKey();
		boolean checkSign = signAnnotation.checkSign();
		boolean sign = signAnnotation.sign();
		String[] checkNames = signAnnotation.checkNames();

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
			Object[] params = point.getArgs();
			String[] names = UtilClass.getFieldsName(classType, methodName);
			// 获取签名值,参数名称必须为sgin,类型必须为string
			String sginValue = null;
			Integer sginInndex = 0;
			for (String name : names) {
				if ("sgin".equals(name)) {
					sginValue = params[sginInndex].toString();
					break;
				}
				sginInndex++;
			}
			
			if (StringUtils.isBlank(sginValue)) {
				throw new RockException(ResultEnum.SIGN_ERR, "签名值为空");
			}

			// 规则,若注解中checkNames 有值，则只取checkNames指定的值校验
			// 若无指定，则除sgin之外的所有参数,2个参数以上指定才生效
			// 若签名只有一个参数,则直接签名
			// 若签名参数有多个，则根据参数名称组成map

			// 获取签名参数
			Object obj = null;
			if (names.length < 2) {
				throw new RockException(ResultEnum.SIGN_ERR, "签名参数不够");
			} else if (names.length == 2) {
				if (sginInndex == 0) {
					obj = params[1];
				} else {
					obj = params[0];
				}
			} else if (names.length > 2) {
				JSONObject obj2 = new JSONObject();
				Integer sginInndex2 = 0;

				// 判断是否指定签名参数
				boolean flag = false;
				List<String> list = null;
				if (checkNames.length > 0) {
					flag = true;
					list = Arrays.asList(checkNames);
				}
				// 未指定参数
				if (!flag) {
					for (String name : names) {
						if (!"sgin".equals(name)) {
							obj2.put(name, params[sginInndex2]);
						}
						sginInndex2++;
					}
					obj = obj2;
				} else {
					// 指定参数
					// 指定参数只有一个
					if (list.size() == 1) {
						for (String name : names) {
							if (!"sgin".equals(name) && list.contains(name)) {
								obj = params[sginInndex2];
								break;
							}
						}
					} else {
						for (String name : names) {
							if (!"sgin".equals(name) && list.contains(name)) {
								obj2.put(name, params[sginInndex2]);
							}
							sginInndex2++;
						}
						obj = obj2;
					}
				}
			}
			// 签名校验
			if (!UtilSign.check(obj, checkSignKey, sginValue)) {
				logger.debug("obj={},key={},sgin={}", obj, checkSignKey, sginValue);
				throw new RockException(ResultEnum.SIGN_ERR, "签名不匹配");
			}
		}

		// 执行方法
		Object rs = point.proceed();
		
		if(sign) {
			//获取签名秘钥
			if(StringUtils.isBlank(signKey)) {
				if(StringUtils.isNotBlank(baseSignKey)) {
					signKey=baseSignKey;
				}else if(StringUtils.isNotBlank(checkSignKey)) {
					signKey=checkSignKey;
				}else if(StringUtils.isNotBlank(baseCheckSignKey)) {
					signKey=baseCheckSignKey;
				}
			}
			if(StringUtils.isBlank(signKey)) {
				throw new RockException(ResultEnum.SIGN_ERR, "签名秘钥为空");
			}
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes();
			HttpServletResponse resp = attributes.getResponse();
			resp.setHeader("sgin", UtilSign.sign(rs, signKey));
		}
		return rs;
	}
}
