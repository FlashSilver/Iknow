package com.qiadao.secondfragment;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.qiadao.firstfragment.MessageFragment;
import com.qiadao.iknow.R;
import com.qiadao.tool.HttpUtil;
import com.qiadao.tool.PreferenceUtils;
import com.qiadao.tool.Resource;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ExtractFragment extends Activity {
	private Button handin;
	private EditText zhifubao;
	private EditText amount;
	private double price;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.extract);
		handin=(Button) findViewById(R.id.handin);
		zhifubao=(EditText) findViewById(R.id.zhifubao);
		amount=(EditText) findViewById(R.id.amount);
		
		handin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				price=Double.parseDouble(amount.getText().toString());
				RequestParams param=new RequestParams();
				param.put("userid", PreferenceUtils.getPrefString(getApplicationContext(), "userid", ""));
				param.put("alipayid", zhifubao.getText().toString());
				param.put("price",price+"");
				HttpUtil.post(Resource.url+"/IKnow/WthdrawDepositAction", param, new AsyncHttpResponseHandler(){

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							String content) {
						// TODO Auto-generated method stub
						super.onSuccess(statusCode, headers, content);
						AlertDialog.Builder builder = new AlertDialog.Builder(
								ExtractFragment.this);
						builder.setMessage("申请成功，系统将在48小时之内将钱打到您的支付宝上");
						builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
							ExtractFragment.this.finish();
							}
						
						});
						
						
						builder.create().show(); 
				}
					
				});
			}
		});
	}
	
	
}
