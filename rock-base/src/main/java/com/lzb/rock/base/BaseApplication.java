package com.lzb.rock.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.google.common.base.Predicates;
import com.lzb.rock.base.properties.RockProperties;

import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * 启动基类
 * 
 * @author lzb 2018年2月8日 上午11:41:55
 */
@Api(tags = { "默认接口" })
public abstract class BaseApplication {

	@Autowired
	RockProperties rockProperties;

	@GetMapping("/home")
	@ResponseBody
	public String home() {
		return "Hello world";
	}

	@GetMapping("/info")
	@ResponseBody
	public String info() {
		return "info";
	}

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public RibbonRest initializationRibbonRest() {
		RibbonRest ribbonRest = new RibbonRest();
		return ribbonRest;
	}

	// https://blog.csdn.net/u010963948/article/details/72476854
	// http://127.0.0.1:15027/swagger-ui.html

	@Bean
	public Docket createRestApi() {// 创建API基本信息
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).enable(rockProperties.getSwaggerOnOff()).select()
				.apis(Predicates.or(RequestHandlerSelectors.basePackage("com.lzb"), RequestHandlerSelectors.basePackage("com.jiahong")))// 扫描该包下的所有需要在Swagger中展示的API，@ApiIgnore注解标注的除外
				.paths(PathSelectors.any()).build();
	}

	public ApiInfo apiInfo() {// 创建API的基本信息，这些信息会在Swagger UI中进行显示
		return new ApiInfoBuilder().title("API标题")// API 标题
				.description("API描述")// API描述
				.contact("联系人")// 联系人
				.version("1.0")// 版本号
				.build();
	}
}
