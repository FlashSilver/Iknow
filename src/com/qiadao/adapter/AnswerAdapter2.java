package com.qiadao.adapter;

import java.io.IOException;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.ikonw.bean.ConversBean;
import com.ikonw.bean.QuestionBean;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiadao.bean.AnswerBean;
import com.qiadao.firstfragment.LaunchFragment;
import com.qiadao.iknow.R;
import com.qiadao.secondfragment.AnswerFragment;
import com.qiadao.tool.HttpUtil;
import com.qiadao.tool.RemoteImageHelper;
import com.qiadao.tool.Resource;
import com.qiadao.tool.RoundImageView;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AnswerAdapter2 extends BaseAdapter {

	private Context context;
	private List<ConversBean> answers;
	RemoteImageHelper lazyImageHelper = new RemoteImageHelper();

	public AnswerAdapter2(Context context, List<ConversBean> answers) {
		super();
		this.context = context;
		this.answers = answers;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
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
		if (convertView == null) {
			relaytivelayout = (RelativeLayout) View.inflate(context, R.layout.answer_item, null);
		} else {
			relaytivelayout = (RelativeLayout) convertView;
		}
		//
		final ConversBean converbean = (ConversBean) getItem(position);

		final RelativeLayout answerbo = (RelativeLayout) relaytivelayout.findViewById(R.id.answerbo);
		TextView answer = (TextView) relaytivelayout.findViewById(R.id.answerbox);
		RoundImageView answererhead = (RoundImageView) relaytivelayout.findViewById(R.id.answererhead);
		answer.setText(converbean.getAnswertitle());
		ImageLoader.getInstance().displayImage(converbean.getAnswerer().getAvatar(), answererhead);

		answerbo.setOnClickListener(new OnClickListener() {
			
			@Override
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

		return relaytivelayout;
	}
	public List<ConversBean> getList() {
		return answers;
	}

	public void setList(List<ConversBean> answers) {
		this.answers = answers;
	}
}
