package com.lzb.rock.base.config;
//package com.jiahong.base.config;
//
//import java.text.DateFormat;
//import java.text.FieldPosition;
//import java.text.ParseException;
//import java.text.ParsePosition;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
///**
// * https://blog.csdn.net/u014747616/article/details/77855224
// * 
// * @author lzb
// *
// *         2018年11月2日 上午8:20:31
// */
//public class MyDateFormat extends DateFormat {
//
//	private DateFormat dateFormat;
//
//	private SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//	private SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
//
//	public MyDateFormat(DateFormat dateFormat) {
//		this.dateFormat = dateFormat;
//	}
//
//	public MyDateFormat() {
//		super();
//	}
//
//	@Override
//	public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
//		return dateFormat.format(date, toAppendTo, fieldPosition);
//	}
//
//	@Override
//	public Date parse(String source, ParsePosition pos) {
//		Date date = null;
//
//		try {
//			if (source != null && source.length() > 27 && source.contains("T") && source.contains("+")) {
//				String str = source.substring(0, 23);
//				date = format2.parse(str, pos);
//			} else {
//				date = format1.parse(source, pos);
//			}
//
//		} catch (Exception e) {
//
//			date = dateFormat.parse(source, pos);
//		}
//
//		return date;
//	}
//
//	// 主要还是装饰这个方法
//	@Override
//	public Date parse(String source) throws ParseException {
//		Date date = null;
//
//		try {
//			// 先按我的规则来
//			if (source != null && source.length() > 27 && source.contains("T") && source.contains("+")) {
//				String str = source.substring(0, 23);
//				date = format2.parse(str);
//			} else {
//				date = format1.parse(source);
//			}
//		} catch (Exception e) {
//
//			// 不行，那就按原先的规则吧
//			date = dateFormat.parse(source);
//		}
//		if (date == null) {
//			try {
//				date = new Date(Long.valueOf(source));
//			} catch (Exception e) {
//			}
//		}
//
//		return date;
//	}
//
//	// 这里装饰clone方法的原因是因为clone方法在jackson中也有用到
//	@Override
//	public Object clone() {
//		Object format = this.dateFormat.clone();
//		return new MyDateFormat(dateFormat);
//	}
//}
