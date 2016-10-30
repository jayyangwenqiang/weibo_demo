package com.example.myweibo;

import com.example.myweibo.utils.AccessTokenKeeper;
import com.example.myweibo.utils.Contacts;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.CommentsAPI;
import com.sina.weibo.sdk.openapi.models.Comment;

import android.R.integer;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ReplyActivity extends Activity implements OnClickListener {
	private Oauth2AccessToken mAccessToken;
	private CommentsAPI mCommentsAPI;
	private TextView tv_cancle;
	private Button bt_send;
	private EditText et_content;
	private CheckBox cb_reweent;
	private long weiboid;
	private String commentid;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.reply_layout);
		getIntentData();
		initView();
		initData();
	}

	private void getIntentData() {
		Intent intent = getIntent();
		weiboid = intent.getLongExtra("weiboid", 0);
		commentid = intent.getStringExtra("commentid");

	}

	private void initData() {
		bt_send.setEnabled(false);
		mAccessToken = AccessTokenKeeper.readAccessToken(ReplyActivity.this);
		mCommentsAPI = new CommentsAPI(ReplyActivity.this, Contacts.APPKEY,
				mAccessToken);
		et_content.addTextChangedListener(new TextWatcher() {
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

	private void initView() {
		tv_cancle = (TextView) findViewById(R.id.reply_tv_cancal);
		bt_send = (Button) findViewById(R.id.reply_bt_send);
		et_content = (EditText) findViewById(R.id.reply_et_content);
		cb_reweent = (CheckBox) findViewById(R.id.reply_cb_both);
		tv_cancle.setOnClickListener(this);
		bt_send.setOnClickListener(this);
	}

	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.reply_tv_cancal:
			finish();
			break;
		case R.id.reply_bt_send:
			String content = et_content.getText().toString().trim();
			mCommentsAPI.reply(Long.valueOf(commentid), weiboid, content, true,
					true, new RequestListener() {
						
						public void onWeiboException(WeiboException arg0) {
							Toast.makeText(ReplyActivity.this, arg0.toString(),
									0).show();
							finish();
						}

						
						public void onComplete(String arg0) {
							Toast.makeText(ReplyActivity.this, "评论成功", 0)
									.show();
							finish();
						}
					});
		default:
			break;
		}

	}
}
