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
import android.widget.Toast;

public class Month extends Activity {
	private TextView monthmoney;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.a4);
	monthmoney=(TextView) findViewById(R.id.month);
	get();
}
@Override
	protected void onResume() {
		super.onResume();

		get();
	}

private void get(){
	HttpUtil.get(Resource.url+"/IKnow/QuestionUserAction/Count?userid="+PreferenceUtils.getPrefString(getApplicationContext(), "userid", "")+"&sign=month",new JsonHttpResponseHandler(){

		@Override
		public void onSuccess(int statusCode, JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, response);
			Log.v("adfadf",response.toString()+"sdsdafa");
			DecimalFormat df = new DecimalFormat("0.0");
			//PreferenceUtils.setPrefString(getApplicationContext(), "monthmoney", df.format(response.getDouble("result")));
			//Toast.makeText(getApplicationContext(), PreferenceUtils.getPrefString(getApplicationContext(), "monthmoney", ""), 1).show();
			//monthmoney.setText(PreferenceUtils.getPrefString(getApplicationContext(), "monthmoney", "0.0")+"元");
			monthmoney.setText(df.format(response.optDouble("result"))+"元");
		}
	});
}
}
