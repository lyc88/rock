package com.lzb.rock.kafka.aop;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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
import com.lzb.rock.base.util.UtilClass;

@Aspect
@Component
@Order(9999)
public class MqKafkaListenerAop {

	@Pointcut(value = "@annotation(org.springframework.kafka.annotation.KafkaListener)")
	public void mqKafkaListener() {
	}

	@Around("mqKafkaListener()")
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

		for (Object object : params) {
			if (object instanceof ConsumerRecord) {
				/**
				 * 查找消息接收消息消息体真实类型
				 */
				Type valueTypes = null;
				Type[] genericParameterTypes = currentMethod.getGenericParameterTypes();
				for (Type genericParameterType : genericParameterTypes) {
					if (genericParameterType instanceof ParameterizedType) {
						ParameterizedType aType = (ParameterizedType) genericParameterType;
						Type[] parameterArgTypes = aType.getActualTypeArguments();

						if (parameterArgTypes != null && parameterArgTypes.length > 1) {
							valueTypes = parameterArgTypes[1];
						}
					}
				}

				// 修改值
				ConsumerRecord<String, Object> record = (ConsumerRecord<String, Object>) object;
				Object value = record.value();

				if (value!=null && value instanceof String && valueTypes != null) {
					String typeName = valueTypes.getTypeName();
					if (typeName.indexOf("String") < 0) {
						value = UtilClass.getJavaBeanByString(value.toString(), valueTypes);
						Field nameField = record.getClass().getDeclaredField("value");
						nameField.setAccessible(true);
						nameField.set(record, value);
					}
				}
				break;
			}
		}
		// 执行方法
		Object rs = point.proceed();

		return rs;
	}

}
