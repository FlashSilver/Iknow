package com.qiadao.secondfragment;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.FakeActivity;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tpl.OnLoginListener;
import cn.sharesdk.wechat.friends.Wechat;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.qiadao.firstfragment.PersonalFragment;
import com.qiadao.iknow.R;
import com.qiadao.thirdfragment.SignupPage;
import com.qiadao.tool.HttpUtil;
import com.qiadao.tool.PreferenceUtils;
import com.qiadao.tool.QQLonginUtil;
import com.qiadao.tool.Resource;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends FakeActivity implements OnClickListener, Callback, PlatformActionListener {
	private static final String TAG = PersonalFragment.class.getName();
	public static String mAppid;
	private Button qqlogin;
	private static final int MSG_AUTH_CANCEL = 2;
	private static final int MSG_AUTH_ERROR = 3;
	private static final int MSG_AUTH_COMPLETE = 4;
	public static final int RESULT_OK = -1;

	private String smssdkAppkey;
	private String smssdkAppSecret;
	private OnLoginListener signupListener;
	private Handler handler;
	private static final String CONSUMER_KEY = "485181798";
	private Intent it = null;
	private TextView mText;
	public static String username;
	public static String avatar;
	private String registerid;
	private SharedPreferences.Editor editor;
	private Platform wechat;
	private Platform sina;
	private ProgressBar load;
	
	public void setOnLoginListener(OnLoginListener l) {
		this.signupListener = l;
	}

	public void onCreate() {
		// ��ʼ��ui
		handler = new Handler(this);
		activity.setContentView(R.layout.tpl_login_page);
		load=(ProgressBar) activity.findViewById(R.id.load);
		(activity.findViewById(R.id.tvWeixin)).setOnClickListener(this);
		(activity.findViewById(R.id.tvWeibo)).setOnClickListener(this);
		
	}


	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvWeixin: {
			load.setVisibility(View.VISIBLE);
			 wechat = ShareSDK.getPlatform(getContext(), Wechat.NAME);
			wechat.setPlatformActionListener(this);
			wechat.SSOSetting(false);
			authorize(wechat);

		}
			break;
		case R.id.tvWeibo: {
			load.setVisibility(View.VISIBLE);
			sina = ShareSDK.getPlatform(SinaWeibo.NAME);
			sina.setPlatformActionListener(this);
			sina.SSOSetting(false);
			authorize(sina);

		}
			break;
		}
		
		
	}
	private void authorize(Platform plat) {
		if (plat == null) {
			return;
		}

		plat.setPlatformActionListener(this);
		plat.showUser(null);
	}

	public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
		if (action == Platform.ACTION_USER_INFOR) {
			Message msg = new Message();
			msg.what = MSG_AUTH_COMPLETE;
			msg.obj = new Object[] { platform, res };
			handler.sendMessage(msg);
		}
	}

	public void onError(Platform platform, int action, Throwable t) {
		if (action == Platform.ACTION_USER_INFOR) {
			handler.sendEmptyMessage(MSG_AUTH_ERROR);
		}
		t.printStackTrace();
	}

	public void onCancel(Platform platform, int action) {
		if (action == Platform.ACTION_USER_INFOR) {
			handler.sendEmptyMessage(MSG_AUTH_CANCEL);
		}
	}

	@SuppressWarnings("unchecked")
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case MSG_AUTH_CANCEL: {
			load.setVisibility(View.GONE);
			// ȡ����Ȩ
			Toast.makeText(activity, R.string.auth_cancel, Toast.LENGTH_SHORT).show();
		}
			break;
		case MSG_AUTH_ERROR: {
			load.setVisibility(View.GONE);
			// ��Ȩʧ��
			Toast.makeText(activity, R.string.auth_error, Toast.LENGTH_SHORT).show();
		}
			break;
		case MSG_AUTH_COMPLETE: {
			load.setVisibility(View.GONE);
			// ��Ȩ�ɹ�
			Toast.makeText(activity, R.string.auth_complete, Toast.LENGTH_SHORT).show();
			Object[] objs = (Object[]) msg.obj;

			Platform platform = (Platform) objs[0];
			HashMap<String, Object> res = (HashMap<String, Object>) objs[1];
			username = platform.getDb().getUserName();
			avatar = platform.getDb().getUserIcon();
			registerid = JPushInterface.getRegistrationID(getContext());
			
			RequestParams params = new RequestParams();
			params.put("username", username);
			params.put("avatar", avatar);
			params.put("registerid", registerid);
			params.put("wbid", platform.getDb().getUserId());
			HttpUtil.post(Resource.url + "/IKnow/UserAction/login", params, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONObject response) {
					super.onSuccess(response);
					Log.v("response", response.toString());
					try {
						String money = response.getDouble("money") + "";
						PreferenceUtils.setPrefString(getContext(), "money", money);
						PreferenceUtils.setPrefString(getContext(), "userid", response.get("userid").toString());
						PreferenceUtils.setPrefString(getContext(), "registerid", registerid);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

			});
			PreferenceUtils.setPrefString(getContext(), "nickname", username);
			PreferenceUtils.setPrefString(getContext(), "avatar", avatar);

			if (signupListener != null && signupListener.onSignin(platform.getName(), res)) {
				activity.finish();	 			
				SignupPage signupPage = new SignupPage();
				signupPage.setOnLoginListener(signupListener);
				signupPage.setPlatform(platform.getName());
				signupPage.show(activity, null);
			}

		}
			break;
		}
		return false;
	}

	// void writeSharedPreferences(String username,String strPassword)
	// {
	// SharedPreferences user =getContext().getSharedPreferences("userInfo",0);
	// editor = user.edit();
	//
	// editor.putString("username", username);
	// editor.putString("avatar" ,avatar);
	// editor.commit();
	// }

	public void show(Context context) {
		initSDK(context);
		super.show(context, null);
	}

	private void initSDK(Context context) {

		ShareSDK.initSDK(context);

	}

}
