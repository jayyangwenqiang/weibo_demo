package com.example.myweibo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class MyRelativeLayout extends RelativeLayout {
	
	
	
	public MyRelativeLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
    public MyRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
     * 拦截消费触屏事件
     */
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return true;
	}

}
