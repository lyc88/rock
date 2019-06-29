package com.lzb.rock.base.test;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class test {

	public static void main(String[] args) {
		TestDate t = new TestDate();
//		t.setStartDate(new Date());
//		t.setEndDate(new Date());
		String str = JSON.toJSONString(t);
		System.out.println(str);
	}

}
