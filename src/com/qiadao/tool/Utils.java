package com.qiadao.tool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @Author SunnyCoffee
 * @Date 2014-1-28
 * @version 1.0
 * @Desc 工具类
 */

public class Utils {

	public static String getCurrentTime(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		String currentTime = sdf.format(date);
		return currentTime;
	}

	public static String getCurrentTime() {
		return getCurrentTime("yyyy-MM-dd  HH:mm:ss");
	}
	
	public static String timeAccount(long timepoint) {
		long lefttime = System.currentTimeMillis() % (24 * 60 * 60 * 1000);
		lefttime = lefttime / 1000 / 60 / 60;
		long betweenTime = System.currentTimeMillis() - timepoint;
		int minute = (int) betweenTime / 1000 / 60;
		String t = null;
		if (minute <= 1) {
			t = "刚刚";
			return t;
		} else if (minute < 60) {
			t = String.valueOf(minute) + "分钟以前";
			return t;
		} else if (minute < lefttime && minute > 60) {
			t = String.valueOf(minute / 60) + "小时前";
			return t;
		} else if (minute > lefttime && minute < 24 * 60) {
			t = "昨天";
			return t;
		} else {
			SimpleDateFormat sf4 = new SimpleDateFormat("MM月dd日");
			String newsDate2 = sf4.format(new Date(timepoint));
			t = newsDate2;
			return t;
		}
	}
}
