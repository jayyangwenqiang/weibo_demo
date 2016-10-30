package com.example.myweibo.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.myweibo.R;
import com.example.myweibo.SendPicWeiboActivity;
import com.example.myweibo.SendTextWeiboActivity;

public class MyPopupWindow extends PopupWindow {

	private Context mContext;
	private View mView;
	private ImageView weibo_tv, weibo_pic, weibo_close;

	public MyPopupWindow(final Context mContext, View.OnClickListener onClickListener) {
		this.mContext=mContext;
		mView = LayoutInflater.from(mContext).inflate(
				R.layout.send_weibo_pupwindow, null);
		weibo_tv = (ImageView) mView.findViewById(R.id.sendweibo_text);
		weibo_pic = (ImageView) mView.findViewById(R.id.sendweibo_pic);
		weibo_close = (ImageView) mView.findViewById(R.id.sendweibo_close);
		weibo_tv.setOnClickListener(onClickListener);
		weibo_pic.setOnClickListener(onClickListener);
		weibo_close.setOnClickListener(onClickListener);
		this.setOutsideTouchable(true);// 外部可点击
		this.mView.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				int height = mView.findViewById(R.id.pop_layout).getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < height) {
						dismiss();
					}
				}
				return true;
			}
		});
		// 设置内容view
		this.setContentView(this.mView);
		// 设置宽高

		this.setHeight(600);
		this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
		// 获取焦点
		this.setFocusable(true);
		weibo_tv.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AnimatorSet ast = new AnimatorSet();
				ObjectAnimator oxt = ObjectAnimator
						.ofFloat(weibo_tv,
								"scaleX", 1.0f, 1.1f,
								1.2f, 1.1f, 1.0f);
				ObjectAnimator oyt = ObjectAnimator
						.ofFloat(weibo_tv,
								"scaleY", 1.0f, 1.1f,
								1.2f, 1.1f, 1.0f);
				ast.playTogether(oxt, oyt);
				ast.setDuration(200);
				ast.start();
				Intent intentt = new Intent(mContext,
						SendTextWeiboActivity.class);
				((Activity) mContext).overridePendingTransition(
						R.anim.pop_exit,
						R.anim.pop_enter);
				mContext.startActivity(intentt);
				((Activity) mContext).finish();
			}
		});
		weibo_pic.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AnimatorSet asp = new AnimatorSet();
				ObjectAnimator oxp = ObjectAnimator
						.ofFloat(weibo_pic,
								"scaleX",1.0f, 1.1f,
								1.2f, 1.1f, 1.0f);
				ObjectAnimator oyp = ObjectAnimator
						.ofFloat(weibo_pic,
								"scaleY", 1.0f, 1.1f,
								1.2f, 1.1f, 1.0f);
				asp.playTogether(oxp, oyp);
				asp.setDuration(200);
				asp.start();
				Intent intentp = new Intent(
						mContext,
						SendPicWeiboActivity.class);
				((Activity) mContext).overridePendingTransition(
						R.anim.pop_exit,
						R.anim.pop_enter);
				mContext.startActivity(intentp);
				
				((Activity) mContext).finish();
				
			}
		});
		// 背景颜色
		ColorDrawable cd = new ColorDrawable(Color.WHITE);
		this.setBackgroundDrawable(cd);
		// 设置动画style
		this.setAnimationStyle(R.style.MyPopupWindow);
	}

}
