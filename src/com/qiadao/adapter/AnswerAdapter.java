package com.qiadao.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRouter.SimpleCallback;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ikonw.bean.ConversBean;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiadao.chat.MainActivity;
import com.qiadao.chat.PrivateChat;
import com.qiadao.iknow.R;
import com.qiadao.secondfragment.AnswerFragment;
import com.qiadao.tool.ACache;
import com.qiadao.tool.HttpUtil;
import com.qiadao.tool.PreferenceUtils;
import com.qiadao.tool.RemoteImageHelper;
import com.qiadao.tool.Resource;
import com.qiadao.tool.RoundImageView;

public class AnswerAdapter extends BaseAdapter {

	private Context context;
	private List<ConversBean> answers;
	RemoteImageHelper lazyImageHelper = new RemoteImageHelper();
	public static AnswerAdapter answeradapter;
	public CheckBox chooseperson;
	public Button chat;
	public Button xuanze;
	public static Boolean a = true;
	public HashMap<Integer, Boolean> ischeck;
	public List<Boolean> mChecked;
	public static String uid;
	private int i = -1;
	public static Boolean b = true;
	public static List<String> usersid = new ArrayList<String>();
	private String sb = "";// 用来控制CheckBox的选中状况
	public static HashMap<Integer, Boolean> isSelected;
	public static String questionid;

	public AnswerAdapter(Context context, List<ConversBean> answers) {
		super();
		this.context = context;
		this.answers = answers;
		isSelected = new HashMap<Integer, Boolean>();
		initDate();
	}

	private void initDate() {
		for (int i = 0; i < answers.size(); i++) {
			getIsSelected().put(i, false);
		}
	}

	@Override
	public int getCount() {
		return answers.size();
	}

	@Override
	public Object getItem(int position) {
		return answers.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		RelativeLayout relaytivelayout;
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			// relaytivelayout = (RelativeLayout) View.inflate(context, R.layout.answer_item, null);
			convertView = View.inflate(context, R.layout.answer_item, null);
			holder.chooseperson = (CheckBox) convertView.findViewById(R.id.chooseperson);
			chooseperson = (CheckBox) convertView.findViewById(R.id.chooseperson);
			holder.answer = (TextView) convertView.findViewById(R.id.answerbox);
			holder.chat = (Button) convertView.findViewById(R.id.chat);
			holder.answererhead = (RoundImageView) convertView.findViewById(R.id.answererhead);
			// 为view设置标签
			convertView.setTag(holder);
		} else {
			// relaytivelayout = (RelativeLayout) convertView;
			holder = (ViewHolder) convertView.getTag();
		}
		final ConversBean converbean = (ConversBean) getItem(position);
		final RelativeLayout answerbo = (RelativeLayout) convertView.findViewById(R.id.answerbo);
		chooseperson = (CheckBox) convertView.findViewById(R.id.chooseperson);
		chat = (Button) convertView.findViewById(R.id.chat);
		TextView answer = (TextView) convertView.findViewById(R.id.answerbox);
		RoundImageView answererhead = (RoundImageView) convertView.findViewById(R.id.answererhead);
		holder.answer.setText(converbean.getAnswertitle());
		ImageLoader.getInstance().displayImage(converbean.getAnswerer().getAvatar(), holder.answererhead);
		
		chooseperson.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton btn, boolean value) {
				if(value){
					getList().get(position).setIscheck(true);
				}else{
					getList().get(position).setIscheck(false);
				}
			}
		});

		answerbo.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					MediaPlayer mp = new MediaPlayer();
					if (mp.isPlaying()) {
						mp.stop();
						mp.release();
					} else {
						mp.setDataSource(converbean.getAnswerURL());
						mp.prepare();
						mp.start();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		if (!a) {
			chooseperson.setVisibility(View.VISIBLE);
			chat.setVisibility(View.GONE);
		} else if (a) {
			chooseperson.setVisibility(View.GONE);
			chat.setVisibility(View.VISIBLE);
		}

		chat.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, PrivateChat.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("chatid", converbean.getAnswerer().getUserid());
				intent.putExtra("chatname",converbean.getAnswerer().getUsername());
				intent.putExtra("avatar", converbean.getAnswerer().getAvatar());
				
				intent.putExtra("questionid",questionid);
				context.startActivity(intent);
				Log.v("222222222222222", questionid);
			}
		});

		return convertView;
	}

	public List<ConversBean> getList() {
		return answers;
	}

	public void setList(List<ConversBean> answers) {
		this.answers = answers;
	}

	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		AnswerAdapter.isSelected = isSelected;
	}

	public class ViewHolder {
		public TextView answer;
		RoundImageView answererhead;
		TextView createTime;
		public CheckBox chooseperson;
		Button chat;
		Button xuanze;
	}

}