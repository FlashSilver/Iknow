package com.qiadao.secondfragment;

import java.text.DecimalFormat;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.qiadao.iknow.R;
import com.qiadao.tool.HttpUtil;
import com.qiadao.tool.PreferenceUtils;
import com.qiadao.tool.Resource;

public class Week extends Activity {
	private TextView weekmoney;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a3);
		weekmoney=(TextView) findViewById(R.id.textView2);
		
		get();
	}
	@Override
	protected void onResume() {
		super.onResume();
		get();
		Toast.makeText(getApplicationContext(),"afafewewe",1).show();
	}

private void get(){
	HttpUtil.get(Resource.url+"/IKnow/QuestionUserAction/Count?userid="+PreferenceUtils.getPrefString(getApplicationContext(), "userid", "")+"&sign=week",new JsonHttpResponseHandler(){
		@Override
		public void onSuccess(int statusCode, JSONObject response) {
			super.onSuccess(statusCode, response);
			Log.v("adfadf",response.toString());
			DecimalFormat df = new DecimalFormat("0.0");
			//				PreferenceUtils.setPrefString(getApplicationContext(), "monthmoney", df.format(response.getDouble("result")));
//				weekmoney.setText(PreferenceUtils.getPrefString(getApplicationContext(), "monthmoney", "0.0"));
			weekmoney.setText(df.format(response.optDouble("result"))+"元");
		}
	});
}
}
