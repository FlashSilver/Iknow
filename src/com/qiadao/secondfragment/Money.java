package com.qiadao.secondfragment;

import com.qiadao.iknow.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class Money extends Activity {
	Button recharge;
	Button extract;
	Button back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.money);
		recharge=(Button) findViewById(R.id.recharge);
		extract=(Button) findViewById(R.id.extract);
		back=(Button) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Money.this.finish();
			}
		});
	}
	public void torecharge(View target){             
		Intent intent=new Intent(Money.this,RechargeFragment.class);
		startActivity(intent);
	}
	public void toextract(View target){
		Intent intent=new Intent(Money.this,ExtractFragment.class);
		startActivity(intent);
	}
	
}
