package com.lzb.rock.base.config;

import feign.Response;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.support.ResponseEntityDecoder;
import org.springframework.cloud.netflix.feign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;

import com.lzb.rock.base.common.ResultEnum;
import com.lzb.rock.base.exception.RockException;
import com.lzb.rock.base.exception.BusinessExceptionResponse;
import com.lzb.rock.base.exception.UtilExceptionStackTrace;

public class GeneralFeignConfiguration {
	Logger log = LoggerFactory.getLogger(GeneralFeignConfiguration.class);
	@Autowired
	private ObjectFactory<HttpMessageConverters> messageConverters;

	/**
	 * 配置错误解析器
	 *
	 * @return the error decoder
	 */
	@Bean
	public ErrorDecoder configErrorDecoder() {
		return new BusinessExceptionDecoder(new ResponseEntityDecoder(new SpringDecoder(this.messageConverters)));
	}

	/**
	 * 解析BusinessException
	 */
	class BusinessExceptionDecoder implements ErrorDecoder {

		private Decoder decoder;

		public BusinessExceptionDecoder(Decoder decoder) {
			this.decoder = decoder;
		}

		@Override
		public Exception decode(String methodKey, Response response) {
			try {
				// 从服务端返回的异常
				BusinessExceptionResponse ber = (BusinessExceptionResponse) decoder.decode(response,
						BusinessExceptionResponse.class);
				// 重新封装
				return new RockException(ResultEnum.REST_ERR, ber.getMessage());
			} catch (Exception e) {
				log.error("unknown error occurred when call service: {}，ex:{}", methodKey,
						UtilExceptionStackTrace.getStackTrace(e));
				return new RockException(ResultEnum.SYSTTEM_ERR, "Service Unavailable");
			}
		}
	}
}
