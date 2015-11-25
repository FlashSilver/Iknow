package com.qiadao.iknow;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.demo.tpl.LoginActivity;

import com.alipay.mobilesecuritysdk.MainHandler;
import com.qiadao.firstfragment.LaunchFragment;
import com.qiadao.firstfragment.PersonalFragment;
import com.qiadao.firstfragment.MessageFragment;
import com.qiadao.firstfragment.SquareFragment;
import com.qiadao.secondfragment.ExtractFragment;
import com.qiadao.tool.ACache;
import com.qiadao.tool.FileUtils;
import com.qiadao.tool.PreferenceUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.Toast;


@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity implements OnCheckedChangeListener{
	private ViewPager mViewPager;
	private List<Fragment> mTabs = new ArrayList<Fragment>();
	private FragmentPagerAdapter mAdapter;
	private LaunchFragment launchfragment;
	private MessageFragment messagefragment;
	private SquareFragment squarefragment;
	private PersonalFragment personalfragment;
	private Button one;
	private Button two;
	private Button three;
	private Button four;
	private TabHost tabHost;
	private RadioGroup radioderGroup;
	public static Activity mainactivity;
	private Bitmap bitmap; 
	
	
	boolean isFirstIn = false;

	private static final int GO_HOME = 1000;
	private static final int GO_GUIDE = 1001;
	// 延迟3秒
	private static final long SPLASH_DELAY_MILLIS = 1000;

	private static final String SHAREDPREFERENCES_NAME = "first_pref";


	private List<Button> mTabIndicator = new ArrayList<Button>();
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_HOME:
				if (PreferenceUtils.getPrefString(getApplicationContext(), "userid","")=="") {
					Intent intent=new Intent(MainActivity.this,LoginActivity.class);
					startActivity(intent);
					MainActivity.this.finish();
				} else {
				}
				
				break;
			case GO_GUIDE:
				goGuide();
				break;
			}
			super.handleMessage(msg);
		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		tabHost=this.getTabHost();
		tabHost.addTab(tabHost.newTabSpec("1").setIndicator("1").setContent(new Intent(this,LaunchFragment.class)));
		tabHost.addTab(tabHost.newTabSpec("2").setIndicator("2").setContent(new Intent(this,SquareFragment.class)));
		tabHost.addTab(tabHost.newTabSpec("3").setIndicator("3").setContent(new Intent(this,MessageFragment.class)));
		tabHost.addTab(tabHost.newTabSpec("4").setIndicator("4").setContent(new Intent(this,PersonalFragment.class)));
		radioderGroup = (RadioGroup) findViewById(R.id.main_radio);
		radioderGroup.setOnCheckedChangeListener(this);
		radioderGroup.check(R.id.id_indicator_one);//Ĭ�ϵ�һ����ť
		
		ACache cache=ACache.get(getApplicationContext());		
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);  
		cache.put("logo", bitmap);
      
		//int resID = getResources().getIdentifier(imageName,"drawable",packageName);
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
		File SpicyDirectory = new File(FileUtils.fileUtils.getStorageDirectory());
		SpicyDirectory.mkdirs();
		String filename=FileUtils.fileUtils.getStorageDirectory()+"logo.JPEG";
		FileOutputStream out = null;
		try {
		out = new FileOutputStream(filename);
		Log.v("1222222222", filename);
		bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
		} catch (Exception e) {
		e.printStackTrace();
		} finally {
//		try {
//		out.flush();
//		} catch (IOException e) {
//		e.printStackTrace();
//		}
//		try {
//		out.close();
//		} catch (IOException e) {
//		e.printStackTrace();
//		}
		out=null;
		}

		
	}
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch(checkedId){
		case R.id.id_indicator_one:
			tabHost.setCurrentTabByTag("1");
			break;
		case R.id.id_indicator_two:
			tabHost.setCurrentTabByTag("2");
			break;
		case R.id.id_indicator_three:
			tabHost.setCurrentTabByTag("3");
			break;
		case R.id.id_indicator_four:     
			tabHost.setCurrentTabByTag("4");
			break;
		}		
		
	}
//	@Override
//	protected void onResume() {
//		super.onResume();
//		 ifnet();
//		if (PreferenceUtils.getPrefString(getApplicationContext(), "userid","")=="") {
//			Intent intent=new Intent(MainActivity.this,LoginActivity.class);
//			startActivity(intent);
//			this.finish();
//		}
//	}
//	
	private void init() {
		// 读取SharedPreferences中需要的数据       
		// 使用SharedPreferences来记录程序的使用次数
		SharedPreferences preferences = getSharedPreferences(
				SHAREDPREFERENCES_NAME, MODE_PRIVATE);

		// 取得相应的值，如果没有该值，说明还未写入，用true作为默认值
		isFirstIn = preferences.getBoolean("isFirstIn", true);

		// 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面
		if (!isFirstIn) {
			// 使用Handler的postDelayed方法，3秒后执行跳转到MainActivity
			mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
		} else {
			mHandler.sendEmptyMessageDelayed(GO_GUIDE, SPLASH_DELAY_MILLIS);
		}

	}
	
	private void goGuide() {
		Intent intent = new Intent(MainActivity.this, GuideActivity.class);
		MainActivity.this.startActivity(intent);
		MainActivity.this.finish();
	}
	
	private void ifnet(){
		ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);  
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();  
        if(networkInfo == null || !networkInfo.isAvailable())  
        {  
        	AlertDialog.Builder builder = new AlertDialog.Builder(
					MainActivity.this);
			builder.setMessage("当前网络不可用...");
			builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				}
			
			});			
			builder.create().show();   
        }  
	}
	
}	


	

