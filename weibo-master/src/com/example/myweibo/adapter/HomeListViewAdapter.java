package com.example.myweibo.adapter;

import java.util.List;

import com.example.myweibo.R;
import com.example.myweibo.domain.WeiboBean;
import com.example.myweibo.utils.FormatTime;
import com.example.myweibo.widget.CircleImageView;
import com.example.myweibo.widget.loopj.android.image.SmartImageView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

public class HomeListViewAdapter extends BaseAdapter {
	private PullToRefreshListView mListView;
	private List<WeiboBean> mList;
	private Context mContext;

	public HomeListViewAdapter(PullToRefreshListView mListView,
			List<WeiboBean> mList, Context mContext) {
		this.mListView = mListView;
		this.mList = mList;
		this.mContext = mContext;
	}

	public int getCount() {
		return mList.get(0).getStatuses().size();
	}

	public Object getItem(int position) {

		return mList.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.weibo_layout, null);
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
			holder.img_weibo_desc = (SmartImageView) convertView
					.findViewById(R.id.weibo_desc_img);
			holder.img_weibo_desc.setTag(mList.get(0).getStatuses()
					.get(position).getId());
			holder.tv_weibo_transmitCount = (CheckBox) convertView
					.findViewById(R.id.weibo_transmit);
			holder.tv_weibo_CommentsCount = (CheckBox) convertView
					.findViewById(R.id.weibo_comment);
			holder.tv_weibo_likeCount = (CheckBox) convertView
					.findViewById(R.id.weibo_like);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		WeiboBean weibo = mList.get(0);
		holder.weibo = weibo;
		holder.img_userheader.setImageUrl(weibo.getStatuses().get(position)
				.getUser().getProfile_image_url());
		holder.tv_username.setText(weibo.getStatuses().get(position).getUser()
				.getName());
		holder.tv_weibo_time.setText(weibo.getStatuses().get(position)
				.getCreated_at());
		String source = weibo.getStatuses().get(position).getSource();
		String[] str1 = source.split(">");
		String str2 = str1[1];
		String[] str3 = str2.split("<");
		holder.tv_source.setText("来自" + " " + str3[0]);
		holder.tv_weibo_desc.setText(weibo.getStatuses().get(position)
				.getText());

		String url = weibo.getStatuses().get(position).getBmiddle_pic();
		if (TextUtils.isEmpty(url)) {
			holder.img_weibo_desc.setVisibility(View.INVISIBLE);
		} else {
			holder.img_weibo_desc.setTag(url);// 给imageview设置tag
			if (holder.img_weibo_desc.getTag().equals(
					weibo.getStatuses().get(position).getPic_urls().get(0))) {
				holder.img_weibo_desc.setImageUrl(url);
			} else {

			}
		}
		holder.tv_weibo_transmitCount.setText(weibo.getStatuses().get(position)
				.getReposts_count()
				+ "");
		holder.tv_weibo_CommentsCount.setText(weibo.getStatuses().get(position)
				.getComments_count()
				+ "");
		holder.tv_weibo_likeCount.setText(weibo.getStatuses().get(position)
				.getAttitudes_count()
				+ "");

		return convertView;
	}

	 public class ViewHolder {
		CircleImageView img_userheader;
		TextView tv_username;
		TextView tv_weibo_time;
		TextView tv_source;
		TextView tv_weibo_desc;
		SmartImageView img_weibo_desc;
		CheckBox tv_weibo_transmitCount;
		CheckBox tv_weibo_CommentsCount;
		CheckBox tv_weibo_likeCount;
		WeiboBean weibo;
	}

}
