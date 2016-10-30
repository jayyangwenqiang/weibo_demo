package com.example.myweibo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUserInfoCreateAt {
	public static String getCreateAt(String time) {

		if (time != null) {
			try {
				long date = Date.parse(time);
				SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
				Date d = new Date(date);
				String timeAfterFormat = format.format(d);
				return timeAfterFormat;
			} catch (Exception e) {
				return "未知";
			}
		}
		return "未知";
	}
}
