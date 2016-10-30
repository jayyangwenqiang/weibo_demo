package com.example.myweibo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout extends ViewGroup {

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int lineWidth = 0;//记录每一个子view的宽度
        int lineHeight = 0;//记录每一个子view的高度
        int width = 0;//记录当前容器的宽度
        int height = 0;//记录当前容器的高度
        int count = getChildCount();//子view的个数
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                View view = getChildAt(i);
                if (view != null) {
                    measureChild(view, widthMeasureSpec, heightMeasureSpec);//子view的宽度和高度
                    MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
                    //每个子view的宽度
                    int viewWidth = view.getMeasuredWidth() + params.leftMargin + params.rightMargin;
                    //每个子view的高度
                    int viewHeight = view.getMeasuredHeight() + params.topMargin + params.bottomMargin;
                    //前面的宽度+当前view的宽度
					if (lineWidth + viewWidth > widthSize) {
                        height += lineHeight;  //换行  记录容器的高度
                        width = Math.max(lineWidth, width);//记录每一行的最大宽度 把它设置成父view的宽度
                        lineHeight = viewHeight;//换行的时候当前的view的高度 就是每一行初始化的高度

                    } else {//小于一行
                        lineWidth += viewWidth;//每个子view 的宽度的累加
                        lineHeight = Math.max(lineHeight, viewWidth);//去一行中高度最大的座位一行的高度

                    }
                    if (i == count - 1) {
                        height += lineHeight;
                        width = Math.max(lineWidth, width);
                    }
                }
            }
        }
        //设置容器的宽和高
         setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? widthSize
                : width, (heightMode == MeasureSpec.EXACTLY) ? heightSize
                : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int top = 0;//记录view的向上的坐标
        int left = 0;//记录view的向左坐标
        int lineWidth = 0;//
        int lineHeight = 0;
        int count = getChildCount();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                View view = getChildAt(i);
                if (view != null) {
                    MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
                    int viewWidth = view.getMeasuredWidth() + params.leftMargin + params.rightMargin;
                    int viewHeight = view.getMeasuredHeight() + params.topMargin + params.bottomMargin;
                    if (lineWidth + viewWidth > getMeasuredWidth()) {
                        //换行
                        left = 0;
                        top += lineHeight;
                        lineWidth = viewWidth;
                        lineHeight=viewHeight;
                    }else{//不换行
                        lineWidth+=viewWidth;
                        lineHeight=Math.max(lineHeight,viewHeight);

                    }
                    int newL=left+params.leftMargin;
                    int newT=top+params.topMargin;
                    int newR=newL+view.getMeasuredWidth();
                    int newB=newT+view.getMeasuredHeight();
                    view.layout(newL,newT,newR,newB);
                    left+=viewWidth;
                }
            }
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return  new MarginLayoutParams(getContext(),attrs);
    }
}