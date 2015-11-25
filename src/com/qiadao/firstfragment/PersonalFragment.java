package com.qiadao.firstfragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import cn.sharesdk.demo.tpl.LoginActivity;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tpl.OnLoginListener;
import cn.sharesdk.tpl.UserInfo;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiadao.iknow.MainActivity;
import com.qiadao.iknow.R;
import com.qiadao.secondfragment.AcountManage;
import com.qiadao.secondfragment.Asset;
import com.qiadao.secondfragment.HelpFragment;
import com.qiadao.secondfragment.Login;
import com.qiadao.secondfragment.MarkFragment;
import com.qiadao.secondfragment.Money;
import com.qiadao.secondfragment.Month;
import com.qiadao.secondfragment.RechargeFragment;
import com.qiadao.secondfragment.Today;
import com.qiadao.secondfragment.Week;
import com.qiadao.tool.PreferenceUtils;
import com.qiadao.tool.QQLonginUtil;
import com.qiadao.tool.RoundImageView;
import com.tencent.connect.auth.QQAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import android.app.Activity;
import android.app.Dialog;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.preference.Preference;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class PersonalFragment extends Activity implements OnClickListener, Callback {
	/** 淇敼鐢ㄦ埛淇℃伅dialog,濡傜敤鎴蜂俊鎭紝澶囨敞淇℃伅 */
	private enum ChangeUserType {
		USER_NAME, USER_NOTE
	};

	/** 鍔犺浇瀵硅瘽妗� */
	private static final int SHOW_PROGRESS_DIALOG = 1;
	/** 鍔犺浇鐢ㄦ埛icon */
	private static final int LOAD_USER_ICON = 2;
	/** Toast 鎻愬崌 */
	private static final int MSG_SHOW_TOAST = 3;
	/** 鎵撳紑鐩稿唽锛屽苟鎴浘 */
	private static final int INTENT_ACTION_PICTURE = 0;
	/** 鎵撳紑鐩告満鐓х浉 */
	private static final int INTENT_ACTION_CAREMA = 1;
	/** 鐓х浉鍚庯紝鎴浘 */
	private static final int INTENT_ACTION_CROP = 2;
	/** 鍥剧墖鍚嶅瓧 */
	private static final String PICTURE_NAME = "userIcon.jpg";

	private OnLoginListener signupListener;
	private ImageView ivUserIcon;
	private TextView tvUserName, tvUserGender, tvUserNote, tvEnsure;
	private Platform platform;

	public void setOnLoginListener(OnLoginListener l) {
		this.signupListener = l;
	}

	private String picturePath;
	private UserInfo userInfo = new UserInfo();


	private Button manage;
	private Button addmoney;
	private Button help;

	private TextView nickname;
	private RoundImageView userhead;

	private ViewPager viewPager;
	private ArrayList<View> pageviews;
	private ImageView imageView;
	private ImageView[] imageViews;
	private ViewGroup main;
	private ViewGroup group;
	private View viewsss;
	private String name;
	private String avatar;
	private LocalActivityManager manager;
	private List<View> list;
	public static Activity PersonalFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.personal_content1);
		PersonalFragment=this;
		LayoutInflater inflate = LayoutInflater.from(this);
		// RelativeLayout rela=(RelativeLayout) inflate.inflate(R.id.relativeLayout1, null);
		nickname = (TextView) findViewById(R.id.nickname);
		userhead = (RoundImageView) findViewById(R.id.userhead);

		manage = (Button) findViewById(R.id.manage);
		addmoney = (Button) findViewById(R.id.addmoney);
		help = (Button) findViewById(R.id.help);

		//Toast.makeText(getApplicationContext(), PreferenceUtils.getPrefString(this, "nickname", ""), 1).show();
		nickname.setText(PreferenceUtils.getPrefString(this, "nickname", ""));
		ImageLoader.getInstance().displayImage(PreferenceUtils.getPrefString(this, "avatar", ""), userhead);

		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);
		list = new ArrayList<View>();
		Intent intent = new Intent(PersonalFragment.this, Asset.class);
		list.add(getView("ImageActivity1", intent));

		intent = new Intent(PersonalFragment.this, Today.class);
		getView("ImageActivity2", intent);
		list.add(getView("ImageActivity2", intent));

		intent = new Intent(PersonalFragment.this, Week.class);
		getView("ImageActivity3", intent);
		list.add(getView("ImageActivity3", intent));

		intent = new Intent(PersonalFragment.this, Month.class);
		getView("ImageActivity4", intent);
		list.add(getView("ImageActivity4", intent));

		imageViews = new ImageView[list.size()];

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.group);

		viewPager = (ViewPager) findViewById(R.id.guidePages);

		for (int i = 0; i < list.size(); i++) {
			imageViews[i] = new ImageView(this);
			imageViews[i].setLayoutParams(new LayoutParams(20, 20));
			if (i == 0) {
				imageViews[i].setBackgroundResource(R.drawable.dot_white);
			} else {
				imageViews[i].setBackgroundResource(R.drawable.dot_red);
			}
			linearLayout.addView(imageViews[i]);
		}
		
		viewPager.setAdapter(new MyAdapter());
		viewPager.setOnPageChangeListener(new MyListener());

	}

@Override
protected void onResume() {
	super.onResume();
	nickname = (TextView) findViewById(R.id.nickname);
	userhead = (RoundImageView) findViewById(R.id.userhead);
	nickname.setText(PreferenceUtils.getPrefString(this, "nickname", ""));
	ImageLoader.getInstance().displayImage(PreferenceUtils.getPrefString(this, "avatar", ""), userhead);
	viewPager.setOnPageChangeListener(new MyListener());
	if (PreferenceUtils.getPrefString(getApplicationContext(), "userid","")=="") {
		Intent intent=new Intent(PersonalFragment.this,LoginActivity.class);
		startActivity(intent);
		PersonalFragment.this.finish();
	} else {
	}
}
	// public void onClickAccute(View v){
	// Intent intent=new Intent(this,AcountManage.class);
	// startActivity(intent);
	// }
	//


	public void setPlatform(String platName) {
		platform = ShareSDK.getPlatform(platName);
	}

	public void tomanage(View target) {
		PreferenceUtils.setPrefString(getApplicationContext(), "userid", "");
		Intent intent = new Intent(PersonalFragment.this, MainActivity.class);
		startActivity(intent);
	}

	public void toaddmoney(View target) {
		Intent intent = new Intent(this, Money.class);
		startActivity(intent);
	}

	public void tohelp(View target) {
		Intent intent = new Intent(this, HelpFragment.class);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}

	private View getView(String id, Intent intent) {
		return manager.startActivity(id, intent).getDecorView();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			this.finish();
			// mHandler.removeCallbacks(mRunnable);
		default:
			break;
		}

		return super.onKeyDown(keyCode, event);
	}

	class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return super.getItemPosition(object);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			// TODO Auto-generated method stub
			((ViewPager) arg0).removeView(list.get(arg1 % list.size()));
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			// TODO Auto-generated method stub
			try {
				// ((ViewPager) arg0).addView(list.get(arg1),0);
				((ViewPager) arg0).addView((View) list.get(arg1 % list.size()), 0);
			} catch (Exception e) {
				// TODO: handle exception
			}

			return list.get(arg1 % list.size());

		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public Parcelable saveState() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void finishUpdate(View arg0) {
			// TODO Auto-generated method stub

		}
	}

	class MyListener implements OnPageChangeListener {

		// 当滑动状态改变时调用
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			// arg0=arg0%list.size();

		}

		// 当当前页面被滑动时调用
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		// 当新的页面被选中时调用
		@Override
		public void onPageSelected(int arg0) {
			arg0 = arg0 % list.size();
			for (int i = 0; i < imageViews.length; i++) {
				imageViews[arg0].setBackgroundResource(R.drawable.dot_white);
				if (arg0 != i) {
					imageViews[i].setBackgroundResource(R.drawable.dot_red);
				}
			}

			System.out.println("当前是第" + arg0 + "页");
		}

	}
}
