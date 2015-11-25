package com.qiadao.adapter;

import java.util.List;

import com.ikonw.bean.ConversBean;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiadao.bean.ChartBean;
import com.qiadao.bean.QuestionBean;
import com.qiadao.iknow.R;
import com.qiadao.tool.RemoteImageHelper;
import com.qiadao.tool.RoundImageView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ChatAdapter extends BaseAdapter {
	private Context context;
	private List<ChartBean> chart;
	RemoteImageHelper lazyImageHelper = new RemoteImageHelper();

	public ChatAdapter(Context context, List<ChartBean> answers) {
		super();
		this.context = context;
		this.chart = answers;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return chart.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return chart.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RelativeLayout relaytivelayout;
		if(convertView==null){
			relaytivelayout=(RelativeLayout) View.inflate(context,R.layout.message_item, null);			
		}
		else{
			relaytivelayout=(RelativeLayout) convertView;
		}
		ChartBean chartbean=(ChartBean) getItem(position);
		TextView answerername=(TextView) relaytivelayout.findViewById(com.qiadao.iknow.R.id.answerername);
		TextView answererid=(TextView) relaytivelayout.findViewById(com.qiadao.iknow.R.id.answererid);
		RoundImageView answerhead=(RoundImageView) relaytivelayout.findViewById(R.id.answererhead);
		
		ImageLoader.getInstance().displayImage(chartbean.getAnswereravatar(), answerhead);
		answerername.setText(chartbean.getAnswerername());
		answererid.setText(chartbean.getAnswererid());
		return relaytivelayout;
	}
	public List<ChartBean> getList() {
		return chart;
	}
	public void setList(List<ChartBean> chart) {
		this.chart = chart;
	}

}
