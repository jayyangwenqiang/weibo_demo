package com.example.myweibo.fragment;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myweibo.LoginUI;
import com.example.myweibo.R;
import com.example.myweibo.utils.AccessTokenKeeper;
import com.example.myweibo.utils.CacheUtils;
import com.example.myweibo.utils.Contacts;
import com.example.myweibo.utils.FormatUserInfoCreateAt;
import com.example.myweibo.widget.CircleImageView;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.LogoutAPI;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.models.User;

public class UserFragment extends BaseFragment implements OnClickListener {
	private String USERFRAGMENT_KEY = "UserFragment";
	private String USERFRAGMENT_TIME_KEY = USERFRAGMENT_KEY + "date";
	private View view;
	private UsersAPI usersAPI;
	private Oauth2AccessToken mAccessToken;
	private LogoutAPI logout;
	private User user;
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
	private TextView tv_setting;
	private String json;
	private long currentTime;

	@Override
	public View initView() {
		view = View.inflate(mActivity, R.layout.usercenter, null);
		initSelfView();
		initData();
		listenner();
		return view;
	}

	private void processData() {
		user = User.parse(json);
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
		isVip.setImageResource(verified ? R.drawable.v : R.drawable.v_blue);
		followCount.setText("关注" + " " + user.friends_count);
		fans.setText("粉丝" + " " + user.followers_count);
		desc.setText(user.name);
		if ("f".equals(sex)) {
			s.setText("女");
		} else if ("m".equals(sex)) {
			s.setText("男");
		} else {
			s.setText("未知");
		}
		address.setText(user.location);
		desc_j.setText(TextUtils.isEmpty(user.description) ? "暂无简介" : "简介："
				+ user.description);
		createat.setText(FormatUserInfoCreateAt.getCreateAt(user.created_at));

	}

	private void listenner() {
		tv_setting.setOnClickListener(this);
	}

	private void initData() {
		mAccessToken = AccessTokenKeeper.readAccessToken(mActivity);
		usersAPI = new UsersAPI(mActivity, Contacts.APPKEY, mAccessToken);
		logout = new LogoutAPI(mActivity, Contacts.APPKEY, mAccessToken);
		currentTime = CacheUtils.getLong(mActivity, USERFRAGMENT_TIME_KEY);
		json = CacheUtils.getString(mActivity, USERFRAGMENT_KEY);
		if (json != null) {
			processData();
		}
		if (currentTime != 0 && System.currentTimeMillis() - currentTime < 60) {
			return;
		} else {
			usersAPI.show(Long.valueOf(mAccessToken.getUid()),
					new RequestListener() {
						public void onWeiboException(WeiboException arg0) {
							// TODO Auto-generated method stub

						}

						public void onComplete(String arg0) {
							CacheUtils.setString(mActivity, USERFRAGMENT_KEY,
									arg0);
							CacheUtils.setLong(mActivity,
									USERFRAGMENT_TIME_KEY,System.currentTimeMillis());
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
							followCount
									.setText("关注" + " " + user.friends_count);
							fans.setText("粉丝" + " " + user.followers_count);
							desc.setText(user.name);
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
							createat.setText(FormatUserInfoCreateAt
									.getCreateAt(user.created_at));
						}
					});
		}

	}

	private void initSelfView() {
		header = (CircleImageView) view.findViewById(R.id.usercenter_header);
		name = (TextView) view.findViewById(R.id.usercenter_user_name);
		gender = (ImageView) view.findViewById(R.id.userinfo_gender);
		isVip = (ImageView) view.findViewById(R.id.userinfo_isvip);
		followCount = (TextView) view.findViewById(R.id.userinfo_guanzhu);
		fans = (TextView) view.findViewById(R.id.userinfo_fans);
		desc = (TextView) view.findViewById(R.id.userinfo_content);
		s = (TextView) view.findViewById(R.id.userinfo_desc_gender);
		address = (TextView) view.findViewById(R.id.userinfo_desc_address);
		desc_j = (TextView) view.findViewById(R.id.userinfo_jianjie_);
		createat = (TextView) view.findViewById(R.id.userinfo_createat);
		tv_setting = (TextView) view.findViewById(R.id.user_center_setting);

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.user_center_setting:
			AlertDialog.Builder builder = new Builder(mActivity);
			builder.setMessage("确定退出？");
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							/*
							 * logout.logout(new RequestListener() { public void
							 * onWeiboException(WeiboException arg0) {
							 * 
							 * } public void onComplete(String arg0) { Intent
							 * intent = new Intent(mActivity, LoginUI.class);
							 * startActivity(intent); mActivity.finish(); } });
							 */
						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							Toast.makeText(mActivity, "取消", 0).show();
						}
					});
			builder.show();
			break;

		default:
			break;
		}
	}

}
