package com.example.myweibo;

import com.example.myweibo.utils.AccessTokenKeeper;
import com.example.myweibo.utils.Contacts;
import com.example.myweibo.utils.FormatUserInfoCreateAt;
import com.example.myweibo.widget.CircleImageView;
import com.google.gson.Gson;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.legacy.FriendshipsAPI;
import com.sina.weibo.sdk.openapi.models.User;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserInfoActivity extends Activity implements OnClickListener,
		OnCheckedChangeListener {
	private UsersAPI usersAPI;
	private Oauth2AccessToken mAccessToken;
	private FriendshipsAPI api;
	Gson gson;
	User user;
	private ImageView img_back;
	private CircleImageView header;
	private TextView name;
	private ImageView gender;
	private ImageView isVip;
	private TextView followCount;
	private TextView fans;
	private TextView desc;
	private TextView nicknema;
	private TextView s;
	private TextView address;
	private TextView desc_j;
	private TextView createat;
	private long uid;
	private CheckBox add;
	private String uName;
	private SharedPreferences sp;
	Editor editor;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.u_layout);
		Intent intent = getIntent();
		uid = intent.getLongExtra("uid", 0);
		uName = intent.getStringExtra("uname");
		sp = getSharedPreferences("add", MODE_PRIVATE);
		editor = sp.edit();
		initView();
		initData();
		read();

	}

	private void read() {
		int isChecked = sp.getInt("isChecked", 1);
		add.setChecked(isChecked == 0 ? true : false);
		String value = sp.getString("value", "加关注");
		add.setText(add.isChecked() ? "已关注" : "加关注");
	}

	private void initData() {
		gson = new Gson();
		mAccessToken = AccessTokenKeeper.readAccessToken(UserInfoActivity.this);
		usersAPI = new UsersAPI(UserInfoActivity.this, Contacts.APPKEY,
				mAccessToken);
		api = new FriendshipsAPI(UserInfoActivity.this, Contacts.APPKEY,
				mAccessToken);
		usersAPI.show(uid, new RequestListener() {
			
			public void onWeiboException(WeiboException arg0) {
				// TODO Auto-generated method stub
			}

			public void onComplete(String arg0) {
				user = User.parse(arg0);
				header.setImageUrl(user.profile_image_url);
				name.setText(user.name);
				String sex = user.gender;
				if ("f".equals(sex)) {
					gender.setImageResource(R.drawable.list_female);
				} else if ("m".equals(sex)) {
					gender.setImageResource(R.drawable.list_male);
				} else {
					gender.setImageResource(R.drawable.market_sng_main_download_btn);
				}
				boolean verified = user.verified;
				isVip.setImageResource(verified ? R.drawable.v
						: R.drawable.v_blue);
				followCount.setText("关注" + " " + user.friends_count);
				fans.setText("粉丝" + " " + user.followers_count);
				desc.setText(user.name);
				// nicknema.setText(user.name);
				if ("f".equals(sex)) {
					s.setText("女");
				} else if ("m".equals(sex)) {
					s.setText("男");
				} else {
					s.setText("未知");
				}
				address.setText(user.location);
				desc_j.setText(TextUtils.isEmpty(user.description) ? "暂无简介"
						: "简介：" + user.description);
				createat.setText(FormatUserInfoCreateAt.getCreateAt(user.created_at));
			}
		});
	}

	private void initView() {
		header = (CircleImageView) findViewById(R.id.u_usercenter_header);
		name = (TextView) findViewById(R.id.u_usercenter_user_name);
		gender = (ImageView) findViewById(R.id.u_userinfo_gender);
		isVip = (ImageView) findViewById(R.id.u_userinfo_isvip);
		followCount = (TextView) findViewById(R.id.u_userinfo_guanzhu);
		fans = (TextView) findViewById(R.id.u_userinfo_fans);
		desc = (TextView) findViewById(R.id.u_userinfo_content);
		s = (TextView) findViewById(R.id.u_userinfo_desc_gender);
		address = (TextView) findViewById(R.id.u_userinfo_desc_address);
		desc_j = (TextView) findViewById(R.id.u_userinfo_jianjie_);
		createat = (TextView) findViewById(R.id.u_userinfo_createat);
		img_back = (ImageView) findViewById(R.id.u_back);
		add = (CheckBox) findViewById(R.id.u_add);
		img_back.setOnClickListener(this);
		add.setOnCheckedChangeListener(this);

	}

	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.u_back:
			finish();
			break;
		case R.id.u_add:

			break;
		default:
			break;
		}
	}

	
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.u_add:
			
			if (isChecked) {
				api.create(uid, uName, new RequestListener() {
					
					public void onWeiboException(WeiboException arg0) {
						Toast.makeText(UserInfoActivity.this, "onCheckedChanged"+arg0.toString(), 0).show();
					}

					
					public void onComplete(String arg0) {
						add.setText("已关注");
						editor.putInt("isChecked", 0);
						editor.putString("value", "已关注");
					}
				});
			} else {
				api.destroy(uid, uName, new RequestListener() {
					public void onWeiboException(WeiboException arg0) {
						Toast.makeText(UserInfoActivity.this, "onCheckedChanged"+arg0.toString(), 0).show();
					}
					public void onComplete(String arg0) {
						add.setText("加关注");
						editor.putInt("isChecked", 1);
						editor.putString("value", "加关注");
					}
				});
			}
			editor.commit();
			Toast.makeText(UserInfoActivity.this, "onCheckedChanged", 0).show();
			break;

		default:
			break;
		}
	}
}
