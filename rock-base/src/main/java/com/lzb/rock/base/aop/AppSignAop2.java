package com.lzb.rock.base.aop;
//package com.jiahong.ouyi.base.aop;
//
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.serializer.SerializerFeature;
//import com.jiahong.ouyi.base.ResultEnum;
//import com.jiahong.ouyi.base.common.Constants;
//import com.jiahong.ouyi.base.exception.BusinessException;
//import com.jiahong.ouyi.base.security.Security;
//
//import lombok.extern.slf4j.Slf4j;
//
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang.StringUtils;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.Signature;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//
///**
// * 校验签名，添加签名值 校验签名的值必须为参数，签名值参数默认为sgin不可更改,类型为字符串
// * 
// * @author SEELE
// *
// */
//@Aspect
//@Component
//@Order(999)
//@Slf4j
//public class AppSignAop {
//
//
//	@Pointcut(value = "@annotation(com.jiahong.ouyi.base.aop.annotion.AppSign)")
//	public void appSign() {
//	}
//
//	@Around("appSign()")
//	private Object handle(ProceedingJoinPoint point) throws Throwable {
//		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
//				.getRequestAttributes();
//		HttpServletRequest request = attributes.getRequest();
//		HttpServletResponse response = attributes.getResponse();
//
//
//		Object[] params = point.getArgs();
//
//		Object obj = JSONObject.toJSON(params[0]);
//		String data =null;
//		String apisign=null;
//		if(obj instanceof JSONObject) {
//			JSONObject obj2=(JSONObject) obj;
//			
//			data=obj2.getString("data");
//			Object apisignObj = obj2.get("apisign");
//			apisign=JSON.toJSONString(apisignObj,SerializerFeature.SortField, SerializerFeature.MapSortField);
//		}else {
//			throw new BusinessException(ResultEnum.SIGN_ERR,"参数异常,对象不能为list");
//		}
//		int statusCode = response.getStatus();
//		System.out.println("data======>"+data);
//	//	log.debug("方式={}，状态码={}", request.getMethod(), statusCode);
//	//	log.info("请求地址={}，参数={}", request.getRequestURL(), json);
//
//		if (statusCode != 200) {
//			HttpStatus status = HttpStatus.valueOf(statusCode);
//			throw new BusinessException(ResultEnum.STATUS_ERR,status.value() +";"+status.getReasonPhrase());
//			}
//
//		// 校验签名是否正确
//		if (StringUtils.isNotBlank(data)) {
//			String md5sign = Security.MD5(Constants.APP_KEY +data);
//			if (!md5sign.equals(apisign)) {
//				log.warn("秘钥验证不通过：参数秘钥：{}, 产生的秘钥：{}", apisign, md5sign);
//				throw new BusinessException(ResultEnum.SIGN_ERR);
//			}
//			log.debug("秘钥验证通过：参数秘钥：{}, 产生的秘钥：{}", apisign, md5sign);
//		}
//		// 执行方法
//		Object rs = point.proceed();
////		 attributes = (ServletRequestAttributes) RequestContextHolder
////				.getRequestAttributes();
////		response = attributes.getResponse();
////		if("OPTIONS".equals(request.getMethod())){
////			response.addHeader("Access-Control-Allow-Origin", "*");
////			response.addHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Origin,Content-Type");
////			response.addHeader("Access-Control-Allow-Headers", "token");
////			//预检一次设置一个有效期，在有效期内（单位秒）不再重复预检,OPTIONS预检请求
////			response.addHeader("Access-Control-Max-Age", "3600");
////		}else{
////			response.addHeader("Access-Control-Allow-Origin", "*");
////		}
//		return rs;
//	}
////	/**
////	 * 提取参数
////	 * @param json
////	 * @return
////	 */
////	private RequestInfo<String> getRequestParam(String json) {
////		RequestInfo<String> resultInfo = new RequestInfo<String>();
////		int signIndex = json.indexOf("\"apisign\":");
////		int dataIndex = json.indexOf("\"data\":");
////		String sign = null;
////		String data = null;
////		String regBefore = "(?<=(\"apisign\":|\"data\":)).*(?=(,\"data\"|,\"apisign\"))";
////		String regAfter = "(?<=(,\"apisign\":|,\"data\":)).*(?<!}$)";
////		 Pattern pattern = Pattern.compile(regBefore);
////		 Matcher matcher = pattern.matcher(json);
////			 if(matcher.find()){
////		        	
////		        	if(signIndex<dataIndex){
////		        		sign = matcher.group();
////		        		log.debug("sign="+sign);
////		        	}else{
////		        		data = matcher.group();
////		        		log.debug("data="+data);
////		        	}
////		        	
////		        }
////			 pattern = Pattern.compile(regAfter);
////			 matcher = pattern.matcher(json);
////			 if(matcher.find()){
////		        	if(signIndex>dataIndex){
////		        		sign = matcher.group();
////		        		log.debug("sign="+sign);
////		        	}else{
////		        		data = matcher.group();
////		        		log.debug("data="+data);
////		        	}
////		        }
////			 resultInfo.setApisign(sign);
////			 resultInfo.setData(data);
////		return resultInfo;
////	}
////
////	/**
////	 * 响应内容
////	 * @param response
////	 * @param code
////	 * @param msg
////	 * @throws Exception
////	 */
////	protected Result<String> resultInfo(String code, String msg) throws IOException {
////		Result<String> resultInfo = new Result<>();
////		resultInfo.setCode(code);
////		resultInfo.setMsg(msg);
////		return resultInfo;
////	}
//	
//}
