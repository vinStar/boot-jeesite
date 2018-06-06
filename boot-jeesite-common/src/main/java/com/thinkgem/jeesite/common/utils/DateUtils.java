/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author ThinkGem
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}

	/**
	 * 获取下个月日期 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getNextMonthDate(String pattern) {
		return DateFormatUtils.format(addDays(new Date(),30), pattern);
	}

	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*60*1000);
	}
	
	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*1000);
	}
	
	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }
	/**
	 * json转换为日期（pattern "yyyy-MM-dd"）
	 * @param timeMillis
	 * @param pattern
	 * @return
	 */
    public static String formatDateTime(Long timeMillis,Object pattern){
    	Calendar c = Calendar.getInstance();
		 
		c.setTimeInMillis(timeMillis);
	 
		System.out.println(c.getTime());
	 
		return formatDate(c.getTime(),pattern) ;
    }
	
	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}
	
	/**
	 * 获取传入日期所在周周一的日期
	 * @return
	 */
	public static Date getWeekMonday(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		//System.out.println(cal.getTime()); 
		return cal.getTime();
	}
	
	/**
	 * 获取传入日期所在周周日的日期
	 * @return
	 */
	public static Date getWeekSunday(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		//解决周日会出现 并到下一周的情况
		cal.add(Calendar.DAY_OF_YEAR, -1);
		// 这种输出的是上个星期周日的日期，因为老外那边把周日当成第一天
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		// 增加一个星期，才是我们中国人理解的本周日的日期
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		return cal.getTime();
	}
	
	/**
	 * 获取传入日期的上周周一的日期
	 * @param date
	 * @return
	 */
	public static Date getLastWeekMonday(Date date){
		//System.out.println(getWeekMonday(date)); 			 
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, -7);
		//System.out.println(cal.getTime()); 
		return getWeekMonday(cal.getTime());
	}
	
	/**
	 * 获取传入日期的上周周末的日期
	 * @param date
	 * @return
	 */
	public static Date getLastWeekSunday(Date date){
		//System.out.println(getWeekSunday(date)); 
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, -7);
		return getWeekSunday(cal.getTime());
	}
	
	/**
	 * 获取大上周的周一日期
	 * @param date
	 * @return
	 */
	public static Date getLastTwoWeekMonday(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, -14);
		return getWeekMonday(cal.getTime());
	}
	
	/**
	 * 获取大上周的周日日期
	 * @param date
	 * @return
	 */
	public static Date getLastTwoWeekSunday(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, -14);
		return getWeekSunday(cal.getTime());
	}
	
	/**
	 * 获取传入的日期为第几周
	 * @param date
	 * @return
	 */
	public static int weekNum(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 拼接周数
	 * @param
	 * @return
	 */
	public static int weekIndex(Date date){
		int i = DateUtils.weekNum(date);
		String num = "";
		if(i<10){
			num = "0"+i;
		}else {
			num = num +i;
		}
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy");
		String lastWeekIndexStr = df2.format(date);
		int lastWeekIndex = Integer.parseInt(lastWeekIndexStr + num);
		return lastWeekIndex;
	}
	
	
	
	
	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
//		System.out.println(formatDate(parseDate("2010/3/6")));
//		System.out.println(getDate("yyyy年MM月dd日 E"));
//		long time = new Date().getTime()-parseDate("2012-11-19").getTime();
//		System.out.println(time/(24*60*60*1000));
//		System.out.println(weekNum(new Date()));
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.DAY_OF_YEAR, -14);
//		Date date = getWeekMonday(new Date());
//		System.out.println(weekNum(date));

		getWeekFridays(new Date());


	}

	/**
	 * 获取当天零点倒计时
	 * @param date
	 * @return
	 */
	public static long pastHourMethod1(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		long t =  cal.getTimeInMillis() -new Date().getTime();
		return t/(60*60*1000);
	}

	/**
	 * 获取两个时间的差值
	 * @param endTime 结束时间
	 * @param startTime 开始时间
	 * @return
	 */
	public static long hourDiff(Date endTime,Date startTime) {
		long l = endTime.getTime() - startTime.getTime();
		return l/(60*60*1000);
	}

	/**
	 * 获取当前时间与结束时间的差值
	 * @param endTime 结束时间
	 * @return
	 */
	public static long hourDiff2(Date endTime) {
		long l = endTime.getTime() - new Date().getTime();
		return l/(60*60*1000);
	}

	/**
	 * 获取当前时间与当天下午6点的差值
	 * @return
	 */
	public static long pastHourMethod2() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 18);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		long t =  cal.getTimeInMillis() -new Date().getTime();
		return t/(60*60*1000);
	}

	/**
	 * 获取当前时间与当天下午10点的差值
	 * @return
	 */
	public static long pastHourMethod22() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 22);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		long t =  cal.getTimeInMillis() -new Date().getTime();
		return t/(60*60*1000);
	}

	/**
	 * 获取两个时间的差值
	 * @param time 上次结束开始时间
	 * @param timeStandard 任务周期时间
	 * @param type 任务类型（1：日任务；2：周任务；3：月任务；4：年任务）
	 * @return
	 */
	public static Map<String,Date> getStartEndTime(Date time,Date timeStandard,int type) {
		Date startDate = time;
		Date endDate = null;
		Map<String,Date> map=new HashMap<String,Date>();

		Calendar cal = Calendar.getInstance();
		cal.setTime(time);

		/*===================*/
		//String week = getWeek();


		if(type == 1){//日任务
			String week = DateUtils.formatDate(cal.getTime(), "EEEE");
			if(week.equals("星期五")){
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(time);
				cal2.add(Calendar.DAY_OF_MONTH,2);
				startDate = cal2.getTime();
				cal2.add(Calendar.DAY_OF_MONTH,1);
				endDate = cal2.getTime();
			}else {
				cal.add(Calendar.DAY_OF_MONTH,1);
				endDate = cal.getTime();
			}
		}else if(type == 2){//周任务
			cal.add(Calendar.DAY_OF_MONTH, 7);
			endDate = cal.getTime();
		}else if(type == 3){//月任务

			Integer ddS = Integer.parseInt(DateUtils.formatDate(timeStandard, "dd"));//任务时间为几号
			cal.add(Calendar.MONTH,1); //下个月
			int nextMonthDay = getMonthDay(cal.getTime()); //下月天数
			if(ddS >= nextMonthDay){
				Map<String, Date> dateOfLastMonth = getDateOfLastMonth(cal.getTime());
				endDate = dateOfLastMonth.get("end");//结束时间（下月最后一天）
			}else {
				cal.set(Calendar.DAY_OF_MONTH,ddS);
				endDate = cal.getTime();
			}


		}else if(type == 4){//年任务
			cal.add(Calendar.YEAR,1);
			endDate = cal.getTime();
		}

		map.put("startDate",startDate);
		map.put("endDate",endDate);
		return map;

	}


	/**
	 * 获取传入时间月第一天和最后一天
	 * @param date
	 * @return
	 */
	public static Map<String,Date> getDateOfLastMonth(Date date) {
		Map<String,Date> map=new HashMap<String,Date>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		map.put("first",cal.getTime());
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 0);
		map.put("end",cal.getTime());
		return map;
	}

	/**
	 * 取得当月天数
	 * */
	public static int getMonthDay(Date date)
	{
		Calendar a = Calendar.getInstance();
		a.setTime(date);
		a.set(Calendar.DATE, 1);//把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 获取第一次开始结束时间
	 * @param timeStandard 任务周期时间
	 * @param type 任务类型（1：日任务；2：周任务；3：月任务；4：年任务）
	 * @return
	 */
	public static Map<String,Date> getFirstStartEndTime(Date timeStandard,int type){
		Map<String,Date> map=new HashMap<String,Date>();
		Date nowDate = new Date();
		Date time = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(timeStandard);
		cal.set(Calendar.MILLISECOND, 0);
		int i = 1;
		if(nowDate.before(timeStandard)) i = -1;
		switch (type){
			case 1: cal.add(Calendar.DAY_OF_MONTH,1*i);
				time = cal.getTime();
				break;
			case 2: cal.add(Calendar.DAY_OF_MONTH, 7*i);
				time = cal.getTime();
				break;
			case 3:
				Integer ddS = Integer.parseInt(DateUtils.formatDate(timeStandard, "dd"));//任务时间为几号
				int nowMonthDay = getMonthDay(nowDate); //本月多少天
				cal.setTime(nowDate);

				//time开始   timeStandard结束
				//选择的日期数比当月天数大，则从上月开始周期，比如当月30天，选的是31号，则从上月开始，若果上月没有31号，从最后一天开始
				if(ddS>=nowMonthDay){
					cal.add(Calendar.MONTH,-1); //上个月
					int lastMonthDay = getMonthDay(cal.getTime()); //上月天数
					if(ddS >= lastMonthDay){//选择的日期数比当月天数大，则从上月最后一天开始
						Map<String, Date> dateOfLastMonth = getDateOfLastMonth(cal.getTime());
						time = dateOfLastMonth.get("end");//开始时间（上月最后一天）
					}else {
						cal.set(Calendar.DAY_OF_MONTH,ddS);
						time = cal.getTime();
					}
					//获取本月的最后一天,是结束时间
					cal.add(Calendar.MONTH, 2);
					cal.set(Calendar.DAY_OF_MONTH, 0);
					timeStandard = cal.getTime();

				}else {//选择的日期数没有当月天数大，则从本月开始
					//cal.set(Calendar.DAY_OF_MONTH,ddS);
					//本月的选中的那一天是开始时间
					cal.set(Calendar.DAY_OF_MONTH,ddS);
					time = cal.getTime();
					//结束时间
					cal.add(Calendar.MONTH,1); //下个月
					int nextMonthDay = getMonthDay(cal.getTime()); //下月天数
					if(ddS >= nextMonthDay){
						Map<String, Date> dateOfLastMonth = getDateOfLastMonth(cal.getTime());
						timeStandard = dateOfLastMonth.get("end");//结束时间（下月最后一天）
					}else {
						cal.set(Calendar.DAY_OF_MONTH,ddS);
						timeStandard = cal.getTime();
					}
				}
				break;
			case 4: cal.add(Calendar.YEAR,1*i);
				time = cal.getTime();
				break;
		}
		if(type != 3){
			if(nowDate.after(timeStandard)){
				//如果现在时间在timeStandard大于-开始时间是timeStandard  结束时间是后延
				map.put("startDate",timeStandard);
				map.put("endDate",time);
			}else {
				//如果现在时间在timeStandard小于-结束时间为timeStandard 开始时间往前推
				map.put("startDate",time);
				map.put("endDate",timeStandard);
			}
		}else {
			map.put("startDate",time);
			map.put("endDate",timeStandard);
		}
		return map;
	}


	/**
	 * 得到小时字符串 格式（dd）
	 */
	public static String getHour(Date time) {
		return formatDate(time, "HH");
	}

	/**
	 * 获取传入日期之前的周一的日期
	 * @return
	 */
	public static Map<String,Date> getWeekMondays(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		Date monday_1 = cal.getTime();
		cal.add(Calendar.DAY_OF_MONTH,-7);
		Date monday_2 = cal.getTime();
		Map<String,Date> map=new HashMap<String,Date>();
		map.put("monday_1",monday_1);
		map.put("monday_2",monday_2);
		return map;
	}

	/**
	 * 获取传入日期之前的周五的日期
	 * @return
	 */
	public static Map<String,Date> getWeekFridays(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)-1;
		if(dayOfWeek > 5){
			cal.add(Calendar.DAY_OF_MONTH,dayOfWeek-7);
		}else if(dayOfWeek < 5){
			cal.add(Calendar.DAY_OF_MONTH,-2-dayOfWeek);
		}
		Date friday_1 = cal.getTime();
		cal.add(Calendar.DAY_OF_MONTH,-7);
		Date friday_2 = cal.getTime();
		Map<String,Date> map=new HashMap<String,Date>();
		map.put("friday_1",friday_1);
		map.put("friday_2",friday_2);
		return map;
	}

}
