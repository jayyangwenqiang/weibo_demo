package com.example.myweibo.fragment;

import android.app.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
	Activity mActivity;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mActivity = getActivity();
		return initView();
	}

	/**
	 * 子类的view由继承的子view自己实现
	 * 
	 * @return
	 */
	public abstract View initView();

	public Context getContext() {
		return mActivity;
	}
}
