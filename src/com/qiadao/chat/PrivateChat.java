package com.qiadao.chat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.qiadao.bean.ChartBean;
import com.qiadao.bean.ChatMessage;
import com.qiadao.bean.ChatMessage.Type;
import com.qiadao.firstfragment.MessageFragment;
import com.qiadao.iknow.R;
import com.qiadao.tool.ACache;
import com.qiadao.tool.HttpUtil;
import com.qiadao.tool.HttpUtils2;
import com.qiadao.tool.PreferenceUtils;
import com.qiadao.tool.Resource;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PrivateChat extends Activity {
	/**
	 * 展示消息的listview
	 */
	private ListView mChatView;
	/**
	 * 文本�?
	 */
	private EditText mMsg;
	/**
	 * 存储聊天消息
	 */
	private List<ChatMessage> mDatas = new ArrayList<ChatMessage>();
	/**
	 * 适配�?
	 */
	public ChatMessageAdapter mAdapter;
	private TextView toptitle;
	public static List<ChartBean> chart = new ArrayList<ChartBean>();
	private String title;
	private String answererid;
	private String questionid;
	private ACache mcahe ;
	private JSONArray messagearray;
	public static PrivateChat activity;
	private long time;
	private String name;
	private String avatar;
	private Button back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_chatting);
		activity=this;
		mChatView = (ListView) findViewById(R.id.id_chat_listView);
		mMsg = (EditText) findViewById(R.id.id_chat_msg);
		toptitle = (TextView) findViewById(R.id.toptitle);
		back=(Button) findViewById(R.id.back);
		mcahe = ACache.get(getApplicationContext());
		messagearray = mcahe.getAsJSONArray("messagearray");
		
		answererid = getIntent().getStringExtra("chatid");
		questionid=getIntent().getStringExtra("questionid");
		//chart = JSON.parseArray(mcahe.getAsJSONArray("messagearray").toString(), ChartBean.class);
		for (ChartBean bean : chart) {
			
			if (!"".equals(bean.getAnswererid())&& bean.getAnswererid()!=null&& bean.getAnswererid().equals(answererid)) {
				ChatMessage message = new ChatMessage();
				message.setMsg(bean.getTitle());
				message.setName(bean.getAnswerername());
				
				
				message.setDate(bean.getCreatetime());
				if(bean.isIsput()){
					message.setType(ChatMessage.Type.OUTPUT);
				}else{
					message.setType(ChatMessage.Type.INPUT);
				}
				message.setAnswereravatar(bean.getAnswereravatar());
				
				questionid=getIntent().getStringExtra("questionid");
				mDatas.add(message);
			}
			
			
		}
		avatar=getIntent().getStringExtra("avatar");
		name=getIntent().getStringExtra("chatname");
		
		toptitle.setText(name);
		mAdapter = new ChatMessageAdapter(this, mDatas);

		mChatView.setAdapter(mAdapter);
		// mChatView.setSelection(mDatas.size()-1);
		
		time=System.currentTimeMillis();
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PrivateChat.this.finish();
			}
		});
	}

	public void sendMessage(View view)
	{
		final String msg = mMsg.getText().toString();
		if (TextUtils.isEmpty(msg))
		{
			Toast.makeText(this, "您还没有填写信息?", Toast.LENGTH_SHORT).show();
			return;
		}
		
		ChatMessage to = new ChatMessage(Type.OUTPUT, msg);
		to.setDate(new Date());
		mDatas.add(to);
		
		mAdapter.notifyDataSetChanged();
		mChatView.setSelection(mDatas.size() - 1);

		mMsg.setText("");
		
		
		RequestParams params=new RequestParams();
		params.put("answertitle", msg);
		params.put("presenterid", answererid);
		params.put("answererid", PreferenceUtils.getPrefString(getApplicationContext(), "userid",""));
		params.put("questionid",questionid);
		
		Log.v("ffff", params+"");
		if (MessageFragment.activity != null) {
			if( MessageFragment.activity.chatadapter!=null && MessageFragment.activity.chatadapter.getList()!=null ){
				boolean sign=false;
				for (ChartBean bean : MessageFragment.activity.chatadapter.getList()) {
					if(answererid.equals(bean.getAnswererid())){
						sign=true;
					}
				}
				if(sign==false){
					MessageFragment.activity.chatadapter.getList().add(new ChartBean(answererid, name, avatar));
					MessageFragment.activity.chatadapter.notifyDataSetChanged();
				}
			}
		}
		
		HttpUtil.post(Resource.url+"/IKnow/ConversationAction/Other", params, new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(int statusCode, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, response);
				try {
					JSONObject jsonObject=new JSONObject();
					jsonObject.put("title", msg);
					jsonObject.put("presenterid", answererid);
					jsonObject.put("questionid", questionid);
					jsonObject.put("answererid", answererid);
					jsonObject.put("isput", true);
					messagearray.put(jsonObject);
					mcahe.put("messagearray", messagearray);
					
		
				} catch (Exception e) {
					// TODO: handle exception
					Log.v("ffffsss", e+"");
				}
						
				//Toast.makeText(getApplicationContext(), response.toString(), 1).show();
			}
		});

		// 关闭软键�?
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		// 得到InputMethodManager的实�?
		if (imm.isActive())
		{
			// 如果�?��
			imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
					InputMethodManager.HIDE_NOT_ALWAYS);
			// 关闭软键盘，�?��方法相同，这个方法是切换�?��与关闭状态的
		}
	}
}
