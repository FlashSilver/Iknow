package com.qiadao.iknow;

import java.io.File;

import android.graphics.Bitmap.CompressFormat;
import cn.jpush.android.api.JPushInterface;

import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.qiadao.tool.FileUtils;
import com.umeng.fb.push.FeedbackPush;

public class Application extends android.app.Application {
	//private File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(), "Iknow/Image");

	@Override
	public void onCreate() {
		super.onCreate();
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);
		
		
		FileUtils.getInstance(getApplicationContext());

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).memoryCacheExtraOptions(480, 800) // max width, max height���������ÿ�������ļ�����󳤿�
				.threadPoolSize(6)// �̳߳��ڼ��ص�����
				.threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory().memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/�����ͨ���Լ����ڴ滺��ʵ��
				.memoryCacheSize(2 * 1024 * 1024).discCacheSize(50 * 1024 * 1024).discCacheFileNameGenerator(new Md5FileNameGenerator())// �������ʱ���URI�����MD5 ����
				.tasksProcessingOrder(QueueProcessingType.LIFO).discCacheFileCount(100) // ������ļ�����
				//.discCache((DiskCache) new UnlimitedDiscCache(cacheDir))// �Զ��建��·��
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple()).imageDownloader(new BaseImageDownloader(getApplicationContext(), 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)��ʱʱ��
				.writeDebugLogs() // Remove for release app
				.build();// ��ʼ���� 
		// Initialize ImageLoader with configuration.

		ImageLoader.getInstance().init(config);// ȫ�ֳ�ʼ��������
		  FeedbackPush.getInstance(this).init(false);
		
	}
	
	

}
