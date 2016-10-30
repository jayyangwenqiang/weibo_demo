package com.example.myweibo.fragment;

import com.example.myweibo.MainActivity;
import com.example.myweibo.R;
import com.example.myweibo.utils.WeiBoUtils;
import com.example.myweibo.widget.CircleImageView;
import com.example.myweibo.widget.loopj.android.image.SmartImageView;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

import android.view.View;
import android.widget.TextView;

public class AtMeFragment extends BaseFragment {
	View view;
	private CircleImageView header;
	private TextView tv_name;
	private TextView tv_time;
	private TextView tv_source;
	private TextView tv_reContent;
	private SmartImageView img;
	private TextView tv_auth_name;
	private TextView tv_content;

	public View initView() {
		view = View.inflate(mActivity, R.layout.comment_me_layout, null);
		init();

		initData();
		return view;
	}

	private void initData() {
		MainActivity.mainActivity.mCommentsAPI.mentions(0, 0, 20, 1, 0, 0,
				new RequestListener() {
					public void onWeiboException(WeiboException arg0) {
						// TODO Auto-generated method stub
					}

					public void onComplete(String arg0) {
						System.out.println(arg0);
					}
				});
	}

	private void init() {

		header = (CircleImageView) view.findViewById(R.id.commentme_header);
		tv_name = (TextView) view.findViewById(R.id.commentme_name);
		tv_time = (TextView) view.findViewById(R.id.commentme_time);
		tv_source = (TextView) view.findViewById(R.id.commentme_source);
		tv_reContent = (TextView) view.findViewById(R.id.commentme_recontent);
		img = (SmartImageView) view.findViewById(R.id.commentme_img);
		tv_auth_name = (TextView) view.findViewById(R.id.commentme_auth_name);
		tv_content = (TextView) view.findViewById(R.id.commentme_comtent);
	}

}
