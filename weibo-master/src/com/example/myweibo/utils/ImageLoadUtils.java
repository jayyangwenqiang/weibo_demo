package com.example.myweibo.utils;

import android.content.Context;
import android.widget.ImageView;

import com.example.myweibo.MainActivity;
import com.example.myweibo.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

public enum ImageLoadUtils {
	INSTANCE;
	/**
	 * ��׼����
	 */
	private DisplayImageOptions normalOptions;
	/**
	 * Բ������
	 */
	private DisplayImageOptions circleOptions;
	/**
	 * Բ��ͼƬ����
	 */
	private DisplayImageOptions roundedOptions;

	private BitmapDisplayer simpleBitmapDisplayer;
	private BitmapDisplayer roundedBitmapDisplayer;

	private int onLoadingImageResId;
	private int onEmptyImageResId;
	private int onFailedImageResId;

	/**
	 * ���췽�� ������ʼ�� ������ʽ ֻ���ʼ��һ�� ���ⲻ��Ҫ����Դ��֧
	 */
	private ImageLoadUtils() {
		// ��ʼ�� ȫ��Ĭ��ͼƬ
		onLoadingImageResId = R.drawable.loading;
		onEmptyImageResId = R.drawable.loading;
		onFailedImageResId = R.drawable.loading;

		simpleBitmapDisplayer = new SimpleBitmapDisplayer();
		normalOptions = getOption(onLoadingImageResId, onEmptyImageResId,
				onFailedImageResId, simpleBitmapDisplayer);

		/*
		 * circleBitmapDisplayer = new CircleBitmapDisplayer(); circleOptions =
		 * getOption(onLoadingImageResId, onEmptyImageResId, onFailedImageResId,
		 * circleBitmapDisplayer);
		 */

		// Բ��ͼƬ Բ�ǰ뾶dp
		int cornerRadiusDp = 10;
		// Բ�Ǵ�Сͨ�� dp2pxת�� ʹ�� ��ͬ�ֱ����豸�ϳ���һ����ʾЧ��
		roundedBitmapDisplayer = new RoundedBitmapDisplayer(dip2px(
				MainActivity.getContext(), cornerRadiusDp));
		roundedOptions = getOption(onLoadingImageResId, onEmptyImageResId,
				onFailedImageResId, roundedBitmapDisplayer);

	}

	/**
	 * �ع� ��ȡ����ͨ������Option����
	 * 
	 * @param onLoadingImageResId
	 * @param onEmptyImageResId
	 * @param onFailedImageResId
	 * @param bitmapDisplayer
	 *            normal ��Բ�Ρ�Բ�� bitmapDisplayer
	 * 
	 * @return
	 */
	private DisplayImageOptions getOption(int onLoadingImageResId,
			int onEmptyImageResId, int onFailedImageResId,
			BitmapDisplayer bitmapDisplayer) {
		return new DisplayImageOptions.Builder()
				.showImageOnLoading(onLoadingImageResId)
				.showImageForEmptyUri(onEmptyImageResId)
				.showImageOnFail(onFailedImageResId).cacheInMemory(true)
				.cacheOnDisc(true).considerExifParams(true)
				.displayer(bitmapDisplayer).build();
	}

	public void loadImageView(ImageView iv, String url) {
		ImageLoader.getInstance().displayImage(url, iv, normalOptions);
	}

	/*
	 * public void loadCircleImageView(ImageView iv, String url) {
	 * ImageLoader.getInstance().displayImage(url, iv, circleOptions); }
	 */

	public void loadRoundedImageView(ImageView iv, String url) {
		ImageLoader.getInstance().displayImage(url, iv, roundedOptions);
	}

	/**
	 * dip px ת�������� ��Բ�ǽ���ת�� ��ʵ�ֲ�ͬ�ֱ����豸�ϳ�����ͬЧ��
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue) {
		final float density = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * density + 0.5f);
	}

	/**
	 * ��ʼ������
	 * 
	 * @param context
	 */
	public void init(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(
				context);
		config.threadPriority(Thread.NORM_PRIORITY - 2);
		config.denyCacheImageMultipleSizesInMemory();
		config.discCacheFileNameGenerator(new Md5FileNameGenerator());
		config.discCacheSize(80 * 1024 * 1024); // 80 MiB
		config.tasksProcessingOrder(QueueProcessingType.LIFO);
		config.writeDebugLogs(); // Remove for release app

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config.build());
	}

}