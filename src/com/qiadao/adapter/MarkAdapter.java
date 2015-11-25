package com.qiadao.adapter;

import java.util.List;

import com.qiadao.iknow.R;
import com.qiadao.secondfragment.MarkFragment.markbean;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MarkAdapter extends BaseAdapter {
	private List<markbean> marklist;
	private Context context;
	public MarkAdapter(Context context,List<markbean>marklist){
		this.context=context;
		this.marklist=marklist;
	}
	@Override
	public int getCount() {
		return marklist.size();
	}

	@Override
	public Object getItem(int position) {
		return marklist.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
    
	@Override
	//�ڲ�����ѭ��
	public View getView(int position, View contentview, ViewGroup parent) {
		RelativeLayout relaytivelayout;
		if(contentview==null){
			relaytivelayout=(RelativeLayout) View.inflate(context,R.layout.markitem, null);			
		}
		else{
			relaytivelayout=(RelativeLayout) contentview;
		}
		//View view = super.getView(position, convertView, parent);
		markbean markbean =(markbean) getItem(position);
		TextView markname=(TextView) relaytivelayout.findViewById(com.qiadao.iknow.R.id.smallmark);
		markname.setText(marklist.get(position).getMarkname());
		return relaytivelayout;
	}
	public List<markbean> getList() {
		return marklist;
	}
	public void setList(List<markbean> expensivebean) {
		this.marklist = marklist;
	}
}
