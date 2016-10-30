package com.example.myweibo;

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
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

public class CommentActivity extends Activity implements OnClickListener {
	private TextView tv_cancle;
	private Button bt_send;
	private EditText et_content;
	private CheckBox cb_retweent;
	private long weiboid;
	boolean comment_ori;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.comment_layout);
		Intent intent = getIntent();
		weiboid = intent.getLongExtra("weiboid", 0);
		initView();
		listener();
	}

	private void listener() {
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

			}

			public void afterTextChanged(Editable s) {
			}
		});
		tv_cancle.setOnClickListener(this);
		bt_send.setOnClickListener(this);
		cb_retweent.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				comment_ori = isChecked ? true : false;
			}
		});
	}

	private void initView() {
		tv_cancle = (TextView) findViewById(R.id.comment_tv_cancal);
		bt_send = (Button) findViewById(R.id.comment_bt_send);
		et_content = (EditText) findViewById(R.id.comment_et_content);
		cb_retweent = (CheckBox) findViewById(R.id.comment_cb_both);
		bt_send.setEnabled(false);
	}

	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.comment_tv_cancal:
			finish();
			break;
		case R.id.comment_bt_send:
			String content = et_content.getText().toString().trim();
			MainActivity.mainActivity.mCommentsAPI.create(content, weiboid,
					comment_ori, new RequestListener() {
						
						public void onWeiboException(WeiboException arg0) {
							// TODO Auto-generated method stub
							Toast.makeText(CommentActivity.this,
									arg0.toString(), 0).show();
							finish();
						}

						
						public void onComplete(String arg0) {
							// TODO Auto-generated method stub
							Toast.makeText(CommentActivity.this, "评论成功", 0)
									.show();
							finish();
						}
					});
			break;
		default:
			break;
		}

	}

}
