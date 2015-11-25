package com.qiadao.adapter;

import java.util.List;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiadao.bean.QuestionBean;
import com.qiadao.iknow.R;
import com.qiadao.tool.RoundImageView;

public class ExpensiveAdapter extends BaseAdapter {
	private List<QuestionBean> expensivebean;
	private Context context;
	public ExpensiveAdapter(Context context,List<QuestionBean> expensivebean){
		this.context=context;
		this.expensivebean=expensivebean;
	}
	@Override
	public int getCount() {
		return expensivebean.size();
	}

	@Override
	public Object getItem(int position) {
		return expensivebean.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
    
	@Override
	public View getView(int position, View contentview, ViewGroup parent) {
		RelativeLayout relaytivelayout;
		if(contentview==null){
			relaytivelayout=(RelativeLayout) View.inflate(context,R.layout.expensiveitem, null);			
		}
		else{
			relaytivelayout=(RelativeLayout) contentview;
		}
		QuestionBean expensivebean=(QuestionBean) getItem(position);
		TextView username=(TextView) relaytivelayout.findViewById(com.qiadao.iknow.R.id.username);
		TextView questiontitle=(TextView) relaytivelayout.findViewById(com.qiadao.iknow.R.id.questiontitle);
		TextView price=(TextView) relaytivelayout.findViewById(R.id.price);
		TextView answeramount=(TextView) relaytivelayout.findViewById(R.id.answeramount);
		RoundImageView avater=(RoundImageView) relaytivelayout.findViewById(R.id.avater);
		
		questiontitle.setText(expensivebean.getQuestiontitle());
		ImageLoader.getInstance().displayImage(expensivebean.getPresenter().getAvatar(),avater);
		answeramount.setText(expensivebean.getAnswercount()+"个回答");
		price.setText(expensivebean.getPrice());
		username.setText(expensivebean.getPresenter().getUsername());
		return relaytivelayout;
	}
	public List<QuestionBean> getList() {
		return expensivebean;
	}
	public void setList(List<QuestionBean> expensivebean) {
		this.expensivebean = expensivebean;
	}
}   
