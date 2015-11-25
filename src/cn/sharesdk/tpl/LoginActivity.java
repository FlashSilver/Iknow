package cn.sharesdk.tpl;

import java.util.HashMap;

import com.qiadao.iknow.R;
import com.qiadao.secondfragment.Login;

import cn.sharesdk.tpl.OnLoginListener;
import cn.sharesdk.tpl.UserInfo;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;


public class LoginActivity extends Activity {
	private static String APPKEY = "27fe7909f8e8";
	private static String APPSECRET = "3c5264e7e05b8860a9b98b34506cfa6e";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Builder builder = new Builder(LoginActivity.this);
		showDemo();
		//脚后跟经过好几个
		finish();
		builder.create().show();
		System.out.println("ok");
	}
	
	private void showDemo() {
		Login tpl = new Login();
		tpl.setOnLoginListener(new OnLoginListener() {
			public boolean onSignin(String platform, HashMap<String, Object> res) {
				return true;
			}
			public boolean onSignUp(UserInfo info) {
				return true;
			}
		});
		tpl.show(this);
	}
}
