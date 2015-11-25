package com.qiadao.secondfragment;

import cn.sharesdk.demo.tpl.LoginActivity;

import com.qiadao.iknow.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts.Data;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class AcountManage extends Activity implements OnClickListener {
	private Button register;
	private Button markmanage;
	private Button out;
	public static final int REQUSET = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.account_manage);
		Button back = (Button) findViewById(R.id.back);
		markmanage = (Button) findViewById(R.id.markmanage);
		markmanage.setOnClickListener(this);
		register = (Button) findViewById(R.id.register);
		register.setOnClickListener(this);
		out = (Button) findViewById(R.id.out);
		out.setOnClickListener(this);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AcountManage.this.finish();
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.markmanage:
			Intent intent=new Intent(this,MarkFragment.class);
			startActivityForResult(intent, 1);
			break;
		case R.id.register:
		    Intent intents =  new Intent();
            intents.setClass(AcountManage.this,  LoginActivity.class);
            startActivityForResult(intents,1);//REQUESTCODE定义一个整型做为请求对象标识
		}
	}
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		//Bundle bundle = data.getStringExtra("");
		String result  =data.getStringExtra("userinfor");
		//Log.v("sadddddddddddd", result);
	//	Toast.makeText(getApplicationContext(), result,1).show();
	}
	
}
