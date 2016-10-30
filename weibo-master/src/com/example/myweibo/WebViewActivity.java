package com.example.myweibo;

import android.R.raw;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.RotateAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class WebViewActivity extends Activity {
	WebView mWebView;
	ImageView img;
	String url;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.webview_layout);
		mWebView = (WebView) findViewById(R.id.wv);
		img = (ImageView) findViewById(R.id.web_loading);
		Intent intent = getIntent();
		url = intent.getStringExtra("weburl");

		WebSettings set = mWebView.getSettings();
		set.setJavaScriptEnabled(true);
		set.setUseWideViewPort(true);
		set.setBuiltInZoomControls(true);

		mWebView.loadUrl(url);

		mWebView.setWebViewClient(new WebViewClient() {
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				RotateAnimation ra = new RotateAnimation(0, 360);
				ra.setDuration(3000);
				ra.setRepeatCount(-1);
				img.startAnimation(ra);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				img.setVisibility(view.GONE);
			}
		});
	}
}
