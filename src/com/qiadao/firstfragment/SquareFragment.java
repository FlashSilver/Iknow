package com.qiadao.firstfragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.qiadao.adapter.ExpensiveAdapter;
import com.qiadao.bean.QuestionBean;
import com.qiadao.effect.MyListView;
import com.qiadao.effect.MyListView.OnRefreshListener;
import com.qiadao.iknow.R;
import com.qiadao.secondfragment.AnswerFragment;
import com.qiadao.tool.HttpUtil;
import com.qiadao.tool.PreferenceUtils;
import com.qiadao.tool.Resource;

public class SquareFragment extends Activity implements OnClickListener {
	private ImageView choosearrow;
	private TextView choosetext;
	private PopupWindow pop;
	private MyListView expense;
	private TextView loading;
	private ExpensiveAdapter expenseadapter=null;
	private List<QuestionBean> quenstionlist = new ArrayList<QuestionBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.square_content);
		initView();
		initPopupWindow();
		
		HttpUtil.get(Resource.url + "/IKnow/QuestionAction/Status", new JsonHttpResponseHandler() {
			public void onSuccess(int statusCode, Header[] headers, String responseBody) {
				super.onSuccess(statusCode, headers, responseBody);
				quenstionlist = JSON.parseArray(responseBody, QuestionBean.class);
				expenseadapter = new ExpensiveAdapter(getApplicationContext(), quenstionlist);
				expense.setAdapter(expenseadapter);
				loading.setVisibility(View.GONE);
			}
		});
	
		expense.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(SquareFragment.this,AnswerFragment.class);
				intent.putExtra("id", quenstionlist.get(position-1).getQuestionid());
				startActivity(intent);
			}
		});
		
		
		expense.setOnRefreshListener(new OnRefreshListener() {
		
			public void onRefresh() {
				HttpUtil.get(Resource.url + "/IKnow/QuestionAction/Status", new JsonHttpResponseHandler() {
					public void onSuccess(int statusCode, Header[] headers, String responseBody) {
						super.onSuccess(statusCode, headers, responseBody);
						quenstionlist = JSON.parseArray(responseBody, QuestionBean.class);
						expenseadapter.notifyDataSetChanged();
						expense.setAdapter(expenseadapter);
						expense.refreshComplete();
					}
				});
			}
			
		});
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.newest:
			choosearrow.setImageResource(R.drawable.ic_menu_trangle_down);
			getInforNewst();
			choosetext.setText("时间最近");
			break;
		case R.id.close:
			choosearrow.setImageResource(R.drawable.ic_menu_trangle_down);
			getInforClose();
			choosetext.setText("距离最近");
			break;
		case R.id.high:
			choosearrow.setImageResource(R.drawable.ic_menu_trangle_down);
			getInforPirce();
			choosetext.setText("价格最高");
		}
		pop.dismiss();
	}

	private void initPopupWindow() {
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.square_chooser, null);
		Button newest = (Button) view.findViewById(R.id.newest);
		newest.setOnClickListener(this);
		newest.setPressed(true);
		Button close = (Button) view.findViewById(R.id.close);
		close.setOnClickListener(this);
		Button high = (Button) view.findViewById(R.id.high);
		high.setOnClickListener(this);
		pop = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, false);
		RelativeLayout chooser = (RelativeLayout) findViewById(R.id.chooser);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setOutsideTouchable(true);
		pop.setFocusable(true);
		chooser.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				int flat=1;
				if (flat==1) {
					choosearrow.setImageResource(R.drawable.ic_menu_trangle_up);
					pop.showAsDropDown(v);
					flat=0;
				} else if(flat==0) {
					choosearrow.setImageResource(R.drawable.ic_menu_trangle_down);
					pop.dismiss();
					flat=1;
				}
			}
		});

		
		
	}
	private void initView() {
		loading = (TextView) findViewById(R.id.loading);
		expense = (MyListView) findViewById(R.id.squarelistview);
		choosearrow = (ImageView) findViewById(R.id.choosearrow);
		choosetext = (TextView) findViewById(R.id.choosetext);
	}
	private void getInforNewst() {
		HttpUtil.get(Resource.url + "/IKnow/QuestionAction/Status", new JsonHttpResponseHandler() {
			public void onSuccess(int statusCode, Header[] headers, String responseBody) {
				super.onSuccess(statusCode, headers, responseBody);
				quenstionlist = JSON.parseArray(responseBody, QuestionBean.class);
				expenseadapter.setList(quenstionlist);
				expenseadapter.notifyDataSetChanged();
			}
		});
	}
	private void getInforClose(){
		HttpUtil.get(Resource.url + "/IKnow/QuestionAction/Status" + "?sign=location&userid=?", new JsonHttpResponseHandler() {
			public void onSuccess(int statusCode, Header[] headers, String responseBody) {
				super.onSuccess(statusCode, headers, responseBody);
				quenstionlist = JSON.parseArray(responseBody, QuestionBean.class);
				expenseadapter.setList(quenstionlist);
				expenseadapter.notifyDataSetChanged();
			}
		});
	}
	private void getInforPirce(){
		HttpUtil.get(Resource.url + "/IKnow/QuestionAction/Status" + "?sign=price", new JsonHttpResponseHandler() {
			public void onSuccess(int statusCode, Header[] headers, String responseBody) {
				super.onSuccess(statusCode, headers, responseBody);
				quenstionlist = JSON.parseArray(responseBody, QuestionBean.class);
				expenseadapter.setList(quenstionlist);
				expenseadapter.notifyDataSetChanged();
			}
		});
	}
	
	
	
}
