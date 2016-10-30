package com.example.myweibo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.example.myweibo.utils.ImageLoadUtils;
import com.example.myweibo.widget.loopj.android.image.SmartImageView;

public class ImageActivity extends Activity {
	private ImageView mImageView;
	private String picurl;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.bigpic_layout);
		mImageView = (ImageView) findViewById(R.id.big_pic);
		Intent intent = getIntent();
		picurl = intent.getStringExtra("picurl");
		ImageLoadUtils.INSTANCE.loadImageView(mImageView, picurl);
		mImageView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}
}
