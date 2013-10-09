/**
 ****************************************************
 *  Copyright (c) 2011-2012 二六三网络通信股份有限公司 
 ****************************************************
 * @Package com.net263.boss.util
 * @Title：DateHelper.java
 * @author:YongLiang.Wang
 * @Date:2011-4-8 下午03:00:06
 */
package com.luode.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * 类描述：日期处理帮助类
 * 
 * @author YongLiang.Wang
 * @time Apr 8, 2011 3:06:34 PM
 */
public class DateHelper {
	public static String DATE_FMT = "yyyy-MM-dd";
	public static String TIME_FMT = "yyyy-MM-dd HH:mm:ss";
	public static String SIMPLE_TIME_FMT = "yyyyMMddHHmmss";
	public static String SIMPLE_TIME = "HH:mm:ss";

	/**
	 * 方法描述：输入一个Date类型日期，返回一个字“yyyy-MM-dd hh:mm”格式符串日期
	 * 
	 * @author YongLiang.Wang
	 * @time Apr 8, 2011 3:05:32 PM
	 * @param date
	 *            ：传入的日期
	 * @return 返回一个字符日期
	 */
	public static String getFormatDateHHMM(Date date) {
		try {
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String result = formater.format(date);
			return result;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 方法描述：输入一个Date类型日期，返回一个字“yyyy-MM-dd hh:mm:ss”格式符串日期
	 * 
	 * @author YongLiang.Wang
	 * @time Apr 8, 2011 3:05:32 PM
	 * @param date
	 *            ：传入的日期
	 * @return 返回一个字符日期
	 */
	public static String getFormatDateHHMMSS(Date date) {
		try {
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String result = formater.format(date);
			return result;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 方法描述：输入一个Date类型日期，返回一个字“yyyy-MM-dd”格式符串日期
	 * 
	 * @author YongLiang.Wang
	 * @time Apr 8, 2011 3:05:32 PM
	 * @param date
	 *            ：传入的日期
	 * @return 返回一个字符日期
	 */
	public static String getFormatDate(Date date) {
		try {
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
			String result = formater.format(date);
			return result;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 方法描述：输入一个Date类型日期，返回一个字“yyyy-MM”格式符串日期
	 * 
	 * @author YongLiang.Wang
	 * @time Apr 8, 2011 3:05:32 PM
	 * @param date
	 *            ：传入的日期
	 * @return 返回一个字符日期
	 */
	public static String getFormatDateIsYM(Date date) {
		try {
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM");
			String result = formater.format(date);
			return result;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 方法描述：将yyyy-MM-dd格式的字符串日期转换成所需要的格式
	 * 
	 * @author YongLiang.Wang
	 * @time Apr 8, 2011 3:20:13 PM
	 * @param source
	 *            ：输入的日期，格式为：yyyy-MM-dd
	 * @param pattern
	 *            :要转换成的格式：如yyyy/MM/dd
	 * @return 返回转换后的格式日期
	 */
	public static String getFormatDate(String source, String pattern) {
		SimpleDateFormat formator = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = formator.parse(source);
			SimpleDateFormat targetformator = new SimpleDateFormat(pattern);
			return targetformator.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 方法描述：将Date类型日期转换成字符串类型日期
	 * 
	 * @author YongLiang.Wang
	 * @time Apr 8, 2011 3:21:52 PM
	 * @param source
	 *            ：需要转换的日期
	 * @param pattern
	 *            ：转换成的格式
	 * @return 返回字符串日期
	 */
	public static String getFormatDate(Date source, String pattern) {
		try {
			SimpleDateFormat targetformator = new SimpleDateFormat(pattern);
			return targetformator.format(source);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 方法描述：根据输入字符串日期的格式，转成成指定日期的格式
	 * 
	 * @author YongLiang.Wang
	 * @time Apr 8, 2011 3:27:14 PM
	 * @param source
	 *            ：转换的字符串日期
	 * @param sourcePatton
	 *            ：转换的字符串日期格式
	 * @param pattern
	 *            ：转换成的格式
	 * @return 返回字符串日期
	 */
	public static String getFormatDate(String source, String sourcePatton, String pattern) {
		SimpleDateFormat formator = new SimpleDateFormat(sourcePatton);
		try {
			Date date = formator.parse(source);
			SimpleDateFormat targetformator = new SimpleDateFormat(pattern);
			return targetformator.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 方法描述：将字符串类型日期转换成Date日期类型
	 * 
	 * @author YongLiang.Wang
	 * @time Apr 8, 2011 3:30:19 PM
	 * @param source
	 *            ：要转换的字符串日期
	 * @param sourcePattern
	 *            ：要转换的字符串日期格式
	 * @return 返回Date类型日期
	 */
	public static Date getDateFromStr(String source, String sourcePattern) {
		SimpleDateFormat formator = new SimpleDateFormat(sourcePattern);
		try {
			Date date = formator.parse(source);
			return date;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 方法描述：将格式为yyyy-MM-dd的字符串日期转换成Date类型日期
	 * 
	 * @author YongLiang.Wang
	 * @time Apr 8, 2011 3:17:51 PM
	 * @param source
	 *            ：输入的字符串日期，格式为”yyyy-MM-dd“
	 * @return Date类型日期
	 */
	public static Date getDateFromStr(String source) {
		SimpleDateFormat formator = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = formator.parse(source);
			return date;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 方法描述：将格式为yyyy-MM-dd HH:mm:ss的字符串日期转换成Date类型日期
	 * 
	 * @author Li.yang
	 * @time Apr 8, 2011 3:17:51 PM
	 * @param source
	 *            ：输入的字符串日期，格式为”yyyy-MM-dd HH:mm:ss“
	 * @return Date类型日期
	 */
	public static Date getDateFromStr2(String source) {
		SimpleDateFormat formator = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = formator.parse(source);
			return date;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 方法描述：获取本地长格式时间（14位）
	 * 
	 * @author YongLiang.Wang
	 * @time Apr 8, 2011 3:32:59 PM
	 * @return 本地长格式时间yyyyMMddHHmmss
	 */
	public static String getLocalLongDate14() {
		String dateString = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
		dateString = formatter.format(new java.util.Date());
		return dateString;
	}

	public static String getLocalLongDate8() {
		String dateString = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.US);
		dateString = formatter.format(new java.util.Date());
		return dateString;
	}

	/**
	 * 方法描述：获取本地长格式时间（6位）时分秒
	 * 
	 * @author YongLiang.Wang
	 * @time Apr 8, 2011 3:33:46 PM
	 * @return 本地长时间格式HHmmss
	 */
	public static String getLocalLongSecond6() {
		String secondString = "";
		SimpleDateFormat formatter = new SimpleDateFormat("HHmmss", Locale.US);
		secondString = formatter.format(new java.util.Date());
		return secondString;
	}

	/**
	 * 方法描述：获取本地长格式时间（9位）时分秒毫秒
	 * 
	 * @author YongLiang.Wang
	 * @time Apr 8, 2011 3:34:26 PM
	 * @return 本地长时间格式HHmmssSSS
	 */
	public static String getLocalLongMisSecond9() {
		String secondString = "";
		SimpleDateFormat formatter = new SimpleDateFormat("HHmmssSSS", Locale.US);
		secondString = formatter.format(new java.util.Date());
		return secondString;
	}

	/**
	 * 方法描述：获取本地当前时间
	 * 
	 * @author YongLiang.Wang
	 * @time Apr 8, 2011 3:35:02 PM
	 * @return 返回当前时间yyyy-MM-dd HH:mm:ss
	 */
	public static String getNowDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String curTime = sdf.format(new Date());
		return curTime;
	}

	/**
	 * 通过参数"yyyyMM"得到当前年月的最后一天
	 * 
	 * @param datetemp
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static Date getTheLastDateOfMouth(String datetemp) {
		Calendar today = Calendar.getInstance();
		// SimpleDateFormat sdfInput = new SimpleDateFormat(showformat);
		SimpleDateFormat chineseDateFormat1 = new SimpleDateFormat("yyyyMM");
		try {
			today.setTime(chineseDateFormat1.parse(datetemp));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		today.set(Calendar.DATE, today.getActualMaximum(today.DAY_OF_MONTH));
		today.set(Calendar.HOUR, 23);
		today.set(Calendar.MINUTE, 59);
		today.set(Calendar.SECOND, 59);
		return today.getTime();
	}

	/**
	 * 通过参数"yyyyMM"得到当前年月的第一天
	 * 
	 * @param datetemp
	 * @return
	 */
	public static Date getTheFirstDateOfMouth(String datetemp) {
		if (datetemp == null) {
			System.out.println("param is NULL--datetemp");
			return null;
		}
		Calendar today = Calendar.getInstance();
		// SimpleDateFormat sdfInput = new SimpleDateFormat(showformat);
		SimpleDateFormat chineseDateFormat1 = new SimpleDateFormat("yyyyMM");
		try {
			today.setTime(chineseDateFormat1.parse(datetemp));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		today.set(Calendar.DATE, 1);
		return today.getTime();
	}

	/***************************************************************************
	 * 方法描述：获得当日期的前一个月日期，如当前是2011-4则返回2011-3
	 * 
	 * @author Li.Yang
	 * @time Apr 26, 2011 5:43:42 PM
	 * @return
	 */
	public static String getFrontDate() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		if (month == 0) {
			month = 12;
			year = year - 1;
		}
		if (month < 10) {
			String datetime = year + "0" + month;
			return datetime;
		} else {
			String datetime = year + "" + month;
			return datetime;
		}
	}

	/***************************************************************************
	 * 方法描述：获得系统当前日期的前一天日期(yyyy-MM-dd)
	 * 
	 * @author Li.Yang
	 * @time May 16, 2011 2:41:54 PM
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static String getFrontDateTime() {

		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.DATE, -1);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);
		if (month == 0) {
			month = 12;
			year = year - 1;
		}
		String y = year + "";
		String m = month + "";
		String d = day + "";
		if (month < 10) {
			m = "0" + m;
		}
		if (day < 10) {
			d = "0" + d;
		}
		String datetime = y + "-" + m + "-" + d;
		return datetime;
	}

	/**
	 * desc:根据当前日期和增加天数得到新的日期 author:liujiawu createtime:May 26, 2011
	 * 
	 * @param date
	 * @param i
	 * @return
	 */
	public static Date getNewDate(Date date, int i) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, i);// 增加i天
		return cal.getTime();
	}

	/**
	 * 方法描述：将指定的时间字符串减去指定的天数
	 * 
	 * @author lixiang.xu
	 * @time May 26, 2011 11:45:53 AM
	 * @param date
	 *            日期
	 * @param days
	 *            加/减去的天数,加为正数,减为负数
	 * @return
	 */
	public static String addOrSubtractDays(String date, int days) {
		String result = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date dd = format.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dd);
			calendar.add(Calendar.DAY_OF_MONTH, days);
			result = format.format(calendar.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 方法描述：获取当天的日
	 * 
	 * @author YongLiang.Wang
	 * @time 2011-6-13 上午09:56:06
	 * @return
	 */
	public static Integer getTodayFormatDD() {
		try {
			SimpleDateFormat formater = new SimpleDateFormat("dd");
			String result = formater.format(new Date());
			Integer day = Integer.parseInt(result);
			return day;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 方法描述：获得指定日期的前一天
	 * 
	 * @author lixiang.xu
	 * @time Jun 24, 2011 10:06:21 AM
	 * @param specifiedDay
	 * @return
	 */
	public static String getSpecifiedDayBefore(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayBefore;
	}

	/**
	 * 方法描述：获得指定日期的后一天
	 * 
	 * @author lixiang.xu
	 * @time Jun 24, 2011 10:06:36 AM
	 * @param specifiedDay
	 * @return
	 */
	public static String getSpecifiedDayAfter(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayAfter;
	}

	/**
	 * 方法描述：取得两个日期之间的日期List,包含两端日期
	 * 
	 * @author lixiang.xu
	 * @time Jun 24, 2011 10:25:04 AM
	 * @param startDay
	 *            开始时间
	 * @param endDay
	 *            结束时间
	 */
	public static List<String> printDay(Calendar startDay, Calendar endDay) {
		List<String> list = new ArrayList<String>();

		// 给出的日期开始日比终了日大则不执行打印
		if (startDay.compareTo(endDay) >= 0) {
			return list;
		}
		list.add(new SimpleDateFormat("yyyy-MM-dd").format(startDay.getTime()));
		// 现在打印中的日期
		Calendar currentPrintDay = startDay;
		while (true) {
			// 日期加一
			currentPrintDay.add(Calendar.DATE, 1);
			// 日期加一后判断是否达到终了日，达到则终止打印
			if (currentPrintDay.compareTo(endDay) == 0) {
				break;
			}
			list.add(new SimpleDateFormat("yyyy-MM-dd").format(currentPrintDay.getTime()));
		}
		list.add(new SimpleDateFormat("yyyy-MM-dd").format(endDay.getTime()));
		return list;
	}

	/**
	 * 得到两个日期的间隔天数
	 * 
	 * @param begin
	 *            起始日期
	 * @param end
	 *            结束日期
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static int getDayInterval(Date begin, Date end) {
		return (int) ((end.getTime() - begin.getTime()) / 1000 / 60 / 60 / 24);
	}

	/**
	 * 
	 * 方法描述：从格式yyyyMM获得yyyy-MM格式
	 * 
	 * @author Li.Yang
	 * @time Oct 17, 2011 5:57:06 PM
	 * 
	 * @param datetemp
	 * @return
	 */
	public static String getYearMonth(String datetemp) {
		if (datetemp == null) {
			System.out.println("param is NULL--datetemp");
			return null;
		}
		String year = datetemp.substring(0, 4);
		String month = datetemp.substring(4, 6);
		String ymdate = year + "-" + month;
		return ymdate;

	}

	/***
	 * 
	 * 方法描述：获得系统当前日期前2个月的日期。如果当前日期为2011-6 则返回 2011-3
	 * 
	 * @author Li.Yang
	 * @time Jun 14, 2011 4:00:13 PM
	 * 
	 * @return
	 */
	public static String getDateBeforNowDateofTwo() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		// 获得当前月份
		int month = calendar.get(Calendar.MONTH) + 1;
		if (month >= 1 && month <= 3) {
			year = year - 1;
		}
		calendar.add(Calendar.MONTH, -3); // 得到前一个月
		month = calendar.get(Calendar.MONTH) + 1;
		if (month < 10) {
			String datetime = year + "-0" + month;
			return datetime;
		} else {
			String datetime = year + "-" + month;
			return datetime;
		}
	}

	/**
	 * 
	 * 方法描述：获取指定几个月前的月份
	 * 
	 * @author YongLiang.Wang
	 * @time 2011-12-15 下午05:52:09
	 * @param specifiedMonth
	 * @return
	 */
	public static String getSpecifiedMonthBefore(int specifiedMonth) {
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM");
		GregorianCalendar c1 = new GregorianCalendar();
		c1.add(GregorianCalendar.MONTH, -specifiedMonth);
		String dstr = dtFormat.format(c1.getTime());
		return dstr;
	}

	/**
	 * 方法描述：获取当前日期的前指定多少天的某一天
	 * 
	 * @author lixiang.xu
	 * @time Jun 24, 2011 10:06:21 AM
	 * @param specifiedDay
	 * @return
	 */
	public static String getSpecifiedDayBeforeOneday(int days) {
		Calendar c = Calendar.getInstance();
		Date date = new Date();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - days);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayBefore;
	}

	/**
	 * 
	 * 方法描述：获取当前时间minute分钟后的时间
	 * 
	 * @author David Wang
	 * @time 2012-8-16 上午11:03:54
	 * @param minute
	 * @return
	 */
	public static Date getSpecifiedMinute(int minute) {
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String str3 = dtFormat.format(now);
		System.out.println(str3);

		Calendar can = Calendar.getInstance();
		System.out.println(dtFormat.format(can.getTime()));
		can.add(Calendar.MINUTE, minute);

		System.out.println(dtFormat.format(can.getTime()));

		return can.getTime();

	}

	/**
	 * 
	 * 方法描述：获取两个时间间隔的分钟数
	 * 
	 * @author David Wang
	 * @time 2012-8-9 下午3:09:13
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return
	 */
	public static int getMinuteInterval(Date begin, Date end) {
		int time = (int) Math.ceil((end.getTime() - begin.getTime()) / 1000 / 60);
		if (time == 0)
			return 1;
		else
			return time;
	}

	/**
	 * 
	 * 方法描述：获取指定日期days天之后的日期
	 * 
	 * @author David Wang
	 * @time 2012-8-16 上午11:02:49
	 * @param date
	 * @param days
	 * @return
	 */
	public static String getSpecifiedDayAfterOneDay(Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + days);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayBefore;
	}

	/**
	 * 根据支付周期类型，获得相应的支付日期
	 * 
	 * @param date
	 *            不指定日期为当前日期
	 * @param cycle
	 *            支付周期0：月付1：季付2：半年付3：年付
	 * @return 返回yyyy-MM-dd格式日期
	 */
	public static String getPaymentDateByPayCycle(Date date, Integer cycle) {
		if (date == null)
			date = new Date();
		String result = "";
		int day = Integer.parseInt(new SimpleDateFormat("dd").format(date));// 取到天值
		int payCycle = cycle;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		switch (payCycle) {
		case 0: // 月付
			date = addMONTH(date, 1);
			result = getFormatDateIsYM(date);
			break;
		case 1:// 季付
			date = addMONTH(date, 3);
			result = getFormatDateIsYM(date);
			break;
		case 2:// 半年付
			date = addMONTH(date, 6);
			result = getFormatDateIsYM(date);
			break;
		case 3:// 年付
			date = addMONTH(date, 12);
			result = getFormatDateIsYM(date);
			break;
		default:
			date = addMONTH(date, 1);
			result = getFormatDateIsYM(date);
		}
		int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));// 取到年份值
		int month = Integer.parseInt(new SimpleDateFormat("MM").format(date));// 取到年份值
		if (day > 28) {
			if (month == 2) {
				if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
					day = 29;
				} else
					day = 28;
			} else if ((month == 4 || month == 6 || month == 9 || month == 11) && day == 31) {
				day = 30;
			}
		}
		return result + "-" + day;
	}

	/**
	 * 
	 * 方法描述：获取指定日期月份的第一天日期
	 * 
	 * @author David
	 * @time 2012-11-27 下午4:41:48
	 * 
	 * @param day
	 * @return
	 */
	public static String getMonthFirstDay(Date day) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(day);
		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(day);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		String day_first = df.format(gcLast.getTime());
		return day_first;
	}

	/**
	 * 
	 * 方法描述：时间格式截取
	 * 
	 * @author 何海强
	 * @time Nov 19, 2012 2:49:35 PM
	 * 
	 * @param args
	 * @return yyyy-MM
	 */
	/**
	 * 方法描述：
	 * 
	 * @author David
	 * @time 2013-2-16 下午4:43:50
	 * 
	 * @param month
	 * @return
	 */
	public static String subDate(int month) {
		Date bb = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(bb);
		calendar.add(Calendar.MONTH, -month);
		Date date = calendar.getTime();
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM");
		String ddte = dfs.format(date);
		return ddte;
	}

	/**
	 * 
	 * 方法描述：增加月份
	 * 
	 * @author Administrator
	 * @time Mar 13, 2013 1:06:07 PM
	 * 
	 * @param sourceDate
	 * @param n
	 * @return
	 */
	public static Date addMONTH(Date sourceDate, int n) {
		try {
			Calendar cd = Calendar.getInstance();
			cd.setTime(sourceDate);
			cd.add(Calendar.MONDAY, n);
			return cd.getTime();

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * 方法描述：增加减少分钟
	 * 
	 * @author Administrator
	 * @time Mar 13, 2013 1:06:07 PM
	 * 
	 * @param sourceDate
	 * @param n
	 * @return
	 */
	public static Date addMINUTE(Date sourceDate, int n) {
		try {
			Calendar cd = Calendar.getInstance();
			cd.setTime(sourceDate);
			cd.add(Calendar.MINUTE, n);
			return cd.getTime();

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * 方法描述：时间戳转换成yyyy-MM-dd HH:mm:ss日期
	 * 
	 * @author David
	 * @time 2013-4-15 下午3:10:59
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String timestampToDate(String timestamp) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long time = new Long(timestamp);
		String d = format.format(time);
		return d;
	}

	public static Date timestampToDate(Long timestamp) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String d = format.format(timestamp);
		try {
			Date date = format.parse(d);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 判断当前日期是星期几<br>
	 * <br>
	 * 
	 * @param pTime
	 *            修要判断的时间<br>
	 * @return dayForWeek 判断结果<br>
	 * @Exception 发生异常<br>
	 */
	public static int dayForWeek(String pTime){
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(format.parse(pTime));
			int dayForWeek = 0;
			if (c.get(Calendar.DAY_OF_WEEK) == 1) {
				dayForWeek = 7;
			} else {
				dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
			}
			return dayForWeek;
		} catch (ParseException e) {
			e.printStackTrace();
			return 9;
		}
	}
	public static void main(String[] args) {
		int w = DateHelper.dayForWeek("2013-09-30");
		System.out.print(w);
	}

}
