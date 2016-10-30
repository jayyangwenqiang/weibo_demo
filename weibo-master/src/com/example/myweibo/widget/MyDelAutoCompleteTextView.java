package com.example.myweibo.widget;

import com.example.myweibo.R;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AutoCompleteTextView;

public class MyDelAutoCompleteTextView extends AutoCompleteTextView implements
		OnFocusChangeListener, TextWatcher {

	private Drawable mClearDrawable;
	private boolean hasFoucs;

	public MyDelAutoCompleteTextView(Context context) {
		this(context, null);
		init();
	}

	public MyDelAutoCompleteTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MyDelAutoCompleteTextView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		mClearDrawable = getResources().getDrawable(R.drawable.pic_delete);

		mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(),
				mClearDrawable.getIntrinsicHeight());
		// 默认设置隐藏图标
		setClearIconVisible(false);
		// 设置焦点改变的监听
		setOnFocusChangeListener(this);
		// 设置输入框里面内容发生改变的监听
		addTextChangedListener(this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (getCompoundDrawables()[2] != null) {
				int x = (int) event.getX();
				int y = (int) event.getY();
				Rect rect = getCompoundDrawables()[2].getBounds();
				int height = rect.height();
				int distance = (getHeight() - height) / 2;
				boolean isInnerWidth = x > (getWidth() - getTotalPaddingRight())
						&& x < (getWidth() - getPaddingRight());
				boolean isInnerHeight = y > distance && y < (distance + height);
				if (isInnerWidth && isInnerHeight) {
					this.setText("");
				}
			}
		}
		return super.onTouchEvent(event);
	}

	/**
	 * 当EditText焦点发生变化的时候， 输入长度为零，隐藏删除图标，否则，显示删除图标
	 */
	public void onFocusChange(View v, boolean hasFocus) {
		this.hasFoucs = hasFocus;
		if (hasFocus) {
			setClearIconVisible(getText().length() > 0);
		} else {
			setClearIconVisible(false);
		}
	}

	protected void setClearIconVisible(boolean visible) {
		Drawable right = visible ? mClearDrawable : null;
		setCompoundDrawables(getCompoundDrawables()[0],
				getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
	}

	public void onTextChanged(CharSequence s, int start, int count, int after) {
		if (hasFoucs) {
			setClearIconVisible(s.length() > 0);
		}
	}

	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	public void afterTextChanged(Editable s) {

	}

}
