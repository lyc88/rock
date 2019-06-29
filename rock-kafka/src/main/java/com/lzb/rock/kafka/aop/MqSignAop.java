package com.lzb.rock.kafka.aop;

import java.lang.reflect.Method;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.lzb.rock.base.common.ResultEnum;
import com.lzb.rock.base.exception.RockException;
import com.lzb.rock.base.util.UtilSign;
import com.lzb.rock.base.util.UtilString;
import com.lzb.rock.kafka.aop.annotion.MqSign;
import com.lzb.rock.kafka.exception.BusinessMqException;

import lombok.extern.slf4j.Slf4j;

/**
 * 校验签名，添加签名值 校验签名的值必须为参数，签名值参数默认为sgin不可更改,类型为字符串
 * 
 * @author SEELE
 *
 */
@Aspect
@Component
@Order(9998)
@Slf4j
public class MqSignAop {

	@Pointcut(value = "@annotation(com.lzb.rock.mq.aop.annotion.MqSign)")
	public void mqSign() {
	}

	@Around("mqSign()")
	private Object handle(ProceedingJoinPoint point) throws Throwable {
		// 获取拦截的方法名
		Signature sig = point.getSignature();
		if (!(sig instanceof MethodSignature)) {
			throw new RockException(ResultEnum.AOP_ERR, "该注解只能用于方法");
		}
		// 参数列表
		Object[] params = point.getArgs();

		MethodSignature msig = null;
		msig = (MethodSignature) sig;
		Object target = point.getTarget();
		Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
		// 获取注解参数
		MqSign signAnnotation = currentMethod.getAnnotation(MqSign.class);
		boolean flag = true;
		for (Object object : params) {
			if (object instanceof ConsumerRecord) {
				// 签名
				ConsumerRecord<String, Object> record = (ConsumerRecord<String, Object>) object;

				if (record.value() != null && UtilString.isNotBlank(record.value().toString())) {
					flag = UtilSign.check(record.value(), signAnnotation.value().getSginkey(), record.key());

					if (!flag) {
						log.info("签名失败;key={};value={}", record.key(), record.value());
						// throw new BusinessMqException();
					}
				}
				break;
			}
		}

		// 执行方法
		if (flag) {
			Object rs = point.proceed();
			return rs;
		} else {
			return null;
		}

	}
}
