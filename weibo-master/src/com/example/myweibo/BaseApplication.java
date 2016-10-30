package com.example.myweibo;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {
	public static Context mContext;

	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mContext = getApplicationContext();
	}

	public static Context getApplication() {
		return mContext;
	}
}
