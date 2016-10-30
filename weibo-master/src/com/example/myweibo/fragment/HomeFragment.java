package com.example.myweibo.fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myweibo.CommentActivity;
import com.example.myweibo.ImageActivity;
import com.example.myweibo.MainActivity;
import com.example.myweibo.R;
import com.example.myweibo.RetweentActivity;
import com.example.myweibo.RetweentMainBody;
import com.example.myweibo.UserInfoActivity;
import com.example.myweibo.WeiboMainBody;
import com.example.myweibo.adapter.GridViewAdapter;
import com.example.myweibo.adapter.RetweentGridViewAdapter;
import com.example.myweibo.domain.WeiboBean;
import com.example.myweibo.domain.WeiboBean.StatusesBean;
import com.example.myweibo.domain.WeiboBean.StatusesBean.PicUrlsBean;
import com.example.myweibo.domain.WeiboBean.StatusesBean.RetweetedStatusBean;
import com.example.myweibo.fragment.HomeFragment.HomeListViewAdapter.ViewHolder;
import com.example.myweibo.utils.AccessTokenKeeper;
import com.example.myweibo.utils.CacheUtils;
import com.example.myweibo.utils.FormatSource;
import com.example.myweibo.utils.FormatTime;
import com.example.myweibo.utils.ImageLoadUtils;
import com.example.myweibo.widget.CircleImageView;
import com.example.myweibo.widget.MyGridView;
import com.example.myweibo.widget.loopj.android.image.SmartImageTask.OnCompleteListener;
import com.example.myweibo.widget.loopj.android.image.SmartImageView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.models.User;

public class HomeFragment extends BaseFragment {
	private static final String HOMEFRAGMENT_JSON_KEY = "HomeFragment";
	private static final String TIME_KEY = HOMEFRAGMENT_JSON_KEY + "date";
	SoundPool soundPool;
	int soundid;
	private View view;
	private TextView tv_userName;
	private Oauth2AccessToken mAccessToken;
	private String uid;
	private UsersAPI users;
	private Gson gson;
	SharedPreferences mSharedPreferences;
	private PullToRefreshListView mListView;
	private HomeListViewAdapter adapter;
	private List<WeiboBean.StatusesBean> weiboList;
	int page = 1;
	BitmapUtils bitmapUtils;
	private String json;
	private long currentTime;

	public View initView() {
		/*
		 * TextView tv=new TextView(getContext()); tv.setText("首頁");
		 * tv.setGravity(Gravity.CENTER); return tv;
		 */
		view = View.inflate(mActivity, R.layout.home, null);
		bitmapUtils = new BitmapUtils(mActivity);
		initSelfView();
		initData();
		mListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ViewHolder holder = (ViewHolder) view.getTag();
				WeiboBean.StatusesBean bean = holder.weibo;
				Bundle bundle = new Bundle();
				bundle.putSerializable("weibo", (Serializable) bean);
				Intent intent = new Intent(getContext(), WeiboMainBody.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		json = CacheUtils.getString(mActivity, HOMEFRAGMENT_JSON_KEY);
		if (json != null) {
			processData();
		}
		currentTime = CacheUtils.getLong(mActivity, TIME_KEY);
		if (currentTime != 0
				&& System.currentTimeMillis() - currentTime < 60 * 1000) {

		} else {
			MainActivity.mainActivity.mStatusesAPI.friendsTimeline(0, 0, 20, 1,
					false, 0, false, new RequestListener() {
						public void onWeiboException(WeiboException arg0) {
							AlertDialog.Builder builder = new AlertDialog.Builder(
									mActivity);
							TextView tv = new TextView(mActivity);
							tv.setText("请重试");
							builder.setView(tv);
							builder.show();
						}

						public void onComplete(String arg0) {
							try {
								WeiboBean bean = gson.fromJson(arg0,
										WeiboBean.class);
								weiboList = bean.getStatuses();
								adapter = new HomeListViewAdapter(weiboList,
										getActivity());
								mListView.setAdapter(adapter);
								CacheUtils.setString(mActivity,
										HOMEFRAGMENT_JSON_KEY, arg0);

								CacheUtils.setLong(mActivity, TIME_KEY,
										System.currentTimeMillis());
							} catch (JsonSyntaxException e) {
								// TODO: handle exception
							}
						}
					});
		}

		return view;
	}

	private void processData() {
		WeiboBean bean = gson.fromJson(json, WeiboBean.class);
		weiboList = bean.getStatuses();
		adapter = new HomeListViewAdapter(weiboList, getActivity());
		mListView.setAdapter(adapter);

	}

	private void initSelfView() {
		tv_userName = (TextView) view.findViewById(R.id.tv_username);
		mListView = (PullToRefreshListView) view.findViewById(R.id.home_list);
		soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
		soundid = soundPool.load(mActivity, R.raw.weibo, 1);
		// soundid=soundPool.load(mActivity,R., 1);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initData() {
		weiboList = new ArrayList<WeiboBean.StatusesBean>();
		mAccessToken = AccessTokenKeeper.readAccessToken(mActivity);
		mSharedPreferences = mActivity.getSharedPreferences("config",
				Context.MODE_PRIVATE);
		tv_userName.setText(mSharedPreferences.getString("username", ""));
		uid = mAccessToken.getUid();
		long id = Long.valueOf(uid);
		users = MainActivity.mainActivity.users;
		gson = new Gson();

		users.show(id, new RequestListener() {
			public void onWeiboException(WeiboException arg0) {
				AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
				TextView tv = new TextView(mActivity);
				tv.setText("请重试");
				builder.setView(tv);
				builder.show();
			}

			public void onComplete(String arg0) {
				User user = User.parse(arg0);
				if ("".equals(mSharedPreferences.getString("username", ""))) {
					tv_userName.setText(user.name);
				}
				mSharedPreferences.edit().putString("username", user.name)
						.commit();
				// soundPool.play(soundid, 1.0f, 1.0f, 1, 0, 1.0f);

			}
		});
		mListView.setMode(Mode.BOTH);
		mListView.setOnRefreshListener(new OnRefreshListener2() {
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				/**
				 * 上一次刷新的时间
				 */

				String label = DateUtils.formatDateTime(mActivity,
						System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
								| DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);

				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				MainActivity.mainActivity.mStatusesAPI.friendsTimeline(0, 0,
						20, 1, false, 0, false, new RequestListener() {
							public void onWeiboException(WeiboException arg0) {
								
								AlertDialog.Builder builder = new AlertDialog.Builder(
										mActivity);
								TextView tv = new TextView(mActivity);
								tv.setText("请重试");
								builder.setView(tv);
								builder.show();
								mListView.onRefreshComplete();

							}

							public void onComplete(String arg0) {
								WeiboBean bean = gson.fromJson(arg0,
										WeiboBean.class);
								weiboList = bean.getStatuses();
								mListView.setAdapter(adapter);
								adapter = new HomeListViewAdapter(weiboList,
										getActivity());
								adapter.notifyDataSetChanged();
								mListView.onRefreshComplete();
								soundPool.play(soundid, 1.0f, 1.0f, 1, 0, 1.0f);

								// 缓存数据
								CacheUtils.setString(mActivity,
										HOMEFRAGMENT_JSON_KEY, arg0);
							}
						});

			}

			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				MainActivity.mainActivity.mStatusesAPI.friendsTimeline(0, 0,
						20, ++page, false, 0, false, new RequestListener() {
							public void onWeiboException(WeiboException arg0) {
								AlertDialog.Builder builder = new AlertDialog.Builder(
										mActivity);
								TextView tv = new TextView(mActivity);
								tv.setText("请重试");
								builder.setView(tv);
								builder.show();
								mListView.onRefreshComplete();
							}

							public void onComplete(String arg0) {
								WeiboBean bean = gson.fromJson(arg0,
										WeiboBean.class);
								weiboList = bean.getStatuses();
								adapter = new HomeListViewAdapter(weiboList,
										getActivity());
								mListView.setAdapter(adapter);
								mListView.onRefreshComplete();
								adapter.notifyDataSetChanged();
								soundPool.play(soundid, 1.0f, 1.0f, 1, 0, 1.0f);
								// 缓存数据
								CacheUtils.setString(mActivity, HOMEFRAGMENT_JSON_KEY,
										arg0);

							}
						});
			}
		});

	}

	public class HomeListViewAdapter extends BaseAdapter {
		private List<WeiboBean.StatusesBean> mList;
		private Context mContext;

		public HomeListViewAdapter(List<WeiboBean.StatusesBean> mList,
				Context mContext) {
			this.mList = mList;
			this.mContext = mContext;
		}

		public int getCount() {
			return mList.size();
		}

		public Object getItem(int position) {

			return mList.get(position);
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(mContext, R.layout.weibo_layout,
						null);
				holder = new ViewHolder();
				holder.img_userheader = (CircleImageView) convertView
						.findViewById(R.id.weibo_userimg);
				holder.tv_username = (TextView) convertView
						.findViewById(R.id.weibo_username);
				holder.tv_weibo_time = (TextView) convertView
						.findViewById(R.id.weibo_time);
				holder.tv_source = (TextView) convertView
						.findViewById(R.id.weibo_source);
				holder.tv_weibo_desc = (TextView) convertView
						.findViewById(R.id.weibo_desc);

				holder.gv_img = (MyGridView) convertView
						.findViewById(R.id.weibo_desc_img);
				holder.tv_weibo_transmitCount = (CheckBox) convertView
						.findViewById(R.id.weibo_transmit);
				holder.tv_weibo_CommentsCount = (CheckBox) convertView
						.findViewById(R.id.weibo_comment);
				holder.tv_weibo_likeCount = (CheckBox) convertView
						.findViewById(R.id.weibo_like);
				holder.main_weibo_retweent_ll = (LinearLayout) convertView
						.findViewById(R.id.main_weibo_retweent_container);
				holder.tv_retweent_name = (TextView) convertView
						.findViewById(R.id.main_weibo_retweent_name);
				holder.tv_retweent_content = (TextView) convertView
						.findViewById(R.id.main_weibo_retweent_content);
				holder.img_retweent_desc = (MyGridView) convertView
						.findViewById(R.id.main_weibo_retweent_img);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final WeiboBean.StatusesBean status = mList.get(position);
			System.out.println(status.toString()
					+ "final WeiboBean.StatusesBean");
			holder.weibo = status;
			List<PicUrlsBean> l = status.getPic_urls();
			if (l != null) {
				holder.gv_img.setAdapter(new GridViewAdapter(mActivity, l,
						status));
			}
			RetweetedStatusBean retween = status.getRetweeted_status();
			if (retween != null) {
				holder.main_weibo_retweent_ll.setVisibility(View.VISIBLE);
				holder.main_weibo_retweent_ll
						.setOnClickListener(new RetweentContainerListenner(
								retween, position));
				holder.tv_retweent_name.setText(retween.getUser().getName());
				holder.tv_retweent_content.setText(retween.getText());
				if (retween.getPic_urls() != null) {
					holder.img_retweent_desc
							.setAdapter(new RetweentGridViewAdapter(mActivity,
									retween.getPic_urls(), retween));

				} else {
					holder.img_retweent_desc.setVisibility(View.GONE);
				}
			} else {
				holder.main_weibo_retweent_ll.setVisibility(View.GONE);
			}
			holder.img_userheader.setImageUrl(status.getUser()
					.getProfile_image_url());
			holder.img_userheader.setOnClickListener(new UserImageClick(status,
					position));

			holder.tv_username.setText(status.getUser().getName());
			holder.tv_weibo_time.setText(FormatTime.getTime(status
					.getCreated_at()));

			String source = status.getSource();
			holder.tv_source.setText("来自" + " "
					+ FormatSource.getSource(source));
			holder.tv_weibo_desc.setText(status.getText());

			holder.tv_weibo_transmitCount.setText(status.getReposts_count()
					+ "");

			holder.tv_weibo_transmitCount
					.setOnCheckedChangeListener(new MyRetweentListenner(status,
							position));
			holder.tv_weibo_CommentsCount.setText(status.getComments_count()
					+ "");
			holder.tv_weibo_CommentsCount
					.setOnCheckedChangeListener(new MyCommentListenner(status));
			holder.tv_weibo_likeCount.setText(status.getAttitudes_count() + "");

			return convertView;
		}

		public class ViewHolder {
			CircleImageView img_userheader;
			TextView tv_username;
			TextView tv_weibo_time;
			TextView tv_source;
			TextView tv_weibo_desc;

			MyGridView gv_img;

			CheckBox tv_weibo_transmitCount;
			CheckBox tv_weibo_CommentsCount;
			CheckBox tv_weibo_likeCount;
			WeiboBean.StatusesBean weibo;
			LinearLayout main_weibo_retweent_ll;
			TextView tv_retweent_name;
			TextView tv_retweent_content;
			MyGridView img_retweent_desc;
		}

	}

	private class UserImageClick implements OnClickListener {
		WeiboBean.StatusesBean bean;
		int position;

		public UserImageClick(WeiboBean.StatusesBean bean, int position) {
			this.bean = bean;
			this.position = position;
		}

		public void onClick(View v) {
			Intent intent = new Intent(getContext(), UserInfoActivity.class);
			intent.putExtra("uid", bean.getUser().getId());
			intent.putExtra("uname", bean.getUser().getName());
			startActivity(intent);
		}

	}

	private class RetweentContainerListenner implements OnClickListener {
		RetweetedStatusBean bean;
		int position;

		public RetweentContainerListenner(RetweetedStatusBean bean, int position) {
			this.bean = bean;
			this.position = position;
		}

		public void onClick(View v) {
			Intent intent = new Intent(mActivity, RetweentMainBody.class);
			intent.putExtra("retweentweibo", bean);
			startActivity(intent);

		}

	}

	private class MyRetweentListenner implements OnCheckedChangeListener {
		WeiboBean.StatusesBean bean;
		int position;

		public MyRetweentListenner(WeiboBean.StatusesBean bean, int position) {
			this.bean = bean;
			this.position = position;
		}

		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (isChecked) {
				Intent intent = new Intent(mActivity, RetweentActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("retweent", bean);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		}

	}

	private class MyCommentListenner implements OnCheckedChangeListener {

		WeiboBean.StatusesBean bean;

		public MyCommentListenner(WeiboBean.StatusesBean bean) {
			this.bean = bean;
		}

		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (isChecked) {
				Intent commentIntent = new Intent(mActivity,
						CommentActivity.class);
				commentIntent.putExtra("weiboid", bean.getId());
				startActivity(commentIntent);
			}
		}

	}
}
