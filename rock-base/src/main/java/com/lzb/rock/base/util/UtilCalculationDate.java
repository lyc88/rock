package com.lzb.rock.base.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: Lzz
 * @Description:计算年龄工具类,日期转换
 * @Version: 1.0.0
 * @Date; 2018-08-10 10:53
 */
public class UtilCalculationDate {

    /**
     * 根据年月日计算年龄,birthTimeString:"1994-11-14"  yyyy-MM-dd
     * @param birthTimeString
     * @return
     */
    public static int getAgeFromBirthTime(String birthTimeString) {
        // 先截取到字符串中的年、月、日
        String strs[] = birthTimeString.trim().split("-");
        int selectYear = Integer.parseInt(strs[0]);
        int selectMonth = Integer.parseInt(strs[1]);
        int selectDay = Integer.parseInt(strs[2]);
        // 得到当前时间的年、月、日
        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayNow = cal.get(Calendar.DATE);

        // 用当前年月日减去生日年月日
        int yearMinus = yearNow - selectYear;
        int monthMinus = monthNow - selectMonth;
        int dayMinus = dayNow - selectDay;

        int age = yearMinus;// 先大致赋值
        if (yearMinus < 0) {// 选了未来的年份
            age = 0;
        } else if (yearMinus == 0) {// 同年的，要么为1，要么为0
            if (monthMinus < 0) {// 选了未来的月份
                age = 0;
            } else if (monthMinus == 0) {// 同月份的
                if (dayMinus < 0) {// 选了未来的日期
                    age = 0;
                } else if (dayMinus >= 0) {
                    age = 1;
                }
            } else if (monthMinus > 0) {
                age = 1;
            }
        } else if (yearMinus > 0) {
            if (monthMinus < 0) {// 当前月>生日月
            } else if (monthMinus == 0) {// 同月份的，再根据日期计算年龄
                if (dayMinus < 0) {
                } else if (dayMinus >= 0) {
                    age = age + 1;
                }
            } else if (monthMinus > 0) {
                age = age + 1;
            }
        }
        return age;
    }

    /**
     * 根据时间戳（10位）计算年龄
     * @param birthTimeLong
     * @return
     */
    public static int getAgeFromBirthTime(long birthTimeLong) {
        Date date = new Date(birthTimeLong * 1000L);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String birthTimeString = format.format(date);
        return getAgeFromBirthTime(birthTimeString);
    }
    
    
    public static Date stringToDate(String str, String format) { 
    	  DateFormat dateFormat = new SimpleDateFormat(format); 
    	  Date date = null; 
    	  try { 
    	   date = dateFormat.parse(str); 
    	  } 
    	  catch (ParseException e) { 
    	  } 
    	  return date; 
    } 
    
    //返回格式化之后的日期
    public static Date getNewDate(Date date) { 
	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    String nowdayTime = dateFormat.format(date);
	    try {
			Date nowDate = dateFormat.parse(nowdayTime);
			return nowDate ;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    return null ;
    }
    
    
    /**
     * 日期转字符串
     * @param date
     * @return
     */
    public static String DateToStr(Date date) {
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);
        return str;
    }
    
    /**
     * 日期转字符串格式化
     * @param date
     * @return
     */
    public static String DateToStrg(Date date) {
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String str = format.format(date);
        return str;
    }
    
    
    /**
     * 字符串转日期
     * @param str
     * @return
     */
    public static Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    
    //获取当前月第一天：
    public static Date getFirseDate(){
    	 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    	 String firstday, lastday;  
         // 获取前月的第一天  
    	 Calendar  cale = Calendar.getInstance();  
         cale.add(Calendar.MONTH, 0);  
         cale.set(Calendar.DAY_OF_MONTH, 1);  
         firstday = format.format(cale.getTime());  
         return StrToDate(firstday);

    }
    
    //获取当前月最后一天
    public static Date getLastDate(){
		  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		  Calendar ca = Calendar.getInstance();    
   	      ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
   	      String last = format.format(ca.getTime());
   	      return StrToDate(last);
   }
    /**
     * 
     * 描述:获取下一个月的第一天.
     * 
     * @return
     */
    public static String getPerFirstDayOfMonth() {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return dft.format(calendar.getTime());
    }
    
    //判断两个时间是否超过一天
    public static boolean overOneDay(Date startTime,Date endTime){
        long  between = endTime.getTime() - startTime.getTime();
        if(between > (24* 3600000)){
            return true;
        }
        return false;
    }
           

}
