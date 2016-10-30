package com.example.myweibo;

import java.util.Timer;
import java.util.TimerTask;

import com.example.myweibo.utils.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class WelcomeUI extends Activity implements OnClickListener {
	private Button bt_skip;
	Timer mTimer;
	TimerTask mTimerTask;
	Oauth2AccessToken mAccessToken;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int tvSecond = msg.arg1;
			bt_skip.setText("跳过(" + (4 - tvSecond) + "s)");
		};
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome_layout);
		bt_skip = (Button) findViewById(R.id.bt_skip);
		final Intent mIntent = new Intent(this, LoginUI.class);
		mAccessToken = AccessTokenKeeper.readAccessToken(WelcomeUI.this);
		mTimer = new Timer();
		mTimerTask = new TimerTask() {
			@Override
			public void run() {
				for (int i = 0; i < 3; i++) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Message msg = Message.obtain();
					msg.arg1 = i + 1;
					mHandler.sendMessage(msg);
				}

				if (mTimer != null) {
					if (!mAccessToken.isSessionValid()) {
						startActivity(mIntent);
						mTimer.cancel();
						mTimerTask.cancel();
						finish();
					} else {
						Intent intent = new Intent(WelcomeUI.this,
								MainActivity.class);
						startActivity(intent);
						mTimer.cancel();
						mTimerTask.cancel();
						finish();

					}
				}
			}
		};
		mTimer.schedule(mTimerTask, 0, 1000);
		bt_skip.setOnClickListener(this);

	}

	public void onClick(View v) {
		if (!mAccessToken.isSessionValid()) {
			Intent mIntent = new Intent(this, LoginUI.class);
			mTimer.cancel();
			mTimerTask.cancel();
			mTimer = null;
			mTimerTask = null;
			startActivity(mIntent);
			finish();
		} else {
			Intent intent = new Intent(WelcomeUI.this, MainActivity.class);
			startActivity(intent);
			mTimer.cancel();
			mTimerTask.cancel();
			mTimer = null;
			mTimerTask = null;
			finish();
		}
	}

	protected void onDestroy() {
		super.onDestroy();
		if (mTimer != null || mTimerTask != null) {
			mTimer.cancel();
			mTimerTask.cancel();
			mTimer = null;
			mTimerTask = null;
		}
	}
}
