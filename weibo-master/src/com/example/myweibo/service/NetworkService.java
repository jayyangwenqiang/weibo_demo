package com.example.myweibo.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.widget.Toast;

public class NetworkService extends Service {
	private ConnectivityManager connectivityManager;
	private NetworkInfo networkInfo;
	public IBinder onBind(Intent intent) {
		return null;
	}
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
				connectivityManager = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				networkInfo = connectivityManager.getActiveNetworkInfo();
				if (networkInfo != null) {
					// 网络名称
					String netName = networkInfo.getTypeName();
					Toast.makeText(context, netName, 0).show();
				} else {
					Intent noNetWork = new Intent("noNetWork");
					sendBroadcast(noNetWork);
				}

			}
		}

	};

	public void onCreate() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		// 服务被创建 注册广播
		registerReceiver(receiver, filter);
	};

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (receiver != null) {
			unregisterReceiver(receiver);
			receiver = null;
		}
	}

}
