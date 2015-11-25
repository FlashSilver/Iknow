package com.qiadao.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiadao.bean.ExpensiveBean;
import com.qiadao.bean.QuestionBean;
import com.qiadao.iknow.R;
import com.qiadao.tool.RemoteImageHelper;
import com.qiadao.tool.RoundImageView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class QuestionAdapter extends BaseAdapter {
	private Context context;
	private List<QuestionBean> questions;
	RemoteImageHelper lazyImageHelper = new RemoteImageHelper();

	public QuestionAdapter(Context context, List<QuestionBean> questions) {
		super();
		this.context = context;
		this.questions = questions;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return questions.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return questions.get(position);
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
			relaytivelayout=(RelativeLayout) View.inflate(context,R.layout.launch_item, null);			
		}
		else{
			relaytivelayout=(RelativeLayout) convertView;
		}
		//View view = super.getView(position, convertView, parent);
		QuestionBean questionbean=(QuestionBean) getItem(position);
		TextView question=(TextView) relaytivelayout.findViewById(R.id.questionbox);
		RoundImageView avater=(RoundImageView) relaytivelayout.findViewById(R.id.imageView2);
		TextView price = (TextView) relaytivelayout.findViewById(R.id.launchprice);
		question.setText(questionbean.getQuestiontitle());
		price.setText(questionbean.getPrice());
		//lazyImageHelper.loadImage(avater, questionbean.getPresenter().getAvatar(), false);
		ImageLoader.getInstance().displayImage(questionbean.getPresenter().getAvatar(),avater);
		return relaytivelayout;
	}
	public List<QuestionBean> getList() {
		return questions;
	}
	public void setList(List<QuestionBean> expensivebean) {
		this.questions = questions;
	}

	
}
