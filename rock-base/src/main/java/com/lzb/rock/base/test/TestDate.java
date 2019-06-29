package com.lzb.rock.base.test;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class TestDate {
	@JSONField(format = "yyyy-MM-dd HH:mm:ss SSS")
	Date startDate;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	Date endDate;

}
