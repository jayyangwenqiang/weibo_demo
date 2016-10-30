package com.example.myweibo;

import java.util.ArrayList;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.legacy.StatusesAPI;

public class SendPicWeiboActivity extends Activity implements OnClickListener {
	private TextView tv_cancle;
	private Button bt_send;
	private EditText weibo_content;
	private StatusesAPI mStatusesAPI;
	private ImageView send_gv;
	private Bitmap bitmap;
	private ImageView weibowithpic;
	private RelativeLayout weibowithpic_container;
	private ImageView img_del;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.send_weibo_pic);
		initView();
		initData();
		bindListenner();

	}

	private void bindListenner() {
		weibo_content.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (TextUtils.isEmpty(s)) {
					bt_send.setEnabled(false);
					bt_send.setBackgroundResource(R.drawable.sendbt_enable);
				} else {
					bt_send.setEnabled(true);
					bt_send.setBackgroundResource(R.drawable.common_button_orange_disable);
				}
			}

			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		send_gv.setOnClickListener(this);
		img_del.setVisibility(View.GONE);
		weibowithpic_container.removeView(weibowithpic);

	}

	private void initData() {
		tv_cancle.setOnClickListener(this);
		bt_send.setOnClickListener(this);
		img_del.setOnClickListener(this);

	}

	private void initView() {
		mStatusesAPI = MainActivity.mainActivity.mStatusesAPI;
		tv_cancle = (TextView) findViewById(R.id.sendweibo_tv_cancalp);
		bt_send = (Button) findViewById(R.id.sendweio_bt_sendp);
		weibo_content = (EditText) findViewById(R.id.sendweibo_et_contentp);
		send_gv = (ImageView) findViewById(R.id.sendweibo_gv);
		weibowithpic = (ImageView) findViewById(R.id.weibowithpic);
		weibowithpic_container = (RelativeLayout) findViewById(R.id.weibowithpic_container);
		img_del = (ImageView) findViewById(R.id.weibo_del_pic);

	}

	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sendweibo_tv_cancalp:
			Intent intent = new Intent(SendPicWeiboActivity.this,
					MainActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.pop_enter, R.anim.pop_exit);
			SendPicWeiboActivity.this.finish();
			break;
		case R.id.sendweio_bt_sendp:
			String content = weibo_content.getText().toString().trim();
			if (bitmap != null) {
				mStatusesAPI.upload(content, bitmap, "0.0", "0.0",
						new RequestListener() {
							
							public void onWeiboException(WeiboException arg0) {
								// TODO Auto-generated method stub
								Toast.makeText(SendPicWeiboActivity.this,
										arg0.toString(), 0).show();
							}

							
							public void onComplete(String arg0) {
								Intent intent = new Intent(
										SendPicWeiboActivity.this,
										MainActivity.class);
								startActivity(intent);
								overridePendingTransition(R.anim.pop_enter,
										R.anim.pop_exit);
								SendPicWeiboActivity.this.finish();
							}
						});
			} else {
				if (!TextUtils.isEmpty(content)) {
					mStatusesAPI.update(content, "0.0", "0.0",
							new RequestListener() {
								public void onWeiboException(WeiboException arg0) {
									// TODO Auto-generated method stub
									Toast.makeText(SendPicWeiboActivity.this,
											arg0.toString(), 0).show();
								}

								
								public void onComplete(String arg0) {
									Intent intent = new Intent(
											SendPicWeiboActivity.this,
											MainActivity.class);
									startActivity(intent);
									overridePendingTransition(R.anim.pop_enter,
											R.anim.pop_exit);
									SendPicWeiboActivity.this.finish();
								}
							});
				}
			}
			break;
		case R.id.sendweibo_gv:

			Intent intentp = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intentp, 1);
			break;
		case R.id.weibo_del_pic:
			if (weibowithpic != null) {
				weibowithpic_container.removeView(weibowithpic);
				img_del.setVisibility(View.GONE);
				bitmap = null;
			}
			break;
		default:
			break;
		}
	}

	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
			Uri uri = data.getData();
			String[] filePath = { MediaStore.Images.Media.DATA };
			Cursor cursor = getContentResolver().query(uri, filePath, null,
					null, null);
			cursor.moveToFirst();
			int index = cursor.getColumnIndex(filePath[0]);
			String path = cursor.getString(index);
			cursor.close();
			weibowithpic_container.addView(weibowithpic);
			bitmap = BitmapFactory.decodeFile(path);
			img_del.setVisibility(View.VISIBLE);
			weibowithpic.setImageBitmap(bitmap);
			// mList.add(bitmap);
			// adapter.notifyDataSetChanged();
		}
	}
}
