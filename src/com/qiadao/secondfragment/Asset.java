package com.qiadao.secondfragment;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.qiadao.iknow.R;
import com.qiadao.tool.HttpUtil;
import com.qiadao.tool.PreferenceUtils;
import com.qiadao.tool.Resource;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Asset extends Activity {
	private TextView myasset;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a1);
		myasset=(TextView) findViewById(R.id.myasset);
		get();
		
	
		
	}
	
	private void get(){
		HttpUtil.get(Resource.url+"/IKnow/UserAction/"+PreferenceUtils.getPrefString(getApplicationContext(), "userid", ""),new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				try {
					myasset.setText(response.getDouble("money")+"元");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
			
			
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		get();
	}
}
