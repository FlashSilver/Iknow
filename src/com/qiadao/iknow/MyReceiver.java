package com.qiadao.iknow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qiadao.bean.Answerer;
import com.qiadao.bean.ChartBean;
import com.qiadao.bean.ChatMessage;
import com.qiadao.firstfragment.MessageFragment;
import com.qiadao.secondfragment.AnswerFragment;
import com.qiadao.tool.ACache;
import com.qiadao.tool.PreferenceUtils;

import android.R.string;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.DocumentsContract.Document;
import android.util.Log;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.qiadao.chat.MainActivity;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";
	public static String RegistrationID = "";
	public static JSONObject jsonObject = new JSONObject();
	public static List<JSONObject> receivelist = new ArrayList<JSONObject>();
	public static String chartdata;
	private long time;

	@Override
	public void onReceive(Context context, Intent intent) {
		context.getContentResolver();
		Bundle bundle = intent.getExtras();
		String result = printBundle(bundle);

		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
			RegistrationID = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
			Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);

		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
			Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));

		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
			Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
			try {
				
				jsonObject = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
				ACache mcahe = ACache.get(context);
				Log.v("jsonObject", jsonObject.toString());
				int type = jsonObject.getInt("type");
				switch (type) {
				case 0:
					break;
				case 1:
					
					break;
				case 2: {			
					JSONArray messagearray = mcahe.getAsJSONArray("messagearray");
					//jsonObject.put("createtime", new Date());
					jsonObject.put("isput", false);
					if (messagearray!=null) {
						messagearray.put(jsonObject);
						mcahe.put("messagearray", messagearray);
						
					} else {
						messagearray = new JSONArray();
						messagearray.put(jsonObject);
						mcahe.put("messagearray", messagearray);
					}
					if (MessageFragment.activity != null) {
						if( MessageFragment.activity.chatadapter!=null && MessageFragment.activity.chatadapter.getList()!=null ){
							boolean sign=false;
							for (ChartBean bean : MessageFragment.activity.chatadapter.getList()) {
								if(jsonObject.getString("answererid").equals(bean.getAnswererid())){
									sign=true;
								}
							}
							if(sign==false){
								MessageFragment.activity.chatadapter.getList().add(new ChartBean(jsonObject.getString("answererid"), jsonObject.getString("answerername"), jsonObject.getString("answereravatar")));
								MessageFragment.activity.chatadapter.notifyDataSetChanged();
							}
						}
					}
					if (MainActivity.activity != null) {
						if( MainActivity.activity.mAdapter!=null && MainActivity.activity.mAdapter.getmDatas()!=null ){
							ChatMessage chat=new ChatMessage();
							chat.setAnswereravatar(jsonObject.getString("answereravatar"));
							chat.setName(jsonObject.getString("answerername"));
							chat.setMsg(jsonObject.getString("title"));
//							if(System.currentTimeMillis()-time>10000000){
//								chat.setDate(new Date());
//							}
							chat.setType(ChatMessage.Type.INPUT );
							
							MainActivity.activity.mAdapter.getmDatas().add(chat);
							MainActivity.activity.mAdapter.notifyDataSetChanged();
						}
						time=System.currentTimeMillis();
					}
					
				}
					break;
				case 3:
					break;
				}
				//
				// PreferenceUtils.setPrefString(context, "answererid", jsonObject.getString("answererid"));
				// receivelist.add(jsonObject);
				// MessageFragment.chart.add(new ChartBean(jsonObject.getString("answererid"), jsonObject.getString("answerername"), jsonObject.getString("answereravatar")));
				// Toast.makeText(context, jsonObject.toString(),1).show();
				// PreferenceUtils.setPrefString(context, "data",jsonObject.toString());
				// MessageFragment.converlist.setAdapter(MessageFragment.chatadapter);
				// MessageFragment.chatadapter.notify();

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
			Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
			JPushInterface.reportNotificationOpened(context, bundle.getString(JPushInterface.EXTRA_MSG_ID));
			// try {
			// Log.v("id",jsonObject.getInt("type")+"");
			// // 通知
			// if (jsonObject.getInt("type") == 2) {
			// Intent i = new Intent(context, NotificationInfoActivity.class);
			// i.putExtra("notificationid", jsonObject.getString("id"));
			// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// context.startActivity(i);
			// } else if (jsonObject.getInt("type") == 3) { // 任务
			// Intent i = new Intent(context, TaskInfoActivity.class);
			// i.putExtra("taskid", jsonObject.getString("id"));
			// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// context.startActivity(i);
			// }
			// } catch (JSONException e) {
			//
			// e.printStackTrace();
			// }
		} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
			Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
			// 在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

		} else {
			Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
		}
	}

	public static String getRegistrationID(Context context) {
		return JPushInterface.getRegistrationID(context);
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			// /typelist.put(key, bundle.getString(key));
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));

			}

			if (key.equals("cn.jpush.android.EXTRA")) {
				try {
					jsonObject = new JSONObject(bundle.getString(key));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				Log.v("key", bundle.getString(key));
			}

		}
		return sb.toString();
	}

}
