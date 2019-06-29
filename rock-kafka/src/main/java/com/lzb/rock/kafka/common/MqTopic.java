package com.lzb.rock.kafka.common;

/**
 * 消息主题定义类
 * 
 * @author lzb
 *
 *         2018年12月17日 上午11:10:40
 */
public interface MqTopic {
	/**
	 * 测试主题枚举
	 */
	final static String TEST_USER = "TEST_USER";
	final static String TEST_LIST_USER = "TEST_LIST_USER";
	final static String TEST_STRING = "TEST_LIST_STRING";
	final static String TEST_NOT = "TEST_NOT";

	/**
	 * 尊鸿荟会员注册
	 */
	final static String ZHH_MEMBER_REGISTER = "ZHH_MEMBER_REGISTER";

	/**
	 * 尊鸿荟充值
	 */
	final static String ZHH_MEMBER_RECHERGE = "ZHH_MEMBER_RECHERGE";
	
	/**
	 * 尊鸿荟付款
	 */
	final static String ZHH_MEMBER_PAY = "ZHH_MEMBER_PAY";
	
	/**
	 * 尊鸿荟充值
	 */
	final static String ZHH_MEMBER_RECHARGE = "ZHH_MEMBER_RECHARGE";

	/**
	 * 尊鸿荟领取优惠券消息
	 */
	final static String ZHH_MEMBER_GET_COUPON = "ZHH_MEMBER_GET_COUPON";

	/**
	 * 营销短信消息
	 */
	final static String SHARE_MARKETING_SMS = "SHARE_MARKETING_SMS";

	/**
	 * 通知短信消息
	 */
	final static String SHARE_NOTICE_SMS = "SHARE_NOTICE_SMS";

}
