package com.hym.fxwebview.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Neo on 2019/7/13.
 * Description :
 */
public class DateUtils
{
	private static final String pattern = "yyyy-MM-dd HH:mm:ss";
	
	public static long getCurTimeLong(){
		long time=System.currentTimeMillis();
		return time;
	}
	
	public static String getCurDateSecond(){
		SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
		return sDateFormat.format(new Date());
	}
	
	public static long getStringToDate(String dateString) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		Date date = new Date();
		try{
			date = dateFormat.parse(dateString);
		} catch( ParseException e) {
			e.printStackTrace();
		}
		return date.getTime();
	}
	
	public static String getDistance(String time)
	{
		long now = getCurTimeLong();
		long old = getStringToDate(time);
		long distince = now - old;
		return distince+"";
	}
	
	public static String getDistanceSecond(String time)
	{
		long now = getCurTimeLong();
		long old = getStringToDate(time);
		long distince = (now - old) / 1000;
		long min = now - old - distince*1000;
		return distince+"秒"+min+"毫秒";
	}
}
