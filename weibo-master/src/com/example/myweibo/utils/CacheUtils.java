package com.example.myweibo.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class CacheUtils {

	public static String getString(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences("configdata",
				Context.MODE_PRIVATE);
		String result = sp.getString(key, null);
		return result;
	}

	public static void setString(Context context, String key, String value) {
		SharedPreferences sp = context.getSharedPreferences("configdata",
				Context.MODE_PRIVATE);
		sp.edit().putString(key, value).commit();
	}

	public static long getLong(Context context, String key) {

		SharedPreferences sp = context.getSharedPreferences("configdata",
				Context.MODE_PRIVATE);

		return sp.getLong(key, 0);
	}

	public static void setLong(Context context, String key, long value) {
		SharedPreferences sp = context.getSharedPreferences("configdata",
				Context.MODE_PRIVATE);
		sp.edit().putLong(key, value).commit();
	}
}
