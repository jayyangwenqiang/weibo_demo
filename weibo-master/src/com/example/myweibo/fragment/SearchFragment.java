package com.example.myweibo.fragment;

import java.util.ArrayList;

import java.util.List;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.audiofx.BassBoost.Settings;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myweibo.BaseApplication;
import com.example.myweibo.MainActivity;
import com.example.myweibo.R;
import com.example.myweibo.WebViewActivity;
import com.example.myweibo.domain.Trends;
import com.example.myweibo.domain.Trends.ShowapiResBodyBean.ListBean;
import com.example.myweibo.utils.AccessTokenKeeper;
import com.example.myweibo.utils.CacheUtils;
import com.example.myweibo.utils.Contacts;
import com.example.myweibo.utils.UrlUtils;
import com.example.myweibo.widget.MyDelAutoCompleteTextView;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.openapi.legacy.TrendsAPI;

public class SearchFragment extends BaseFragment implements OnClickListener {

	private static final String SEARCHFRAGMENT_JSON = "SearchFragment";
	private String json;
	SharedPreferences sp;
	ProgressDialog dialog;
	private int what = 1;
	private List<ListBean> bList;
	private Button iv_refrash;
	private View view;
	private TrendsAPI mTrendsAPI;
	private Oauth2AccessToken mAccessToken;
	private MyDelAutoCompleteTextView autoText;
	private TextView hot_top1, hot_top2, hot_top3, hot_top4, hot_top5,
			hot_top6, hot_top7, hot_top8, hot_top9, hot_top10;
	private List<String> mList;
	private Intent intent;
	Gson gson;
	WebView mWebView;
	HttpUtils httpUtils;

	public View initView() {
		// NoHttp.initialize(mActivity);
		intent = new Intent(getActivity(), WebViewActivity.class);
		view = View.inflate(mActivity, R.layout.search, null);
		initData();
		initSelfView();
		initText();
		json = CacheUtils.getString(mActivity, SEARCHFRAGMENT_JSON);
		if (json != null) {
			Editor editor = sp.edit();
			Trends trends = gson.fromJson(json, Trends.class);
			CacheUtils.setString(mActivity, SEARCHFRAGMENT_JSON, json);
			bList = trends.getShowapi_res_body().getList();
			if (bList != null) {
				hot_top1.setText(bList.get(0).getTitle());
				hot_top2.setText(bList.get(1).getTitle());
				hot_top3.setText(bList.get(2).getTitle());
				hot_top4.setText(bList.get(3).getTitle());
				hot_top5.setText(bList.get(4).getTitle());
				hot_top6.setText(bList.get(5).getTitle());
				hot_top7.setText(bList.get(6).getTitle());
				hot_top8.setText(bList.get(7).getTitle());
				hot_top9.setText(bList.get(8).getTitle());
				hot_top10.setText(bList.get(9).getTitle());
				editor.putString("text1", bList.get(0).getTitle());
				editor.putString("text2", bList.get(1).getTitle());
				editor.putString("text3", bList.get(2).getTitle());
				editor.putString("text4", bList.get(3).getTitle());
				editor.putString("text5", bList.get(4).getTitle());
				editor.putString("text6", bList.get(5).getTitle());
				editor.putString("text7", bList.get(6).getTitle());
				editor.putString("text8", bList.get(7).getTitle());
				editor.putString("text9", bList.get(8).getTitle());
				editor.putString("text10", bList.get(9).getTitle());
				System.out.println(bList.get(0).getUrl());
				editor.commit();
			}
		}
		findHotTopic();
		return view;
	}

	private void initText() {
		hot_top1.setText(sp.getString("text1", ""));
		hot_top2.setText(sp.getString("text2", ""));
		hot_top3.setText(sp.getString("text3", ""));
		hot_top4.setText(sp.getString("text4", ""));
		hot_top5.setText(sp.getString("text5", ""));
		hot_top6.setText(sp.getString("text6", ""));
		hot_top7.setText(sp.getString("text7", ""));
		hot_top8.setText(sp.getString("text8", ""));
		hot_top9.setText(sp.getString("text9", ""));
		hot_top10.setText(sp.getString("text10", ""));
	}

	private void findHotTopic() {
		httpUtils.send(HttpMethod.GET, UrlUtils.SEARCH_URL,
				new RequestCallBack<String>() {
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						int code = responseInfo.statusCode;
						if (200 == code) {
							showbList(responseInfo.result);
						}

					}

					private void showbList(String json) {
						Editor editor = sp.edit();
						Trends trends = gson.fromJson(json, Trends.class);
						CacheUtils.setString(mActivity, SEARCHFRAGMENT_JSON,
								json);
						bList = trends.getShowapi_res_body().getList();

						if (bList != null) {
							hot_top1.setText(bList.get(0).getTitle());
							hot_top2.setText(bList.get(1).getTitle());
							hot_top3.setText(bList.get(2).getTitle());
							hot_top4.setText(bList.get(3).getTitle());
							hot_top5.setText(bList.get(4).getTitle());
							hot_top6.setText(bList.get(5).getTitle());
							hot_top7.setText(bList.get(6).getTitle());
							hot_top8.setText(bList.get(7).getTitle());
							hot_top9.setText(bList.get(8).getTitle());
							hot_top10.setText(bList.get(9).getTitle());
							editor.putString("text1", bList.get(0).getTitle());
							editor.putString("text2", bList.get(1).getTitle());
							editor.putString("text3", bList.get(2).getTitle());
							editor.putString("text4", bList.get(3).getTitle());
							editor.putString("text5", bList.get(4).getTitle());
							editor.putString("text6", bList.get(5).getTitle());
							editor.putString("text7", bList.get(6).getTitle());
							editor.putString("text8", bList.get(7).getTitle());
							editor.putString("text9", bList.get(8).getTitle());
							editor.putString("text10", bList.get(9).getTitle());
							System.out.println(bList.get(0).getUrl());
							editor.commit();
						}
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						Toast.makeText(mActivity, msg, 0).show();

					}
				});
	}

	private void initSelfView() {
		autoText = (MyDelAutoCompleteTextView) view
				.findViewById(R.id.search_auto_et);
		hot_top1 = (TextView) view.findViewById(R.id.hot_tv_1);
		hot_top2 = (TextView) view.findViewById(R.id.hot_tv_2);
		hot_top3 = (TextView) view.findViewById(R.id.hot_tv_3);
		hot_top4 = (TextView) view.findViewById(R.id.hot_tv_4);
		hot_top5 = (TextView) view.findViewById(R.id.hot_tv_5);
		hot_top6 = (TextView) view.findViewById(R.id.hot_tv_6);
		hot_top7 = (TextView) view.findViewById(R.id.hot_tv_7);
		hot_top8 = (TextView) view.findViewById(R.id.hot_tv_8);
		hot_top9 = (TextView) view.findViewById(R.id.hot_tv_9);
		hot_top10 = (TextView) view.findViewById(R.id.hot_tv_10);
		iv_refrash = (Button) view.findViewById(R.id.search_refrash);
		hot_top1.setOnClickListener(this);
		hot_top2.setOnClickListener(this);
		hot_top3.setOnClickListener(this);
		hot_top4.setOnClickListener(this);
		hot_top5.setOnClickListener(this);
		hot_top6.setOnClickListener(this);
		hot_top7.setOnClickListener(this);
		hot_top8.setOnClickListener(this);
		hot_top9.setOnClickListener(this);
		hot_top10.setOnClickListener(this);

	}

	private void initData() {
		mWebView = MainActivity.mainActivity.mWebView;
		gson = new Gson();
		httpUtils = new HttpUtils();
		mList = new ArrayList<String>();
		mAccessToken = AccessTokenKeeper.readAccessToken(mActivity);
		mTrendsAPI = new TrendsAPI(mActivity, Contacts.APPKEY, mAccessToken);
		sp = mActivity.getSharedPreferences("config", Context.MODE_PRIVATE);
		dialog = new ProgressDialog(mActivity);
		dialog.setMessage("正在加载");
		dialog.setCancelable(false);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.hot_tv_1:
			if (bList.get(0).getUrl() != null) {
				intent.putExtra("weburl", bList.get(0).getUrl());
				startActivity(intent);
			}
			break;
		case R.id.hot_tv_2:
			if (bList.get(1).getUrl() != null) {
				intent.putExtra("weburl", bList.get(1).getUrl());
				startActivity(intent);
			}
			break;
		case R.id.hot_tv_3:
			if (bList.get(2).getUrl() != null) {
				intent.putExtra("weburl", bList.get(2).getUrl());
				startActivity(intent);
			}
			break;
		case R.id.hot_tv_4:
			if (bList.get(3).getUrl() != null) {
				intent.putExtra("weburl", bList.get(3).getUrl());
				startActivity(intent);
			}
			break;
		case R.id.hot_tv_5:
			if (bList.get(4).getUrl() != null) {
				intent.putExtra("weburl", bList.get(4).getUrl());
				startActivity(intent);
			}
			break;
		case R.id.hot_tv_6:
			if (bList.get(5).getUrl() != null) {
				intent.putExtra("weburl", bList.get(5).getUrl());
				startActivity(intent);
			}
			break;
		case R.id.hot_tv_7:
			if (bList.get(6).getUrl() != null) {
				intent.putExtra("weburl", bList.get(6).getUrl());
				startActivity(intent);
			}
			break;
		case R.id.hot_tv_8:
			if (bList.get(7).getUrl() != null) {
				intent.putExtra("weburl", bList.get(7).getUrl());
				startActivity(intent);
			}
			break;
		case R.id.hot_tv_9:
			if (bList.get(8).getUrl() != null) {
				intent.putExtra("weburl", bList.get(8).getUrl());
				startActivity(intent);
			}
			break;
		case R.id.hot_tv_10:
			if (bList.get(9).getUrl() != null) {
				intent.putExtra("weburl", bList.get(9).getUrl());
				startActivity(intent);
			}
			break;

		default:
			break;
		}

	}
}
