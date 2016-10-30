package com.example.myweibo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.example.myweibo.adapter.AtMeAdapter;
import com.example.myweibo.domain.AtMebBean;
import com.example.myweibo.utils.CacheUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

public class CommentMeActivity extends Activity implements OnClickListener {
	private static final String COMMENTMEACTIVITY_JSON = "CommentMeActivity";
	private Gson gson;
	private ImageView back;
	private Button bt_reply;
	private PullToRefreshListView refreshListView;
	private List<AtMebBean.CommentsBean> mList;
	private String json;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.atme_activity_layout);
		initView();
		initData();
		bindListenner();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void bindListenner() {
		back.setOnClickListener(this);
		refreshListView.setOnRefreshListener(new OnRefreshListener2() {
			@SuppressWarnings("rawtypes")
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				MainActivity.mainActivity.mCommentsAPI.timeline(0, 0, 20, 1,
						false, new RequestListener() {

							public void onWeiboException(WeiboException arg0) {
								// TODO Auto-generated method stub
							}

							public void onComplete(String arg0) {
								AtMebBean bean = gson.fromJson(arg0,
										AtMebBean.class);
								mList = bean.getComments();
								refreshListView.setAdapter(new AtMeAdapter(
										CommentMeActivity.this, mList));
								CacheUtils.setString(CommentMeActivity.this,
										COMMENTMEACTIVITY_JSON, arg0);
								refreshListView.onRefreshComplete();
							}
						});

			}

			@SuppressWarnings("rawtypes")
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				MainActivity.mainActivity.mCommentsAPI.timeline(0, 0, 20, 2,
						false, new RequestListener() {

							public void onWeiboException(WeiboException arg0) {
								// TODO Auto-generated method stub
							}

							public void onComplete(String arg0) {
								AtMebBean bean = gson.fromJson(arg0,
										AtMebBean.class);
								mList = bean.getComments();
								refreshListView.setAdapter(new AtMeAdapter(
										CommentMeActivity.this, mList));
								CacheUtils.setString(CommentMeActivity.this,
										COMMENTMEACTIVITY_JSON, arg0);
								refreshListView.onRefreshComplete();
							}
						});

			}
		});

	}

	private void initView() {
		refreshListView = (PullToRefreshListView) findViewById(R.id.atme_activity_list);
		back = (ImageView) findViewById(R.id.atme_activity_back);
		refreshListView.setMode(Mode.BOTH);

	}

	private void initData() {
		gson = new Gson();
		mList = new ArrayList<AtMebBean.CommentsBean>();
		json = CacheUtils.getString(CommentMeActivity.this,
				COMMENTMEACTIVITY_JSON);
		if (json != null) {
			AtMebBean bean = gson.fromJson(json, AtMebBean.class);
			mList = bean.getComments();
			refreshListView.setAdapter(new AtMeAdapter(CommentMeActivity.this,
					mList));

		}
		MainActivity.mainActivity.mCommentsAPI.timeline(0, 0, 20, 1, false,
				new RequestListener() {
					public void onWeiboException(WeiboException arg0) {
						// TODO Auto-generated method stub
					}

					public void onComplete(String arg0) {
						AtMebBean bean = gson.fromJson(arg0, AtMebBean.class);
						mList = bean.getComments();
						refreshListView.setAdapter(new AtMeAdapter(
								CommentMeActivity.this, mList));
						CacheUtils.setString(CommentMeActivity.this,
								COMMENTMEACTIVITY_JSON, arg0);
						refreshListView.onRefreshComplete();
					}
				});

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.atme_activity_back:
			finish();
			break;

		default:
			break;
		}

	}
}
