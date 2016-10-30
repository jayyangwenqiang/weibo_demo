package com.example.myweibo.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myweibo.R;
import com.example.myweibo.domain.Comment;
import com.example.myweibo.domain.Comment.CommentsBean;
import com.example.myweibo.widget.CircleImageView;
import com.sina.weibo.sdk.api.share.Base;

public class MsgListAdapter extends BaseAdapter {
	Context mContext;
	List<CommentsBean> mList;

	public MsgListAdapter(Context mContext, List<CommentsBean> mList) {
		this.mContext = mContext;
		this.mList = mList;
	}

	public int getCount() {
		return mList.size() + 3;
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
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.messsage_list, parent, false);
			holder = new ViewHolder();
			holder.msg_img_header = (CircleImageView) convertView
					.findViewById(R.id.message_header);
			holder.msg_tv_name = (TextView) convertView
					.findViewById(R.id.message_tv_name);
			holder.msg_tv_desc = (TextView) convertView
					.findViewById(R.id.message_tv_desc);
			holder.msg_tv_time = (TextView) convertView
					.findViewById(R.id.message_tv_time);
			holder.msg_img_raw = (ImageView) convertView
					.findViewById(R.id.message_iv_raw);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (position == 0) {
			holder.msg_img_header
					.setBackgroundResource(R.drawable.messagescenter_at);
			holder.msg_tv_name.setText("@我的");
			holder.msg_img_raw.setVisibility(View.VISIBLE);
			holder.msg_tv_desc.setVisibility(View.GONE);
			holder.msg_tv_time.setVisibility(View.GONE);
		} else if (position == 1) {
			holder.msg_img_header
					.setBackgroundResource(R.drawable.messagescenter_comments);
			holder.msg_tv_name.setText("评论");
			holder.msg_img_raw.setVisibility(View.VISIBLE);
			holder.msg_tv_desc.setVisibility(View.GONE);
			holder.msg_tv_time.setVisibility(View.GONE);
		} else if (position == 2) {
			holder.msg_img_header
					.setBackgroundResource(R.drawable.messagescenter_good);
			holder.msg_tv_name.setText("赞");
			holder.msg_img_raw.setVisibility(View.VISIBLE);
			holder.msg_tv_desc.setVisibility(View.GONE);
			holder.msg_tv_time.setVisibility(View.GONE);
		} else if(position>2){
			CommentsBean bean = mList.get(position-3);
			if (bean.getUser() != null) {
				holder.msg_img_header.setImageUrl(bean.getUser()
						.getProfile_image_url());
				holder.msg_tv_name.setText(bean.getUser().getName());
				holder.msg_tv_time.setText(bean.getCreated_at());
				holder.msg_tv_desc.setText(bean.getText());
				holder.msg_img_raw.setVisibility(View.GONE);
			}
		}
		return convertView;
	}

	class ViewHolder {
		CircleImageView msg_img_header;
		TextView msg_tv_name;
		TextView msg_tv_desc;
		TextView msg_tv_time;
		ImageView msg_img_raw;
	}

}
