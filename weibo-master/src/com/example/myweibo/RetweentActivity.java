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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myweibo.domain.WeiboBean;
import com.example.myweibo.domain.WeiboBean.StatusesBean;
import com.example.myweibo.utils.AccessTokenKeeper;
import com.example.myweibo.utils.Contacts;
import com.example.myweibo.widget.loopj.android.image.SmartImageView;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.CommentsAPI;
import com.sina.weibo.sdk.openapi.legacy.StatusesAPI;

public class RetweentActivity extends Activity implements OnClickListener {
	private Oauth2AccessToken mAccessToken;
	private StatusesAPI mStatusesAPI;
	private CommentsAPI mCommentsAPI;
	private TextView tv_cancle;
	private Button bt_send;
	private LinearLayout weiboauth_container;
	private SmartImageView img;
	private TextView tv_name;
	private TextView tv_content;
	private CheckBox cb_both;
	private EditText et_content;
	private int BOTH;
	private long weiboId;
	private WeiboBean.StatusesBean bean;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.retweent_layout);
		initView();
		initData();
		listener();
	}

	private void initData() {
		mAccessToken = AccessTokenKeeper.readAccessToken(RetweentActivity.this);
		mStatusesAPI = new StatusesAPI(RetweentActivity.this, Contacts.APPKEY,
				mAccessToken);
		mCommentsAPI = MainActivity.mainActivity.mCommentsAPI;
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		bean = (StatusesBean) bundle.getSerializable("retweent");
		System.out.println(bean.getId());
		// ////////////////////////////////////////////////
		weiboId = bean.getId();
		if (bean.getBmiddle_pic() != null) {
			img.setImageUrl(bean.getUser().getProfile_image_url());
		}
		tv_name.setText(bean.getUser().getName());
		tv_content.setText(bean.getText());
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
				// TODO Auto-generated method stub

			}

			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}
		});
		cb_both.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				BOTH = isChecked ? 3 : 0;
			}
		});

		bt_send.setOnClickListener(this);
		tv_cancle.setOnClickListener(this);
	}

	private void initView() {
		tv_cancle = (TextView) findViewById(R.id.reweent_tv_cancal);
		tv_content = (TextView) findViewById(R.id.reweent_content);
		tv_name = (TextView) findViewById(R.id.reweent_auth_name);
		bt_send = (Button) findViewById(R.id.reweent_bt_send);
		img = (SmartImageView) findViewById(R.id.reweent_weiboimg);
		weiboauth_container = (LinearLayout) findViewById(R.id.reweent_auth_container);
		cb_both = (CheckBox) findViewById(R.id.reweent_cb_both);
		et_content = (EditText) findViewById(R.id.reweent_et_content);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.reweent_bt_send:
			String status = et_content.getText().toString().trim();
			if (mStatusesAPI != null) {
				mStatusesAPI.repost(weiboId, status, BOTH,
						new RequestListener() {

							public void onWeiboException(WeiboException arg0) {
								finish();
							}

							public void onComplete(String arg0) {
								Toast.makeText(RetweentActivity.this, "转发成功", 0)
										.show();
								finish();
							}
						});
			} else {
				Toast.makeText(RetweentActivity.this, "错误", 0).show();
				finish();
			}
			break;
		case R.id.reweent_tv_cancal:
			finish();
			break;

		default:
			break;
		}
	}
}
