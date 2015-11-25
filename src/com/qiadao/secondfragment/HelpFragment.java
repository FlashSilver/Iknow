package com.qiadao.secondfragment;

import com.qiadao.iknow.R;
import com.umeng.fb.FeedbackAgent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class HelpFragment extends Activity {
	private Button feedback;
	private Button version;
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.help);
	Button back=(Button) findViewById(R.id.back);
	feedback=(Button) findViewById(R.id.feedback);
	version=(Button) findViewById(R.id.version);
	back.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			HelpFragment.this.finish();
		}
	});
	
	feedback.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			FeedbackAgent fa=new FeedbackAgent(getApplicationContext());
			fa.sync();
			fa.startFeedbackActivity();
		}
	});
	
	version.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), "当前版本为1.0",1).show();
		}
	});
}
}
