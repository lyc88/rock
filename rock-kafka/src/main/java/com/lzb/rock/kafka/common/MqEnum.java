package com.lzb.rock.kafka.common;

/**
 * 消息枚举
 * 
 * @author lzb
 *
 *         2018年12月17日 下午2:32:16
 */
public enum MqEnum {
	/**
	 * -------------------测试消息消息-------------------------------------------------
	 */

	TEST_USER(MqTopic.TEST_USER, "KDpePgEmlo75r7GW1"), TEST_LIST_USER(MqTopic.TEST_LIST_USER, "KDpePgEmlo75r7GW2"),
	TEST_STRING(MqTopic.TEST_STRING, "KDpePgEmlo75r7GW3"), TEST_NOT(MqTopic.TEST_NOT, "KDpePgEmlo75r7GW4"),

	/**
	 * --------------------平台公共消息-------------------------------------------------
	 */
	/**
	 * 营销短信
	 */
	SHARE_MARKETING_SMS(MqTopic.SHARE_MARKETING_SMS, "KDpePgEmlo75r7GW"),

	/**
	 * 通知类短信
	 */
	SHARE_NOTICE_SMS(MqTopic.SHARE_NOTICE_SMS, "zY6y3XpHZGHswQlH"),

	/**
	 * --------------------尊鸿荟-------------------------------------------------
	 */

	/**
	 * 会员付款消息
	 */
	ZHH_MEMBER_PAY(MqTopic.ZHH_MEMBER_PAY, "qdty9ufrTQBcSmjv"),
	/**
	 * 尊鸿荟领取优惠券
	 */
	ZHH_MEMBER_GET_COUPON(MqTopic.ZHH_MEMBER_GET_COUPON, "kBOkfo1DCw9NViNq"),

	/**
	 * 尊鸿荟充值消息
	 */
	ZHH_MEMBER_RECHARGE(MqTopic.ZHH_MEMBER_RECHARGE, "im3MCpHylNs9dlzT"),
	/**
	 * 会员注册
	 */
	ZHH_MEMBER_REGISTER(MqTopic.ZHH_MEMBER_REGISTER, "Gr0yxxlPE2nrboKY");
	/**
	 * 消息主题，用于订阅
	 */
	private String topic;
	/**
	 * 消息签名秘钥
	 */
	private String sginkey;

	MqEnum(String topic, String sginkey) {
		this.topic = topic;
		this.sginkey = sginkey;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getSginkey() {
		return sginkey;
	}

	public void setSginkey(String sginkey) {
		this.sginkey = sginkey;
	}

}
