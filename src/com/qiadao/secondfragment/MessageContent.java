package com.qiadao.secondfragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.qiadao.adapter.ExpensiveAdapter;
import com.qiadao.bean.QuestionBean;
import com.qiadao.firstfragment.MessageFragment;
import com.qiadao.firstfragment.SquareFragment;
import com.qiadao.iknow.R;
import com.qiadao.tool.HttpUtil;
import com.qiadao.tool.PreferenceUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MessageContent extends Activity {
	private TextView title;
	private ListView mylist;
	private String aboutme;
	private ExpensiveAdapter expenseadapter=null;
	private List<QuestionBean> quenstionlist = new ArrayList<QuestionBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.message_aboutme);
		title=(TextView) findViewById(R.id.title);
		title.setText(getIntent().getStringExtra("title"));
		mylist=(ListView) findViewById(R.id.mylist);
		String aboutme=getIntent().getStringExtra("aboutme")+PreferenceUtils.getPrefString(getApplicationContext(), "userid", "")+"/User";
		HttpUtil.get(aboutme, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, String responseBody) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, responseBody);
				quenstionlist = JSON.parseArray(responseBody, QuestionBean.class);
				expenseadapter = new ExpensiveAdapter(getApplicationContext(), quenstionlist);
				mylist.setAdapter(expenseadapter);
			}
		});
		mylist.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(MessageContent.this,AnswerFragment.class);
				intent.putExtra("id", quenstionlist.get(position).getQuestionid());
				startActivity(intent);
			}
		});
	}
}
