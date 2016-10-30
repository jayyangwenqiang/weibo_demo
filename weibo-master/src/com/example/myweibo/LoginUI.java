package com.example.myweibo;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.myweibo.utils.AccessTokenKeeper;
import com.example.myweibo.utils.Contacts;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.widget.LoginButton;

public class LoginUI extends Activity {
	private ProgressDialog mProgressDialog;
	private AuthInfo mAuthInfo;
	private SsoHandler mSsoHandler;
	private Oauth2AccessToken mAccessToken;
	private LoginButton mLoginButton;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		mLoginButton = (LoginButton) findViewById(R.id.loginButton);
		mAuthInfo = new AuthInfo(this, Contacts.APPKEY, Contacts.REDIRECT_URL,
				Contacts.SCOPE);
		mSsoHandler = new SsoHandler(this, mAuthInfo);
		mProgressDialog = new ProgressDialog(LoginUI.this);
		mProgressDialog.setMessage("正在跳转");
		mProgressDialog.setCancelable(true);
		mLoginButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mProgressDialog.show();
				mSsoHandler.authorize(new AuthListener());
			}
		});
	}

	private class AuthListener implements WeiboAuthListener {
		
		public void onComplete(Bundle values) {
			Oauth2AccessToken accessToken = Oauth2AccessToken
					.parseAccessToken(values);
			if (accessToken != null && accessToken.isSessionValid()) {
				String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
						.format(new java.util.Date(accessToken.getExpiresTime()));
				/*
				 * String format =
				 * getString(R.string.weibosdk_demo_token_to_string_format_1);
				 * mTokenView.setText(String.format(format,
				 * accessToken.getToken(), date));
				 */
				Toast.makeText(getApplicationContext(),
						date + accessToken.getToken(), 1).show();
				AccessTokenKeeper.writeAccessToken(getApplicationContext(),
						accessToken);
				Intent intent = new Intent(LoginUI.this, MainActivity.class);
				intent.putExtra("token", accessToken.getToken());
				startActivity(intent);
				mProgressDialog.dismiss();
				finish();
			}
		}

		
		public void onWeiboException(WeiboException e) {
			Toast.makeText(LoginUI.this, e.getMessage(), Toast.LENGTH_SHORT)
					.show();
		}

		
		public void onCancel() {
			Toast.makeText(LoginUI.this, "取消", Toast.LENGTH_SHORT).show();
		}
	}

	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

}
