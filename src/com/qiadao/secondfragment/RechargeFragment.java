package com.qiadao.secondfragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pingplusplus.libone.PayActivity;
import com.qiadao.iknow.R;
import com.qiadao.tool.PreferenceUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.provider.Settings.System;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class RechargeFragment extends Activity implements OnClickListener {
	private Button recharge;
	public static final String URL = "http://115.29.205.108:8080/IKnow/PingXXAction/Pay";
	private RadioGroup chooseamount;
	private RadioButton addfive;
	private RadioButton addtwenty;
	private RadioButton addfifty;
	private RadioButton other;
	private EditText editother;
	private int amount = 0;
	private Boolean istext=true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.recharge);
		Button back = (Button) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RechargeFragment.this.finish();
			}
		});
		recharge = (Button) findViewById(R.id.recharge);
		
		recharge.setOnClickListener(this);
		PayActivity.SHOW_CHANNEL_WECHAT = true;
		PayActivity.SHOW_CHANNEL_UPMP = true;
		PayActivity.SHOW_CHANNEL_BFB = true;
		PayActivity.SHOW_CHANNEL_ALIPAY = true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PayActivity.PAYACTIVITY_REQUEST_CODE) {
			if (resultCode == PayActivity.PAYACTIVITY_RESULT_CODE) {
				Toast.makeText(this, data.getExtras().getString("result"),
						Toast.LENGTH_LONG).show();
			} else if (resultCode == Activity.RESULT_CANCELED) {
				// pressed back
				Toast.makeText(this, data.getExtras().getString("result"),
						Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		setchoose();
		if(!istext){
			amount = Integer.parseInt(editother.getText().toString());
		}
		
		String orderNo = PreferenceUtils.getPrefString(getApplicationContext(),"userid", "");

		JSONArray billList = new JSONArray();
		// �����˵�json����
		JSONObject bill = new JSONObject();
		JSONObject displayItem = new JSONObject();
		try {
			displayItem.put("name", "��Ʒ");
			displayItem.put("contents", billList);
			JSONArray display = new JSONArray();
			display.put(displayItem);
			bill.put("order_no", orderNo);
			bill.put("amount", amount*100);
			bill.put("display", display);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		// ����֧��
		PayActivity.CallPayActivity(this, bill.toString(), URL);
	}

	private void setchoose() {
		addfive = (RadioButton) findViewById(R.id.addfive);
		addtwenty = (RadioButton) findViewById(R.id.addtwenty);
		addfifty = (RadioButton) findViewById(R.id.addfifty);
		other=(RadioButton) findViewById(R.id.other);
		editother = (EditText) findViewById(R.id.editohter);
		chooseamount=(RadioGroup) findViewById(R.id.chooseamount);
		chooseamount.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.addfive:
					amount = 5;
					break;
				case R.id.addtwenty:
					amount = 20;
					break;
				case R.id.addfifty:
					amount = 50;
					break;
				case R.id.other:
					istext=false;
					
					break;
				default:
					break;
				}
				
			
			}
		});

		}
	}
