package com.example.myweibo;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.legacy.StatusesAPI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SendTextWeiboActivity extends Activity implements OnClickListener {
	private TextView tv_cancle;
	private Button bt_send;
	private EditText weibo_content;
	private StatusesAPI mStatusesAPI;
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.send_weibo_text);
		initView();
		initData();
	}

	private void initData() {
		mStatusesAPI = MainActivity.mainActivity.mStatusesAPI;
		tv_cancle.setOnClickListener(this);
		bt_send.setOnClickListener(this);

	}

	private void initView() {
		tv_cancle = (TextView) findViewById(R.id.sendweibo_tv_cancal);
		bt_send = (Button) findViewById(R.id.sendweio_bt_send);
		weibo_content = (EditText) findViewById(R.id.sendweibo_et_content);
		weibo_content.addTextChangedListener(new TextWatcher() {
			
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (TextUtils.isEmpty(s)) {
					bt_send.setEnabled(false);
					bt_send.setBackgroundResource(R.drawable.sendbt_enable);
				} else {
					bt_send.setEnabled(true);
					bt_send.setBackgroundResource(R.drawable.common_button_orange_disable);
				}
			}

			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

	}

	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sendweibo_tv_cancal:
			Intent intent = new Intent(SendTextWeiboActivity.this,
					MainActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.sendweibo_enter, R.anim.pop_exit);
			SendTextWeiboActivity.this.finish();
			break;
		case R.id.sendweio_bt_send:
			String content = weibo_content.getText().toString().trim();
			if (!TextUtils.isEmpty(content)) {
				mStatusesAPI.update(content, "0.0", "0.0",
						new RequestListener() {
							public void onComplete(String arg0) {
								Intent intent = new Intent(
										SendTextWeiboActivity.this,
										MainActivity.class);
								startActivity(intent);
								overridePendingTransition(R.anim.pop_enter,
										R.anim.pop_exit);
								SendTextWeiboActivity.this.finish();
							}

							public void onWeiboException(WeiboException arg0) {
								Toast.makeText(SendTextWeiboActivity.this,
										arg0.toString(), 0).show();
							}
						});
			}
			break;
		default:
			break;
		}

	}
}
