package com.example.myweibo;

import java.util.ArrayList;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.example.myweibo.fragment.BaseFragment;
import com.example.myweibo.fragment.HomeFragment;
import com.example.myweibo.fragment.MsgFragment;
import com.example.myweibo.fragment.SearchFragment;
import com.example.myweibo.fragment.UserFragment;
import com.example.myweibo.service.NetworkService;
import com.example.myweibo.utils.AccessTokenKeeper;
import com.example.myweibo.utils.Contacts;
import com.example.myweibo.widget.MyPopupWindow;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.openapi.CommentsAPI;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.legacy.StatusesAPI;

public class MainActivity extends FragmentActivity implements
		OnCheckedChangeListener {
	SharedPreferences sp;
	private static Context mContext;
	public ImageLoader mImageLoader;
	private RadioGroup mRadioGroup;
	private ViewPager mViewPager;
	private List<BaseFragment> mList;
	private ImageView mImageView;
	public FragmentManager fm;
	private int HOMEPAGER = 0;
	private int MSGPAGER = 1;
	private int SEARCHPAGER = 2;
	private int USERPAGER = 3;
	public static MainActivity mainActivity;
	private Oauth2AccessToken mAccessToken;
	public UsersAPI users;
	public StatusesAPI mStatusesAPI;
	public CommentsAPI mCommentsAPI;
	public Gson gson;
	MyPopupWindow window;
	ImageView sendWeibo_text;
	ImageView sendWeibo_pic;
	View popupWindowView;
	public WebView mWebView;
	private Intent service;
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if ("noNetWork".equals(intent.getAction())) {
				Toast.makeText(mainActivity, "当前无网络连接", Toast.LENGTH_LONG);
			}
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		IntentFilter intentFilter = new IntentFilter("noNetWork");
		registerReceiver(receiver, intentFilter);
		service = new Intent(MainActivity.this, NetworkService.class);
		startService(service);
		mainActivity = this;
		// /////////////////////////////////////
		ImageLoaderConfiguration configuration = ImageLoaderConfiguration
				.createDefault(this);
		ImageLoader.getInstance().init(configuration);
		initView();
		initData();

	}

	private void initData() {
		sp = getSharedPreferences("data", MODE_PRIVATE);
		mWebView = new WebView(getApplicationContext());
		WebSettings setting = mWebView.getSettings();
		setting.setJavaScriptEnabled(true);
		setting.setUseWideViewPort(true);
		setting.setBuiltInZoomControls(true);
		mContext = getApplicationContext();
		mAccessToken = AccessTokenKeeper.readAccessToken(this);
		mStatusesAPI = new StatusesAPI(this, Contacts.APPKEY, mAccessToken);
		mCommentsAPI = new CommentsAPI(this, Contacts.APPKEY, mAccessToken);
		gson = new Gson();
		users = new UsersAPI(this, Contacts.APPKEY, mAccessToken);
		mRadioGroup.setOnCheckedChangeListener(this);
		mImageView = (ImageView) findViewById(R.id.main_add);
		fm = getSupportFragmentManager();
		sendWeibo_text = (ImageView) popupWindowView
				.findViewById(R.id.sendweibo_text);
		sendWeibo_pic = (ImageView) popupWindowView
				.findViewById(R.id.sendweibo_pic);
		mImageView.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				doShowPopupWindon();
			}

			private void doShowPopupWindon() {
				if (window == null) {
					window = new MyPopupWindow(MainActivity.this,
							new OnClickListener() {
								public void onClick(View v) {
									switch (v.getId()) {
									case R.id.sendweibo_close:
										if (window != null
												&& window.isShowing()) {
											window.dismiss();
										}
									default:
										break;
									}
								}
							});
					window.showAtLocation(
							MainActivity.this.findViewById(R.id.main_layout),
							Gravity.BOTTOM, 0, 0);

				} else {

					window.showAtLocation(
							MainActivity.this.findViewById(R.id.main_layout),
							Gravity.BOTTOM, 0, 0);
				}
			}
		});

	}

	private void initView() {
		popupWindowView = LayoutInflater.from(MainActivity.this).inflate(
				R.layout.send_weibo_pupwindow, null);
		mRadioGroup = (RadioGroup) findViewById(R.id.main_rg);
		mViewPager = (ViewPager) this.findViewById(R.id.main_pager);
		mList = new ArrayList<BaseFragment>();
		mList.add(new HomeFragment());
		mList.add(new MsgFragment());
		mList.add(new SearchFragment());
		mList.add(new UserFragment());
		mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
	}

	private class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		public int getCount() {
			if (mList != null) {
				return mList.size();
			}
			return 0;
		}

		public Fragment getItem(int arg0) {
			return mList.get(arg0);
		}
	}

	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.main_home:
			mViewPager.setCurrentItem(HOMEPAGER);
			break;
		case R.id.main_msg:
			mViewPager.setCurrentItem(MSGPAGER);
			break;
		case R.id.main_search:
			mViewPager.setCurrentItem(SEARCHPAGER);
			break;
		case R.id.main_user:
			mViewPager.setCurrentItem(USERPAGER);
			break;
		default:
			break;
		}
	}

	public static Context getContext() {
		return mContext;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (service != null) {
			stopService(service);
			service = null;
		}
		if (receiver != null) {
			unregisterReceiver(receiver);
			receiver = null;
		}
	}
}
