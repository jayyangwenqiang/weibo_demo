package com.example.myweibo.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.myweibo.AtMeActivity;
import com.example.myweibo.CommentMeActivity;
import com.example.myweibo.MainActivity;
import com.example.myweibo.R;
import com.example.myweibo.adapter.MsgListAdapter;
import com.example.myweibo.domain.Comment;
import com.example.myweibo.domain.Comment.CommentsBean;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class MsgFragment extends BaseFragment {
	private View view;
	private PullToRefreshListView mRefreshListView;
	private List<CommentsBean> mList;

	public View initView() {
		view = View.inflate(mActivity, R.layout.message, null);
		initSelf();
		initData();
		return view;
	}
	private void initData() {
		mRefreshListView.setAdapter(new MsgListAdapter(mActivity, mList));
		mRefreshListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 1:
					Intent intent_AtMeActivity = new Intent(mActivity,
							AtMeActivity.class);
					startActivity(intent_AtMeActivity);
					break;
				case 2:
					Intent intent_CommentMe = new Intent(mActivity,
							CommentMeActivity.class);
					startActivity(intent_CommentMe);

					break;
				default:
					break;
				}
			}
		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initSelf() {
		mList = new ArrayList<Comment.CommentsBean>();
		mRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.message_list);
		mRefreshListView.setMode(Mode.DISABLED);
		mRefreshListView.setOnRefreshListener(new OnRefreshListener2() {
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
                 
			}
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {

			}
		});
	}

}
