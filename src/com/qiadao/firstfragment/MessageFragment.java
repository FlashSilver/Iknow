package com.qiadao.firstfragment;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.qiadao.adapter.ChatAdapter;
import com.qiadao.bean.ChartBean;
import com.qiadao.chat.MainActivity;
import com.qiadao.iknow.MyReceiver;
import com.qiadao.iknow.R;
import com.qiadao.secondfragment.AnswerFragment;
import com.qiadao.secondfragment.MessageContent;
import com.qiadao.tool.ACache;
import com.qiadao.tool.PreferenceUtils;
import com.qiadao.tool.Resource;

import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MessageFragment extends Activity {
	private RelativeLayout ilaunch;
	private RelativeLayout ijoin;
	public ChatAdapter chatadapter;
	public static ListView converlist;
	public static List<ChartBean> chart = new ArrayList<ChartBean>();
	String reciever;
	List<String> answererid = new ArrayList<String>();
	private Boolean a = true;
	public static MessageFragment activity;
	private List<ChartBean> templist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_content);
		activity = this;
		ilaunch = (RelativeLayout) findViewById(R.id.ilaunch);
		ijoin = (RelativeLayout) findViewById(R.id.ijoin);
		converlist = (ListView) findViewById(R.id.converlist);
		ACache mcache = ACache.get(getApplicationContext());
		if (mcache.getAsJSONArray("messagearray") != null) {
			chart = JSON.parseArray(mcache.getAsJSONArray("messagearray").toString(), ChartBean.class);
			templist = new ArrayList<ChartBean>();
			for (ChartBean chartBean : chart) {
				boolean sign = false;
				for (ChartBean bean : templist) {
					if (bean.getAnswererid().equals(chartBean.getAnswererid())) {
						sign = true;
					}
				}
				if (!sign) {
					templist.add(chartBean);
				}
			}

			chatadapter = new ChatAdapter(getApplicationContext(), templist);
			converlist.setAdapter(chatadapter);
		}
		ilaunch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MessageFragment.this, MessageContent.class);
				intent.putExtra("aboutme", Resource.url + "/IKnow/QuestionAction/");
				intent.putExtra("title", "我发起的问题");
				startActivity(intent);

			}
		});

		ijoin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MessageFragment.this, MessageContent.class);
				intent.putExtra("aboutme", Resource.url + "/IKnow/ConversationAction/");
				intent.putExtra("title", "我参与的问题");
				startActivity(intent);
			}
		});
		try {
			PreferenceUtils.setPrefString(getApplicationContext(), "answererid", MyReceiver.jsonObject.getString("answererid"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		converlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent=new Intent(MessageFragment.this,MainActivity.class);
				intent.putExtra("answererid", chatadapter.getList().get(position).getAnswererid());
				intent.putExtra("answerername", chatadapter.getList().get(position).getAnswerername());
				startActivity(intent);
			}
		});

		converlist.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(MessageFragment.this, position + "", Toast.LENGTH_LONG).show();
				Log.v("my", "fdfdfdfdonItemClick clicked" + position);
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MessageFragment.this);
				builder.setMessage("确定删除此项？");
				builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						templist.remove(position);
						chatadapter.notifyDataSetChanged();
						dialog.dismiss();
					
					}
				
				});
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						
					}
				});
				
				builder.create().show(); 
				return false;
			}

		});

	}

}
