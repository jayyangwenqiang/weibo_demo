package com.example.myweibo;

import java.util.ArrayList;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.myweibo.adapter.RetweentBodyGridViewAdapter;
import com.example.myweibo.adapter.RetweentGridViewAdapter;
import com.example.myweibo.domain.WeiboBean;
import com.example.myweibo.domain.WeiboBean.StatusesBean;
import com.example.myweibo.domain.WeiboBean.StatusesBean.RetweetedStatusBean;
import com.example.myweibo.utils.AccessTokenKeeper;
import com.example.myweibo.utils.CacheUtils;
import com.example.myweibo.utils.Contacts;
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

public class RetweentMainBody extends Activity implements View.OnClickListener {
	private static final String RETWEENTMAINBODY_JSON = "RetweentMainBody";
	private String json;
	String[] items = { "回复", "转发", "复制", "举报" };
	private ImageView weibo_mainbody_back;
	private PullToRefreshListView mRefreshListView;
	private Intent mIntent;
	private WeiboBean.StatusesBean.RetweetedStatusBean bean;
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

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.retweent_weibo_main_body);
		initView();
		initData();
		listenner();
	}

	private void listenner() {
		mRefreshListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position != 1) {
					final com.example.myweibo.RetweentMainBody.WeiboMainBodyListAdapter.MyViewHolder holder = (com.example.myweibo.RetweentMainBody.WeiboMainBodyListAdapter.MyViewHolder) view
							.getTag();
					builder = new AlertDialog.Builder(RetweentMainBody.this);
					builder.setItems(items,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									switch (which) {
									case 0:
										Intent intent = new Intent(
												RetweentMainBody.this,
												ReplyActivity.class);
										Bundle bundle = new Bundle();
										intent.putExtra("commentid",
												holder.comment.id);
										intent.putExtra("weiboid", weiboId);
										intent.putExtras(bundle);
										startActivity(intent);
										break;
									case 1:
										Toast.makeText(RetweentMainBody.this,
												holder.comment.user.name, 0)
												.show();
										break;
									case 2:
										Toast.makeText(RetweentMainBody.this,
												holder.comment.user.name, 0)
												.show();
										break;
									case 3:
										Toast.makeText(RetweentMainBody.this,
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
		bean = (StatusesBean.RetweetedStatusBean) bundle.get("retweentweibo");
		json = CacheUtils.getString(RetweentMainBody.this,
				RETWEENTMAINBODY_JSON);
		if (json != null) {
			CommentList mCommentList = CommentList.parse(json);
			mList = mCommentList.commentList;
			mRefreshListView.setAdapter(adapter);
			dialog.dismiss();
		}
		if (bean != null) {
			weiboId = bean.getId();
			mCommentsAPI.show(weiboId, 0, 0, 50, 1, 0, new RequestListener() {

				public void onWeiboException(WeiboException arg0) {
					dialog.dismiss();
				}

				public void onComplete(String arg0) {
					CommentList mCommentList = CommentList.parse(arg0);
					mList = mCommentList.commentList;
					mRefreshListView.setAdapter(adapter);
					CacheUtils.setString(RetweentMainBody.this,
							RETWEENTMAINBODY_JSON, arg0);
					dialog.dismiss();
				}
			});
		} else {
			dialog.dismiss();
			Toast.makeText(RetweentMainBody.this, "请求失败", 0).show();
		}

		// 获取评论的异步回调方法

		// 点击事件
		weibo_mainbody_back.setOnClickListener(this);
		commentRetweent.setOnClickListener(this);
		comments.setOnClickListener(this);
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
								mRefreshListView.onRefreshComplete();
								CacheUtils.setString(RetweentMainBody.this,
										RETWEENTMAINBODY_JSON, arg0);
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
								mRefreshListView.onRefreshComplete();
								CacheUtils.setString(RetweentMainBody.this,
										RETWEENTMAINBODY_JSON, arg0);
							}
						});

			}
		});

	}

	private void initView() {
		dialog = new ProgressDialog(RetweentMainBody.this);
		dialog.setMessage("正在加载");
		mAccessToken = AccessTokenKeeper.readAccessToken(RetweentMainBody.this);
		mCommentsAPI = new CommentsAPI(RetweentMainBody.this, Contacts.APPKEY,
				mAccessToken);
		mRefreshListView = (PullToRefreshListView) findViewById(R.id.retweent_weibo_mainbody_list);
		weibo_mainbody_back = (ImageView) findViewById(R.id.retweent_weibo_mainbody_back);
		commentRetweent = (MyRelativeLayout) findViewById(R.id.retweent_weibo_mainbody_retweet);
		comments = (MyRelativeLayout) findViewById(R.id.retweent_weibo_mainbody_comment);
		// commentLike = (MyRelativeLayout)
		// findViewById(R.id.retweent_weibo_mainbody_zan);

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.retweent_weibo_mainbody_back:
			finish();
			break;
		case R.id.retweent_weibo_mainbody_retweet:
			// 转发微博
			Intent intent = new Intent(RetweentMainBody.this,
					RetweentActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("retweent", bean);
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		case R.id.retweent_weibo_mainbody_comment:
			// 评论 微博
			Intent commentIntent = new Intent(RetweentMainBody.this,
					CommentActivity.class);
			commentIntent.putExtra("weiboid", weiboId);
			startActivity(commentIntent);
			break;
		/*
		 * case R.id.retweent_weibo_mainbody_zan:
		 * Toast.makeText(RetweentMainBody.this, "赞微博", 0).show(); break;
		 */
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

		public View getView(int position, View convertView, ViewGroup parent) {
			currentView = getItemViewType(position);
			if (currentView == 0) {
				convertView = View.inflate(RetweentMainBody.this,
						R.layout.retweent_comment_weibo_header, null);
				CircleImageView auth_header = (CircleImageView) convertView
						.findViewById(R.id.retweent_body_weibo_userimg);
				TextView tv_auth_name = (TextView) convertView
						.findViewById(R.id.retweent_body_weibo_username);
				TextView tv_time = (TextView) convertView
						.findViewById(R.id.retweent_body_weibo_time);
				TextView tv_source = (TextView) convertView
						.findViewById(R.id.retweent_body_weibo_source);
				TextView tv_desc = (TextView) convertView
						.findViewById(R.id.retweent_body_weibo_desc);
				GridView img_desc = (GridView) convertView
						.findViewById(R.id.retweent_body_gv_img_t);

				if (bean != null) {
					auth_header.setImageUrl(bean.getUser()
							.getProfile_image_url());
					auth_header.setOnClickListener(new WeiBoHeader(bean,
							position));
					tv_auth_name.setText(bean.getUser().getName());
					tv_time.setText(FormatTime.getTime(bean.getCreated_at()));
					String source = bean.getSource();
					String[] str1 = source.split(">");
					String str2 = str1[1];
					String[] str3 = str2.split("<");
					tv_source.setText("来自" + " " + str3[0]);
					tv_desc.setText(bean.getText());
					if (bean.getPic_urls() != null) {
						img_desc.setAdapter(new RetweentBodyGridViewAdapter(
								RetweentMainBody.this, bean.getPic_urls(), bean));
					} else {
						img_desc.setVisibility(View.GONE);
					}

				}
			} else {
				MyViewHolder holder = null;
				if (convertView == null) {
					convertView = View.inflate(RetweentMainBody.this,
							R.layout.retweent_body_comment_weibo, null);
					holder = new MyViewHolder();
					holder.img_commentsUserHeager = (CircleImageView) convertView
							.findViewById(R.id.retweent_body_comment_userheader);
					holder.tv_comment_userName = (TextView) convertView
							.findViewById(R.id.retweent_body_comment_weibo_username);
					holder.tv_comment_time = (TextView) convertView
							.findViewById(R.id.retweent_body_comment_time);
					holder.tv_comment_content = (TextView) convertView
							.findViewById(R.id.retweent_body_comment_content);
					holder.img_comment_rep = (ImageView) convertView
							.findViewById(R.id.retweent_body_comment_rep);
					holder.comment_rep_s = (RelativeLayout) convertView
							.findViewById(R.id.retweent_body_comment_rep_s);
					holder.tv_comment_auth = (TextView) convertView
							.findViewById(R.id.retweent_body_comment_weibo_auth);
					holder.tv_comment_rep_comment = (TextView) convertView
							.findViewById(R.id.retweent_body_comment_rep_s_content);
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
							comment.id, weiboId));
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
			Intent intent = new Intent(RetweentMainBody.this,
					UserInfoActivity.class);
			intent.putExtra("uid", Long.valueOf(bean.user.id));
			intent.putExtra("uname", bean.user.name);
			System.out.println(bean.user.name + ":" + bean.user.id);
			startActivity(intent);

		}

	}

	private class WeiBoHeader implements OnClickListener {
		WeiboBean.StatusesBean.RetweetedStatusBean bean;
		int position;

		public WeiBoHeader(WeiboBean.StatusesBean.RetweetedStatusBean bean,
				int position) {
			this.bean = bean;
			this.position = position;
		}

		public void onClick(View v) {
			Intent intent = new Intent(RetweentMainBody.this,
					UserInfoActivity.class);
			intent.putExtra("uid", bean.getUser().getId());
			intent.putExtra("uname", bean.getUser().getName());
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
			Intent intent = new Intent(RetweentMainBody.this,
					ReplyActivity.class);
			Bundle bundle = new Bundle();
			intent.putExtra("commentid", comment);
			intent.putExtra("weiboid", weiboId);
			intent.putExtras(bundle);
			startActivity(intent);

		}

	}

}
