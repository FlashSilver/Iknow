package com.qiadao.secondfragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.qiadao.adapter.ExpensiveAdapter;
import com.qiadao.bean.ExpensiveBean;
import com.qiadao.iknow.R;
import com.qiadao.tool.HttpUtil;
import com.qiadao.tool.Resource;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class ExpenseFragment extends Activity {
//	ListView expense;
//	List<ExpensiveBean> expensive;
//	private TextView loading;
//	private ExpensiveAdapter expenseadapter;
//	private com.qiadao.tool.RemoteImageHelper remoteimagehelper;
//	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.expense);
//		loading=(TextView) findViewById(R.id.loading);
//		expense= (ListView)findViewById(R.id.expense);
//		expensive = new ArrayList<ExpensiveBean>();
//		loading.setVisibility(0);
//		ini();
	}
//	Handler handler=new Handler(){
//		public void handleMessage(android.os.Message msg) {
//			String value=(String) msg.obj;
//			try {
//				JSONArray valuearray=new JSONArray(value);
//				for (int i = 0; i < valuearray.length(); i++) {
//					JSONObject json=(JSONObject) valuearray.get(i);
//					expensive.add(new ExpensiveBean(json.getJSONObject("presenter").getString("username"),json.optString("questiontitle"),json.getJSONObject("presenter").optString("avater")));
//				}
//				expenseadapter = new ExpensiveAdapter(getApplicationContext(),expensive);
//				expense.setAdapter(expenseadapter);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		};
//	};
//
//	
//
//	
//	public void ini() {
//		new Thread() {
//			public void run() {
//				String value = HttpUtil.get(Resource.url+"/IKnow/QuestionAction/Status");	
//				Message msg=new Message();
//				msg.obj=value;
//				handler.sendMessage(msg);
//			};			
//		}.start();
//	}

}
