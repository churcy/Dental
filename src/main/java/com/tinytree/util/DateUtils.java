package com.tinytree.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description：日期时间处理工具类
 * @ClassName: DateUtils
 * @Author：tangyang
 * @Date：2015-12-24
 * (变更历史)
 *	
 * 如：eric	2015/01/08 修改了删除功能
 *
 */
public class DateUtils {
	
	public static final int MONDAY = 64;//二进制1000000
	
	public static final int TUESDAY = 32;//二进制0100000
	
	public static final int WEDNESDAY = 16;//二进制0010000
	
	public static final int THURSDAY = 8;//二进制0001000
	
	public static final int FRIDAY = 4;//二进制0000100
	
	public static final int SATURDAY = 2;//二进制0000010
	
	public static final int SUNDAY = 1;//二进制0000001
	
	public static final int[] WEEK_DAYS = {SUNDAY,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY};

	public static String format2String(Date date,String patten){
		if(date == null || !StringUtils.isNotEmpty(patten)){
			return null;
		}
			
		SimpleDateFormat format = new SimpleDateFormat(patten);
		return format.format(date);
	}
	
	/**
	 * 解析一周中的天数
	 * @param weekDays
	 * @return
	 */
	public static List<Integer> parseWeekDays(int weekDays) {
		if(weekDays < SUNDAY || weekDays > (MONDAY|TUESDAY|WEDNESDAY|THURSDAY|FRIDAY|SATURDAY|SUNDAY)){
			return null;
		}
		
		List<Integer> list = new ArrayList<Integer>();
		for(int i = 0;i < WEEK_DAYS.length;i++){
			if((WEEK_DAYS[i] & weekDays) == WEEK_DAYS[i]){
				list.add(WEEK_DAYS[i]);
			}
		}
		return list;
	}
	
	/**
	 * 查询一周中的某一天
	 * @param date
	 * @return
	 */
	public static Integer getWeekDay(Date date){
		if(date == null){
			return null;
		}
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		int dayOFWeek = cal.get(Calendar.DAY_OF_WEEK);
		return WEEK_DAYS[dayOFWeek-1];
	}
	
	/**
	 * 根据星期算日期，如果日期比当前日期靠前，则将日期往后推一周
	 * @param weekDay
	 * @return
	 */
	public static Date getDateByWeekDay(int weekDay){
		int dayOfWeek = 0;
		for(int i = 0;i < WEEK_DAYS.length;i++){
			if(WEEK_DAYS[i] == weekDay){
				dayOfWeek = i+1;
				break;
			}
		}
		
		if(dayOfWeek == 0){
			return null;
		}
		
		Date now = new Date();
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(now);
		cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		if(now.after(cal.getTime())){
			cal.set(Calendar.WEEK_OF_MONTH, cal.get(Calendar.WEEK_OF_MONTH)+1);
		}
		return cal.getTime();
	}
	
	/**
	 * 合并日期，将日期和时间合并成一个date
	 * @param date
	 * @param time
	 * @return
	 */
	public static Date mergeDate(Date date,Date time){
		if(date == null || time == null){
			return null;
		}
		
		GregorianCalendar cal = new GregorianCalendar();
		GregorianCalendar timeCal = new GregorianCalendar();
		cal.setTime(date);
		timeCal.setTime(time);
		cal.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
		cal.set(Calendar.SECOND, timeCal.get(Calendar.SECOND));
		return cal.getTime();
	}
	
	public static void main(String[] args) {
		System.out.println(getFiled(new Date(),Calendar.MONTH));
	}
	
	public static Date format2Date(String time,String patten) throws ParseException{
		if(time == null ||StringUtils.isBlank(patten)){
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat(patten);
		return format.parse(time);
	}
	
	/**
	 * 获取小时
	 * @param date
	 * @return
	 */
	public static int getFiled(Date date,int filed){
		if(date == null){
			throw new NullPointerException("getFiled date is null");
		}
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		return cal.get(filed);
	}
	
	/**
	 * 查询日期跟周是否匹配
	 * @param date
	 * @param week
	 * @return
	 */
	public static boolean checkWeek(Date date,int week){
		if(date == null){
			return false;
		}
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		int dayOFWeek = cal.get(Calendar.DAY_OF_WEEK);
		return WEEK_DAYS[dayOFWeek-1]==week;
	}
	
	public static int getAge(String param) throws ParseException{
		Date ageDate = format2Date(param, GlobalUtil.DATE_PATTERN);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(ageDate);
		Calendar calendarNow = Calendar.getInstance();
		int age;
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int yearNow = calendarNow.get(Calendar.YEAR);
		int monthNow = calendarNow.get(Calendar.MONTH);
		int dayNow = calendarNow.get(Calendar.DAY_OF_MONTH);
		do{
			age = yearNow - year;
			if(month < monthNow){
				break;
			}
			if(month > monthNow){
				if(age != 0){
					age = age - 1;
					break;
				}
			}
			if(month == monthNow){
				if(day > dayNow){
					age = age - 1;
					break;
				}
			}
		}while(false);
		return age;
	}
	public static String parseWeekDayToName(int weekDay){
		String name = null;
		switch(weekDay)
		{
		case MONDAY:name="星期一";
		break;
		case TUESDAY:name="星期二";
		break;
		case WEDNESDAY:name="星期三";
		break;
		case THURSDAY:name="星期四";
		break;
		case FRIDAY:name="星期五";
		break;
		case SATURDAY:name="星期六";
		break;
		case SUNDAY:name="星期日";
		}
		return name;
	}
	
	/**
	 * 获取指定日期到当前日期的秒数
	 * @param date
	 * @return
	 */
	public static long currentSecond2Now(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return Math.abs((calendar.getTimeInMillis()-System.currentTimeMillis())/1000);
	}
}
