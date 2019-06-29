package com.lzb.rock.mybaits.mybatisplus;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;

import lombok.extern.slf4j.Slf4j;

@Configuration
// 扫描dao或者是Mapper接口
@MapperScan("com.**.mapper")
@Slf4j
public  class BaseMybatisPlusConfig {
	/**
	 * mybatis-plus 分页插件
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor page = new PaginationInterceptor();
		page.setDialectType("mysql");
		return page;
	}
}
