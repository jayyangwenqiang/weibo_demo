package com.example.myweibo;

import java.util.ArrayList;


import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myweibo.adapter.GridViewAdapter;
import com.example.myweibo.adapter.RetweentGridViewAdapter;
import com.example.myweibo.domain.WeiboBean;
import com.example.myweibo.domain.WeiboBean.StatusesBean;
import com.example.myweibo.domain.WeiboBean.StatusesBean.RetweetedStatusBean;
import com.example.myweibo.utils.AccessTokenKeeper;
import com.example.myweibo.utils.CacheUtils;
import com.example.myweibo.utils.Contacts;
import com.example.myweibo.utils.FormatSource;
import com.example.myweibo.utils.FormatTime;
import com.example.myweibo.widget.CircleImageView;
import com.example.myweibo.widget.MyGridView;
import com.example.myweibo.widget.MyRelativeLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.CommentsAPI;
import com.sina.weibo.sdk.openapi.models.Comment;
import com.sina.weibo.sdk.openapi.models.CommentList;

public class WeiboMainBody extends Activity implements View.OnClickListener {

	private static final String WEIBO_MAINBODY_JSON = "WeiboMainBody";
	String[] items = { "回复", "转发", "复制", "举报" };
	private ImageView weibo_mainbody_back;
	private PullToRefreshListView mRefreshListView;
	private Intent mIntent;
	private WeiboBean.StatusesBean bean;
	private List<com.sina.weibo.sdk.openapi.models.Comment> mList;
	private long weiboId;
	private ProgressDialog dialog;
	public CommentsAPI mCommentsAPI;
	private Oauth2AccessToken mAccessToken;
	private MyRelativeLayout commentRetweent;
	private MyRelativeLayout comments;
	private MyRelativeLayout commentLike;
	private WeiboMainBodyListAdapter adapter;
	private int page;
	private AlertDialog.Builder builder;
	private String json;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weibo_main_body);
		initView();
		initData();
		listenner();
	}

	private void listenner() {
		mRefreshListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position != 1) {
					final com.example.myweibo.WeiboMainBody.WeiboMainBodyListAdapter.MyViewHolder holder = (com.example.myweibo.WeiboMainBody.WeiboMainBodyListAdapter.MyViewHolder) view
							.getTag();
					builder = new AlertDialog.Builder(WeiboMainBody.this);
					builder.setItems(items,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									switch (which) {
									case 0:
										Intent intent = new Intent(
												WeiboMainBody.this,
												ReplyActivity.class);
										Bundle bundle = new Bundle();
										intent.putExtra("commentid",
												holder.comment.id);
										intent.putExtra("weiboid", weiboId);
										intent.putExtras(bundle);
										startActivity(intent);
										break;
									case 1:
										Toast.makeText(WeiboMainBody.this,
												holder.comment.user.name, 0)
												.show();
										break;
									case 2:
										Toast.makeText(WeiboMainBody.this,
												holder.comment.user.name, 0)
												.show();
										break;
									case 3:
										Toast.makeText(WeiboMainBody.this,
												holder.comment.user.name, 0)
												.show();
										break;
									default:
										break;
									}
								}
							});
					builder.show();

				}
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void initData() {
		dialog.show();
		dialog.setCancelable(true);
		mList = new ArrayList<com.sina.weibo.sdk.openapi.models.Comment>();
		mIntent = getIntent();
		Bundle bundle = mIntent.getExtras();
		bean = (StatusesBean) bundle.get("weibo");
		json = CacheUtils.getString(WeiboMainBody.this, WEIBO_MAINBODY_JSON);

		// 先从缓存中取数据
		if (json != null) {
			CommentList mCommentList = CommentList.parse(json);
			mList = mCommentList.commentList;
			mRefreshListView.setAdapter(adapter);
		}
		if (bean != null) {
			weiboId = bean.getId();
			mCommentsAPI.show(weiboId, 0, 0, 50, 1, 0, new RequestListener() {
				public void onWeiboException(WeiboException arg0) {
					dialog.dismiss();
				}

				public void onComplete(String arg0) {
					CacheUtils.setString(WeiboMainBody.this,
							WEIBO_MAINBODY_JSON, arg0);
					CommentList mCommentList = CommentList.parse(arg0);
					mList = mCommentList.commentList;
					mRefreshListView.setAdapter(adapter);
					dialog.dismiss();
				}
			});
		} else {
			dialog.dismiss();
			Toast.makeText(WeiboMainBody.this, "请求失败", 0).show();
		}
		// 获取评论的异步回调方法
		// 点击事件
		weibo_mainbody_back.setOnClickListener(this);
		commentRetweent.setOnClickListener(this);
		comments.setOnClickListener(this);
		commentLike.setOnClickListener(this);
		adapter = new WeiboMainBodyListAdapter();

		mRefreshListView.setMode(Mode.BOTH);
		mRefreshListView.setOnRefreshListener(new OnRefreshListener2() {
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub
				mCommentsAPI.show(weiboId, 0, 0, 50, 1, 0,
						new RequestListener() {

							public void onWeiboException(WeiboException arg0) {
							}

							public void onComplete(String arg0) {
								CommentList mCommentList = CommentList
										.parse(arg0);
								mList = mCommentList.commentList;
								mRefreshListView.setAdapter(adapter);
								CacheUtils.setString(WeiboMainBody.this,
										WEIBO_MAINBODY_JSON, arg0);
								mRefreshListView.onRefreshComplete();
							}
						});
			}

			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				mCommentsAPI.show(weiboId, 0, 0, 50, ++page, 0,
						new RequestListener() {
							public void onWeiboException(WeiboException arg0) {
							}

							public void onComplete(String arg0) {

								CommentList mCommentList = CommentList
										.parse(arg0);
								mList = mCommentList.commentList;
								mRefreshListView.setAdapter(adapter);
								CacheUtils.setString(WeiboMainBody.this,
										WEIBO_MAINBODY_JSON, arg0);
								mRefreshListView.onRefreshComplete();
							}
						});
			}
		});

	}

	private void initView() {
		dialog = new ProgressDialog(WeiboMainBody.this);
		dialog.setMessage("正在加载");
		mAccessToken = AccessTokenKeeper.readAccessToken(WeiboMainBody.this);
		mCommentsAPI = new CommentsAPI(WeiboMainBody.this, Contacts.APPKEY,
				mAccessToken);
		mRefreshListView = (PullToRefreshListView) findViewById(R.id.weibo_mainbody_list);
		weibo_mainbody_back = (ImageView) findViewById(R.id.weibo_mainbody_back);
		commentRetweent = (MyRelativeLayout) findViewById(R.id.weibo_mainbody_retweet);
		comments = (MyRelativeLayout) findViewById(R.id.weibo_mainbody_comment);
		commentLike = (MyRelativeLayout) findViewById(R.id.weibo_mainbody_zan);

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.weibo_mainbody_back:
			finish();
			break;
		case R.id.weibo_mainbody_retweet:
			// 转发微博
			Intent intent = new Intent(WeiboMainBody.this,
					RetweentActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("retweent", bean);
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		case R.id.weibo_mainbody_comment:
			// 评论 微博
			Intent commentIntent = new Intent(WeiboMainBody.this,
					CommentActivity.class);
			commentIntent.putExtra("weiboid", weiboId);
			startActivity(commentIntent);
			break;
		case R.id.weibo_mainbody_zan:
			Toast.makeText(WeiboMainBody.this, "赞微博", 0).show();
			break;
		default:
			break;
		}
	}

	private class WeiboMainBodyListAdapter extends BaseAdapter {
		private int currentView;

		public int getViewTypeCount() {
			return 2;
		}

		public int getItemViewType(int position) {
			if (position == 0) {
				return 0;
			} else {
				return 1;
			}
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return mList == null ? 1 : mList.size() + 1;
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			currentView = getItemViewType(position);
			if (currentView == 0) {
				convertView = View.inflate(WeiboMainBody.this,
						R.layout.comment_weibo_header, null);
				CircleImageView auth_header = (CircleImageView) convertView
						.findViewById(R.id.weibo_userimg);
				TextView tv_auth_name = (TextView) convertView
						.findViewById(R.id.weibo_username);
				TextView tv_time = (TextView) convertView
						.findViewById(R.id.weibo_time);
				TextView tv_source = (TextView) convertView
						.findViewById(R.id.weibo_source);
				TextView tv_desc = (TextView) convertView
						.findViewById(R.id.weibo_desc);
				GridView img_desc = (GridView) convertView
						.findViewById(R.id.gv_img_t);

				LinearLayout retweent_container = (LinearLayout) convertView
						.findViewById(R.id.z_retweent_container);

				TextView retweent_content = (TextView) convertView
						.findViewById(R.id.z_weibo_retweent_content);
				TextView retweent_name = (TextView) convertView
						.findViewById(R.id.z_weibo_retweent_name);
				MyGridView img_gv = (MyGridView) convertView
						.findViewById(R.id.z_weibo_retweent_img);
				if (bean != null) {
					auth_header.setImageUrl(bean.getUser()
							.getProfile_image_url());
					auth_header.setOnClickListener(new WeiBoHeader(bean,
							position));
					tv_auth_name.setText(bean.getUser().getName());
					tv_time.setText(FormatTime.getTime(bean.getCreated_at()));
					String source = bean.getSource();
					tv_source.setText("来自" + " "
							+ FormatSource.getSource(source));
					tv_desc.setText(bean.getText());
					if (bean.getPic_urls() != null) {
						img_desc.setAdapter(new GridViewAdapter(
								WeiboMainBody.this, bean.getPic_urls(), bean));
					} else {
						img_desc.setVisibility(View.GONE);
					}

					RetweetedStatusBean retweent = bean.getRetweeted_status();

					if (retweent != null) {
						retweent_name.setText(retweent.getUser().getName());

						retweent_content.setText(retweent.getText());
						retweent_container
								.setOnClickListener(new MyContainerListenner(
										retweent));
						if (retweent.getPic_urls() != null) {
							img_gv.setAdapter(new RetweentGridViewAdapter(
									WeiboMainBody.this, retweent.getPic_urls(),
									retweent));

						} else {
							img_gv.setVisibility(View.GONE);
						}

					} else {
						retweent_container.setVisibility(View.GONE);
					}
				}
			} else {
				MyViewHolder holder = null;
				if (convertView == null) {
					convertView = View.inflate(WeiboMainBody.this,
							R.layout.comment_weibo, null);
					holder = new MyViewHolder();
					holder.img_commentsUserHeager = (CircleImageView) convertView
							.findViewById(R.id.comment_userheader);
					holder.tv_comment_userName = (TextView) convertView
							.findViewById(R.id.comment_weibo_username);
					holder.tv_comment_time = (TextView) convertView
							.findViewById(R.id.comment_time);
					holder.tv_comment_content = (TextView) convertView
							.findViewById(R.id.comment_content);
					holder.img_comment_rep = (ImageView) convertView
							.findViewById(R.id.comment_rep);
					holder.comment_rep_s = (RelativeLayout) convertView
							.findViewById(R.id.comment_rep_s);
					holder.tv_comment_auth = (TextView) convertView
							.findViewById(R.id.comment_weibo_auth);
					holder.tv_comment_rep_comment = (TextView) convertView
							.findViewById(R.id.comment_rep_s_content);
					holder.comment = mList.get(position - 1);
					convertView.setTag(holder);

				} else {
					holder = (MyViewHolder) convertView.getTag();
				}
				if (holder != null) {
					com.sina.weibo.sdk.openapi.models.Comment comment = mList
							.get(position - 1);
					com.sina.weibo.sdk.openapi.models.Comment reComment = comment.reply_comment;
					if (reComment != null) {
						holder.comment_rep_s.setVisibility(View.VISIBLE);
						holder.tv_comment_auth.setText(reComment.user.name
								+ ":");

						holder.tv_comment_rep_comment.setText(reComment.text);
					} else {
						holder.comment_rep_s.setVisibility(View.GONE);
					}
					holder.img_comment_rep.setOnClickListener(new ImgListenner(
							holder.comment.id, bean.getId()));
					holder.img_commentsUserHeager
							.setImageUrl(comment.user.profile_image_url);
					holder.img_commentsUserHeager
							.setOnClickListener(new UserImageClick(comment,
									position));
					holder.tv_comment_userName.setText(comment.user.name);
					holder.tv_comment_time.setText(FormatTime
							.getTime(comment.created_at));
					holder.tv_comment_content.setText(comment.text);

				}
			}

			return convertView;
		}

		class MyViewHolder {
			CircleImageView img_commentsUserHeager;
			TextView tv_comment_userName;
			TextView tv_comment_time;
			TextView tv_comment_content;
			ImageView img_comment_rep;
			RelativeLayout comment_rep_s;
			TextView tv_comment_auth;
			TextView tv_comment_rep_comment;
			Comment comment;
		}

	}

	private class UserImageClick implements OnClickListener {
		Comment bean;
		int position;

		public UserImageClick(Comment bean, int position) {
			this.bean = bean;
			this.position = position;
		}

		public void onClick(View v) {
			Intent intent = new Intent(WeiboMainBody.this,
					UserInfoActivity.class);
			intent.putExtra("uid", Long.valueOf(bean.user.id));
			intent.putExtra("uname", bean.user.name);
			System.out.println(bean.user.name + ":" + bean.user.id);
			startActivity(intent);

		}

	}

	private class MyContainerListenner implements OnClickListener {

		private RetweetedStatusBean retweeted;

		public MyContainerListenner(RetweetedStatusBean retweeted) {
			this.retweeted = retweeted;
		}

		public void onClick(View v) {
			Intent intent = new Intent(WeiboMainBody.this,
					RetweentMainBody.class);
			intent.putExtra("retweentweibo", retweeted);
			startActivity(intent);
		}

	}

	private class ImgListenner implements OnClickListener {
		String comment;
		long weiboid;

		public ImgListenner(String comment, long weiboid) {
			this.comment = comment;
			this.weiboid = weiboid;
		}

		public void onClick(View v) {
			Intent intent = new Intent(WeiboMainBody.this, ReplyActivity.class);
			Bundle bundle = new Bundle();
			intent.putExtra("commentid", comment);
			intent.putExtra("weiboid", weiboId);
			intent.putExtras(bundle);
			startActivity(intent);

		}

	}

	private class WeiBoHeader implements OnClickListener {
		WeiboBean.StatusesBean bean;
		int position;

		public WeiBoHeader(WeiboBean.StatusesBean bean, int position) {
			this.bean = bean;
			this.position = position;
		}

		public void onClick(View v) {
			Intent intent = new Intent(WeiboMainBody.this,
					UserInfoActivity.class);
			intent.putExtra("uid", bean.getUser().getId());
			intent.putExtra("uname", bean.getUser().getName());
			startActivity(intent);
		}

	}

}
