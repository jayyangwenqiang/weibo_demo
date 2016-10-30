package com.example.myweibo.adapter;

import java.util.List;

import com.example.myweibo.MainActivity;
import com.example.myweibo.R;
import com.example.myweibo.ReplyActivity;
import com.example.myweibo.domain.AtMebBean;
import com.example.myweibo.domain.AtMebBean.CommentsBean;
import com.example.myweibo.domain.CommentMeBean;
import com.example.myweibo.utils.FormatSource;
import com.example.myweibo.utils.FormatTime;
import com.example.myweibo.widget.CircleImageView;
import com.example.myweibo.widget.loopj.android.image.SmartImageView;

import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AtMeAdapter extends BaseAdapter {
	private Context mContext;
	private List<AtMebBean.CommentsBean> mList;
	public AtMeAdapter(Context mContext, List<AtMebBean.CommentsBean> mList) {
		this.mContext = mContext;
		this.mList = mList;
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return null == mList ? 0 : mList.size();
	}
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		viewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.at_me_layout, null);
			holder = new viewHolder();
			holder.header = (CircleImageView) convertView
					.findViewById(R.id.at_me_header);
			holder.tv_name = (TextView) convertView
					.findViewById(R.id.at_me_name);
			holder.tv_time = (TextView) convertView
					.findViewById(R.id.at_me_time);
			holder.tv_source = (TextView) convertView
					.findViewById(R.id.at_me_source);
			holder.tv_reContent = (TextView) convertView
					.findViewById(R.id.at_me_recontent);
			holder.img = (SmartImageView) convertView
					.findViewById(R.id.at_me_img);
			holder.tv_auth_name = (TextView) convertView
					.findViewById(R.id.at_me_auth_name);
			holder.tv_content = (TextView) convertView
					.findViewById(R.id.at_me_comtent);
			holder.bt_reply = (Button) convertView.findViewById(R.id.at_me_bt);
			convertView.setTag(holder);
		} else {
			holder = (viewHolder) convertView.getTag();
		}
		holder.header.setImageUrl(mList.get(position).getUser()
				.getProfile_image_url());
		holder.tv_name.setText(mList.get(position).getUser().getName());
		holder.tv_time.setText(FormatTime.getTime(mList.get(position)
				.getCreated_at()));
		holder.tv_source.setText("来自" + " "
				+ FormatSource.getSource(mList.get(position).getSource()));
		holder.tv_reContent.setText(mList.get(position).getText());
		holder.img.setImageUrl(mList.get(position).getStatus()
				.getThumbnail_pic());

		holder.tv_auth_name.setText(mList.get(position).getStatus().getUser()
				.getName());
		holder.tv_content.setText(mList.get(position).getStatus().getText());
		holder.bt_reply.setOnClickListener(new MyOnClickListenner(mList
				.get(position), position));
		return convertView;
	}

	public static class viewHolder {
		CircleImageView header;
		TextView tv_name;
		TextView tv_time;
		TextView tv_source;
		TextView tv_reContent;
		SmartImageView img;
		TextView tv_auth_name;
		TextView tv_content;
		Button bt_reply;
	}

	private class MyOnClickListenner implements OnClickListener {
		CommentsBean bean;
		int position;

		public MyOnClickListenner(CommentsBean bean, int position) {
			this.bean = bean;
			this.position = position;
		}

		public void onClick(View v) {
			Intent intent = new Intent(mContext, ReplyActivity.class);
			intent.putExtra("commentid", String.valueOf(bean.getId()));
			mContext.startActivity(intent);
		}

	}

}
