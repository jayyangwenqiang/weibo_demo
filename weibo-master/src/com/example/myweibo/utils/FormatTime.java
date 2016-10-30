package com.example.myweibo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatTime {
	public static String getTime(String time) {
		
		
		SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
		int dayMmin = 24 * 60;
		
		
		
		long date = Date.parse(time);
		Date d = new Date(date);
		long currentTime = System.currentTimeMillis();
		long mistiming = currentTime - date;
		int min = (int) (mistiming / (1000 * 60));
		if (dayMmin < min) {
			String result = format.format(d);
			return result;
		} else if (60 <= min && min <= dayMmin) {

			if (24 * 60 <= min) {

			}
			if (23 * 60 <= min) {
				return "23小时前";
			}
			if (22 * 60 <= min && min < 23 * 60) {
				return "22小时前";
			}
			if (21 * 60 <= min && min < 22 * 60) {
				return "21小时前";
			}
			if (20 * 60 <= min && min < 21 * 60) {
				return "20小时前";
			}
			if (19 * 60 <= min && min < 20 * 60) {
				return "19小时前";
			}
			if (18 * 60 <= min && min < 19 * 60) {
				return "18小时前";
			}
			if (17 * 60 <= min && min < 18 * 60) {
				return "17小时前";
			}
			if (16 * 60 <= min && min < 17 * 60) {
				return "16小时前";
			}
			if (15 * 60 <= min && min < 16 * 60) {
				return "15小时前";
			}
			if (14 * 60 <= min && min < 15 * 60) {
				return "14小时前";
			}
			if (13 * 60 <= min && min < 14 * 60) {
				return "13小时前";
			}
			if (12 * 60 <= min && min < 13 * 60) {
				return "12小时前";
			}
			if (11 * 60 <= min && min < 12 * 60) {
				return "11小时前";
			}
			if (10 * 60 <= min && min < 11 * 60) {
				return "10小时前";
			}
			if (9 * 60 <= min && min < 10 * 60) {
				return "9小时前";
			}
			if (8 * 60 <= min && min < 9 * 60) {
				return "8小时前";
			}
			if (7 * 60 <= min && min < 8 * 60) {
				return "7小时前";
			}
			if (6 * 60 <= min && min < 7 * 60) {
				return "6小时前";
			}

			if (5 * 60 <= min && min < 6 * 60) {
				return "5小时前";
			}
			if (4 * 60 <= min && min < 5 * 60) {
				return "4小时前";
			}
			if (3 * 60 <= min && min < 4 * 60) {
				return "3小时前";
			}
			if (2 * 60 <= min && min < 3 * 60) {
				return "2小时前";
			}
			if (60 <=min && min < 2 * 60) {
				return "1小时前";
			}

		} else {
			for (int i = 0; i < 60; i++) {
				if (min <= 1) {
					return "刚刚";
				} else if (min == i) {
					return i + "分钟前";
				}

			}
		}

		return date + "";
	}
}
