package com.example.myweibo;

import java.util.ArrayList;
import java.util.List;

import com.example.myweibo.adapter.CommentMeAdapter;
import com.example.myweibo.domain.CommentMeBean;
import com.example.myweibo.domain.CommentMeBean.CommentsBean;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class AtMeActivity extends Activity implements OnClickListener {
	private static final String ATMEACTIVITY_JSON = "AtMeActivity";
	private String json;
	private Gson gson;
	private ImageView back;
	private PullToRefreshListView refreshListView;
	private List<CommentsBean> mList;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.commentmt_activity_layout);

		initView();
		initData();
		bindListenner();
	}

	private void bindListenner() {

		back.setOnClickListener(this);

	}

	private void initView() {
		refreshListView = (PullToRefreshListView) findViewById(R.id.commentme_activity_list);
		back = (ImageView) findViewById(R.id.commentme_activity_back);

	}

	private void initData() {
		gson = new Gson();
		mList = new ArrayList<CommentMeBean.CommentsBean>();
		MainActivity.mainActivity.mCommentsAPI.mentions(0, 0, 20, 1, 0, 0,
				new RequestListener() {
					public void onWeiboException(WeiboException arg0) {

					}

					public void onComplete(String arg0) {
						CommentMeBean bean = gson.fromJson(arg0,
								CommentMeBean.class);
						mList = bean.getComments();
						refreshListView.setAdapter(new CommentMeAdapter(
								AtMeActivity.this, mList));
						System.out.println("refreshListView.setAdapter");
					}
				});

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.commentme_activity_back:
			finish();
			break;

		default:
			break;
		}

	}

}
