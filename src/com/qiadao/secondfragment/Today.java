package com.qiadao.secondfragment;

import java.text.DecimalFormat;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.qiadao.iknow.R;
import com.qiadao.tool.HttpUtil;
import com.qiadao.tool.PreferenceUtils;
import com.qiadao.tool.Resource;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Today extends Activity {
	private TextView money;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a2);
		money=(TextView) findViewById(R.id.textView2);
		get();
	}
	@Override
	protected void onResume() {
		super.onResume();
		get();
	}
	private void get(){
		HttpUtil.get(Resource.url+"/IKnow/QuestionUserAction/Count?userid="+PreferenceUtils.getPrefString(getApplicationContext(), "userid", "")+"&sign=day",new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(int statusCode, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, response);
				Log.v("adfadf",response.toString());
				DecimalFormat df = new DecimalFormat("0.0");
				try {
					if(response.getDouble("result")+""==null){
						money.setText(df.format(0.0)+""+"元");
					}else{
						money.setText(df.format(response.optDouble("result"))+""+"元");
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}
