package com.lzb.rock.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.lzb.rock.base.aop.annotion.Beetl;


/**
 * 日期工具类
 * @author lzb
 * 2018年2月1日 下午3:18:39
 */
@Component
@Beetl(name = "UtilDate")
public class UtilDate {

	/**
	 * 获取当前时间Long值 格式 yyyyMMddHHmmssSSS
	 * 
	 * @return
	 */
	public static Long getFomtTimeByDateLong() {
		String time = getFomtDateByThisTime("yyyyMMddHHmmssSSS");
		return Long.valueOf(time);
	}

	/**
	 * 获取当前时间String值 格式 yyyyMMddHHmmssSSS
	 * 
	 * @return
	 */
	public static String getFomtTimeByDateString() {
		String time = getFomtDateByThisTime("yyyyMMddHHmmssSSS");
		return time;
	}

	/**
	 * 格式 yyyyMMddHHmmssSSS 转换为date
	 * 
	 * @return
	 */
	public static Date getDateByLong(Long date) {
		return getDateByFomtDate("yyyyMMddHHmmssSSS", date.toString());
	}
	/**
	 * 格式 yyyyMMddHHmmssSSS 转换为date
	 * 
	 * @return
	 */
	public static Date getDateByString(String date) {
		return getDateByFomtDate("yyyyMMddHHmmssSSS", date);
	}

	/**
	 * 获取当前时间Long值 标准时间戳
	 * 
	 * @return
	 */
	public static Long getDateTimeByDate() {
		return getDateTimeByDate(new Date());
	}

	/**
	 * 获取指定时间long值 标准时间戳
	 * 
	 * @param date
	 * @return
	 */
	public static Long getDateTimeByDate(Date date) {
		return date.getTime();
	}

	/**
	 * 获取标准时间戳转换为int
	 * 
	 * @return
	 */
	public static Integer getTimeInteger() {
		return getDateTimeByDate().intValue();
	}

	/**
	 * 获取指定Long值 指定格式 时间戳
	 * 
	 * @return
	 */
	public static Long getDateTimeByFomtDate(String fomt, String dateStr) {
		Date date = getDateByFomtDate(fomt, dateStr);
		return getDateTimeByDate(date);
	}

	/**
	 * 标准时间戳long值转换为date类型
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateByDateTime(Long date) {
		return new Date(date);
	}

	/**
	 * 指定格式字符串转date类型
	 * @param fomt
	 * @param dateStr
	 * @return
	 */
	public static Date getDateByFomtDate(String fomt, String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(fomt);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取当前时间转换指定格式
	 * 
	 * @param fomt
	 * @return
	 */
	public static String getFomtDateByThisTime(String fomt) {
		SimpleDateFormat sdf = new SimpleDateFormat(fomt);
		return sdf.format(new Date());
	}
	/**
	 * 格式化指定时间
	 * 
	 * @param fomt
	 * @return
	 */
	public static String getFomtDateByDate(String fomt,Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(fomt);
		return sdf.format(date);
	}
	/**
	 * 获取系统当前时间戳(10位到秒)
	 *
	 * @return int
	 */
	public static long time() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * 获得今天凌晨时间戳
	 * @return int
	 */
	public static int dayBreakTime() {
		String nowTime = getFomtDateByThisTime("yyyy-MM-dd") + " 00:00:00"; // 获取今天日期时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Date date = null;
		try {
			date = sdf.parse(nowTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (int) (date.getTime() / 1000); // 转化时间戳
	}
	/**
	 * 获得今天凌晨时间
	 * 
	 * @return int
	 */
	public static Date dayBreak() {
		String nowTime = getFomtDateByThisTime("yyyy-MM-dd") + " 00:00:00"; // 获取今天日期时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Date date = null;
		try {
			date = sdf.parse(nowTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取1970-00-00 00:00:00的date
	 * 
	 * @return
	 */
	public static Date getStartDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse("1970-00-00 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 指定日期加上天数后的日期
	 * 
	 * @param date 指定日期
	 * @param num  为增加的天数
	 * @return
	 * @throws ParseException
	 */
	public static Date addDayDate(Date date, Integer num) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
		return ca.getTime();
	}

	// 当前日期加上天数：

	/**
	 * 当前日期加上天数后的日期
	 * 
	 * @param num 为增加的天数
	 * @return
	 */
	public static Date addDay(Integer num) {

		
		return addDayDate(new Date(), num);
	}

	/**
	 * 在原日期的基础上增加小时数
	 * 
	 * @param date
	 * @param i
	 * @return
	 */
	public static Date addHourOfDate(Date date, Integer i) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.HOUR_OF_DAY, i);
		return ca.getTime();
	}
	
	/**
	 * 在原日期的基础上增加分钟
	 * 
	 * @param date
	 * @param i
	 * @return
	 */
	public static Date addMinuteOfDate(Date date, Integer i) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.MINUTE, i);
		return ca.getTime();
	}

	/**
	 * solr日期格式化
	 * 
	 * @param date
	 * @return
	 */
	public static String solrFormat(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fmt = sdf.format(date);
		fmt = fmt.replaceAll(" ", "T");
		fmt = fmt + "Z";
		return fmt;

	}
}
