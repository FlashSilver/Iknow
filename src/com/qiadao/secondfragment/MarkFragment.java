package com.qiadao.secondfragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import com.qiadao.adapter.MarkAdapter;
import com.qiadao.bean.ExpensiveBean;
import com.qiadao.iknow.R;
import com.qiadao.iknow.R.string;
import com.qiadao.tool.ListViewSwipeGesture;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MarkFragment extends Activity {
	private ListView mark;
	private TextView smallmark;
	private List<MarkFragment.markbean> marklist;
	private MarkAdapter markadapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mark);
		mark = (ListView) findViewById(R.id.mark);	
		marklist=new ArrayList<MarkFragment.markbean>();
		listinit();
		markadapter=new MarkAdapter(getApplicationContext(), marklist);
		mark.setAdapter(markadapter);
		final ListViewSwipeGesture touchListener = new ListViewSwipeGesture(
	                mark, swipeListener, this);
	        touchListener.SwipeType	=	ListViewSwipeGesture.Double;    //设置两个选项列表项的背景
	        mark.setOnTouchListener(touchListener);
	}


	private void listinit() {
		marklist.add(new markbean("ios工程师"));
		marklist.add(new markbean("炸弹专家"));
		marklist.add(new markbean("ios工程师"));
		marklist.add(new markbean("炸弹专家"));
		marklist.add(new markbean("ios工程师"));
		marklist.add(new markbean("炸弹专家"));
		marklist.add(new markbean("ios工程师"));
		marklist.add(new markbean("炸弹专家"));
		marklist.add(new markbean("ios工程师"));
		marklist.add(new markbean("炸弹专家"));
		marklist.add(new markbean("ios工程师"));
		marklist.add(new markbean("炸弹专家"));
	}

	public class markbean{
		private String markname;

		public String getMarkname() {
			return markname;
		}

		public void setMarkname(String markname) {
			this.markname = markname;
		}

		public markbean(String markname) {
			super();
			this.markname = markname;
		}

		public markbean() {
			super();
		}
		
	}
    ListViewSwipeGesture.TouchCallbacks swipeListener = new ListViewSwipeGesture.TouchCallbacks() {

        public void FullSwipeListView(int position) {
            // TODO Auto-generated method stub
            Toast.makeText(MarkFragment.this, "Action_2", Toast.LENGTH_SHORT).show();
        }

        public void HalfSwipeListView(int position) {
            // TODO Auto-generated method stub
//            System.out.println("<<<<<<<" + position);
            marklist.remove(position);
            markadapter.notifyDataSetChanged();
            Toast.makeText(MarkFragment.this,"删除", Toast.LENGTH_SHORT).show();
        }

        public void LoadDataForScroll(int count) {
            // TODO Auto-generated method stub
     
        }

        public void onDismiss(ListView listView, int[] reverseSortedPositions) {
            // TODO Auto-generated method stub
//            Toast.makeText(activity,"Delete", Toast.LENGTH_SHORT).show();
//            for(int i:reverseSortedPositions){
//                data.remove(i);
//                new MyAdapter().notifyDataSetChanged();
//            }
        }

        public void OnClickListView(int position) {


        }


    };

}
