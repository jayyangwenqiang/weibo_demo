package com.example.myweibo.utils;

public class FormatSource {
	public static String getSource(String source) {
		if (source != null) {
			String[] str1 = source.split(">");
			String str2 = str1[1];
			String[] str3 = str2.split("<");
			return str3[0];
		} else {
			return "";
		}
	}
}
