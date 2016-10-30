package com.example.myweibo.adapter;

import java.util.List;

import com.example.myweibo.ImageActivity;
import com.example.myweibo.R;
import com.example.myweibo.domain.WeiboBean;
import com.example.myweibo.domain.WeiboBean.StatusesBean;
import com.example.myweibo.domain.WeiboBean.StatusesBean.PicUrlsBean;
import com.example.myweibo.domain.WeiboBean.StatusesBean.RetweetedStatusBean;
import com.example.myweibo.utils.ImageLoadUtils;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GridViewAdapter extends BaseAdapter {

	Context mContext;
	List<PicUrlsBean> mList;
	String thumbnail_pic;
	StatusesBean bean;

	public GridViewAdapter(Context mContext, List<PicUrlsBean> pic_url,
			StatusesBean bean2) {
		this.mContext = mContext;
		this.mList = pic_url;
		this.bean = bean2;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		View view;
		if (convertView == null) {
			view = View.inflate(mContext, R.layout.weibogridview_layout, null);

		} else {
			view = convertView;
		}
		ImageView img = (ImageView) view.findViewById(R.id.weibo_grid_img);
		ImageLoadUtils.INSTANCE.loadImageView(img, mList.get(position)
				.getThumbnail_pic());
		img.setOnClickListener(new MyClickListener(bean, position));
		return view;
	}

	private class MyClickListener implements OnClickListener {

		WeiboBean.StatusesBean bean;
		int position;

		public MyClickListener(WeiboBean.StatusesBean bean, int position) {
			this.bean = bean;
			this.position = position;
		}

		public void onClick(View v) {
			String large = bean.getPic_urls().get(position).getThumbnail_pic()
					.replace("thumbnail", "large");
			Intent intent = new Intent(mContext, ImageActivity.class);
			intent.putExtra("picurl", large);
			mContext.startActivity(intent);

		}
	}

}
