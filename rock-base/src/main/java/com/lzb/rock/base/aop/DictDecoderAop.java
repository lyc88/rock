package com.lzb.rock.base.aop;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.lzb.rock.base.aop.annotion.DictDecoder;
import com.lzb.rock.base.common.ResultEnum;
import com.lzb.rock.base.exception.RockException;
import com.lzb.rock.base.util.UtilDict;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Order(Integer.MAX_VALUE)
@Slf4j
public class DictDecoderAop {

	@Before("@annotation(com.lzb.rock.base.aop.annotion.DictDecoder)")
	private void before(JoinPoint point) throws Throwable {
		// 获取拦截的方法名
		Signature sig = point.getSignature();
		MethodSignature msig = null;
		if (!(sig instanceof MethodSignature)) {
			throw new RockException(ResultEnum.AOP_ERR, "该注解只能用于方法");
		}
		// 判断
		msig = (MethodSignature) sig;
		Object target = point.getTarget();
		Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());

		// 参数值列表
		Object[] params = point.getArgs();
		// 参数类型列表
		Parameter[] parameters = currentMethod.getParameters();
		Integer index = 0;
		for (Parameter parameter : parameters) {
			/**
			 * 判断参数是否加了DictDecoder注解
			 */
			if (parameter.isAnnotationPresent(DictDecoder.class)) {
				// 转换
				UtilDict.decoder(params[index]);
			}
			index++;
		}
	}
}
