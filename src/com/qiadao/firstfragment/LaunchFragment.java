package com.qiadao.firstfragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioTrack;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baidu.location.LocationClient;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.qiadao.adapter.QuestionAdapter;
import com.qiadao.bean.QuestionBean;
import com.qiadao.iknow.R;
import com.qiadao.secondfragment.AnswerFragment;
import com.qiadao.tool.FileUtils;
import com.qiadao.tool.HttpUtil;
import com.qiadao.tool.IatSettings;
import com.qiadao.tool.JsonParser;
import com.qiadao.tool.LocationApplication;
import com.qiadao.tool.MyThread;
import com.qiadao.tool.PreferenceUtils;
import com.qiadao.tool.Resource;

public class LaunchFragment extends Activity {
	private ListView questionlistview;
	private QuestionAdapter questionadapter;
	private List<QuestionBean> questionlist=new ArrayList<QuestionBean>();
	private TextView questioncount;
	private RelativeLayout unend;
	String questionid;
	
	
	
	final int EVENT_PLAY_OVER = 0x100;
	Thread mThread = null;
	byte[] data = null;
	Handler mHandler;
	String filename = System.currentTimeMillis() + ".pcm";
	String filePath = FileUtils.fileUtils.getStorageDirectory()+"/voice/" + filename;

	private Toast mToast;
	private static final String LOG_TAG = "AudioRecordTest";
	private static String mFileName = null;
	protected static final InitListener InitListener = null;
	private SharedPreferences sp;
	private int flag = 1;
	private LinearLayout voice_rcd_hint_rcding, sound_file;
	private ImageButton use_bnt; // �Ƿ�ʹ�������ļ���ť
	private TextView txtName;// ��ʾ�����ļ�
	public static boolean isUsed = false;// �Ƿ�ʹ���Զ��������ļ�
	private Button speak;
	private EditText launch_text2;
	private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
	private String mEngineType = SpeechConstant.TYPE_CLOUD;
	private SpeechRecognizer mIat;
	private SharedPreferences mSharedPreferences;
	private RecognizerDialog mIatDialog;
	int ret = 0; // ������÷���ֵ
	private Button play;
	private Button text;
	private Button xianshi;
	private Button send;
	AudioTrack mAudioTrack;
	Boolean iscm;

	Boolean isOpenLocation = false;
	File voice = new File(filePath);
	private double price = 0;
	private RadioGroup chooseprice;
	private RadioGroup chooseother;
	private RadioButton yiyuan;
	private RadioButton eryuan;
	private RadioButton fiveyuan;
	private RadioButton other;
	private EditText otherchoose;
	private LocationClient location;
	private Boolean istext=true;
	private RelativeLayout dialog;
	private Button launch_speak;
	private RelativeLayout topred;
	private Button back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launch_content);
		questionlistview = (ListView) findViewById(R.id.questionlist);
		questioncount=(TextView) findViewById(R.id.questioncount);
		unend=(RelativeLayout) findViewById(R.id.unend);
		Log.v("registerid",PreferenceUtils.getPrefString(getApplicationContext(), "registerid", "")+"嘿嘿");
		HttpUtil.get(Resource.url + "/IKnow/QuestionAction/Status",new JsonHttpResponseHandler() {
			public void onSuccess(int statusCode, Header[] headers,String responseBody) {
				questionlist=JSON.parseArray(responseBody, QuestionBean.class);
				questionadapter = new QuestionAdapter(getApplicationContext(),questionlist);
				questionlistview.setAdapter(questionadapter);
			} 
		});
		HttpUtil.get(Resource.url+"/IKnow/QuestionAction/Count", new JsonHttpResponseHandler(){
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				try {
					questioncount.setText(response.getString("result")+"个问题待解决");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	HttpUtil.get("http://115.29.205.108:8080/IKnow/QuestionAction/CheckQuestion?userid="+PreferenceUtils.getPrefString(getApplicationContext(), "userid", ""),new JsonHttpResponseHandler(){

		@Override
		public void onSuccess(int statusCode, JSONArray response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, response);
			if(response.length()!=0){
				unend.setVisibility(View.VISIBLE);	
				launch_speak.setEnabled(true);
				try {
					questionid=response.getJSONObject(0).getString("questionid");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{
				unend.setVisibility(View.GONE);
				launch_speak.setEnabled(false);
				
			}
		}
		
		
	});
		unend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(LaunchFragment.this,AnswerFragment.class);
				intent.putExtra("id",questionid);
				startActivity(intent);
			}
		});
		
		questionlistview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Intent intent = new Intent(LaunchFragment.this,AnswerFragment.class);
				intent.putExtra("id", questionlist.get(position).getQuestionid());
				startActivity(intent);
			}
		});
		
		
		
		initbutton();
		launch_speak = (Button) findViewById(R.id.launch_speak);
		Button back = (Button) findViewById(R.id.back);
		text = (Button) findViewById(R.id.text);
		send = (Button) findViewById(R.id.send);
		chooseprice = (RadioGroup) findViewById(R.id.chooseprice);
		otherchoose = (EditText) findViewById(R.id.otherchoose);
		dialog=(RelativeLayout) findViewById(R.id.dialog);
		topred=(RelativeLayout) findViewById(R.id.topred);
		back=(Button) findViewById(R.id.back);
		xianshi = (Button) findViewById(R.id.xianshi);
		xianshi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (flag == 1) {
					xianshi.setBackgroundResource(R.drawable.xianshi_down);
					flag = 2;
					isOpenLocation = true;
				} else {
					xianshi.setBackgroundResource(R.drawable.xianshi_up);
					flag = 1;
					isOpenLocation = false;
				}
			}
		});
	
		chooseprice.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.yiyuan:
					price = 1;
					break;
				case R.id.eryuan:
					price = 2;
					break;
				case R.id.fiveyuan:
					price = 5;
					break;
				 case R.id.other:
				 otherchoose.requestFocus();
				 InputMethodManager imm=(InputMethodManager) otherchoose.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				 imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
				 istext=false;
				 break;
			     
				default:
					
					break;
				}
			}
		});
		

		send.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
				if(launch_text2.getText().toString().equals("")){
					Toast.makeText(getApplicationContext(), "请输入内容",1).show();
				}else{
			if(!istext){
				price = Double.parseDouble(otherchoose.getText().toString());
			}
				
				//Toast.makeText(getApplicationContext(), price+"", 1).show();
				RequestParams request = new RequestParams();
				request.put("questiontitle", launch_text2.getText().toString());
				request.put("userid", PreferenceUtils.getPrefString(getApplicationContext(), "userid", ""));
				request.put("isOpenLocation", isOpenLocation);
				request.put("price", price+"");
				request.put("longitude", LocationApplication.longitude + "");
				request.put("latitude", LocationApplication.latitude + "");
				try {
					request.put("voice", voice);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				Log.v("4444444444", request.toString());
				
				//Toast.makeText(getApplicationContext(), request.toString(), 1).show();
				HttpUtil.post(Resource.url + "/IKnow/QuestionAction", request, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, JSONObject response) {
						super.onSuccess(statusCode, response);
						Log.v("test", response.toString());
						Log.v("statusCode", statusCode + "    ======");
						//Toast.makeText(getApplicationContext(), response.toString(), 1).show();
						// Toast.makeText(getApplicationContext(), price, 1).show();
						dialog.setVisibility(View.GONE);
						topred.setVisibility(View.GONE);
						questionlistview.setVisibility(View.VISIBLE);
						Toast.makeText(getApplicationContext(), "发起成功！", 1).show();
						launch_speak.setEnabled(false);
					}

					@Override
					public void onFailure(Throwable e, JSONObject errorResponse) {
						// TODO Auto-generated method stub
						super.onFailure(e, errorResponse);
						Toast.makeText(getApplicationContext(), "网络不行哦", 1).show();
					}

				});
				}
			}
		});

		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.setVisibility(View.GONE);
				topred.setVisibility(View.GONE);
				questionlistview.setVisibility(View.VISIBLE);
				
			}
		});

		launch_text2 = (EditText) findViewById(R.id.launch_text2);
		launch_speak.setOnTouchListener(radioButtonTouchListener);
		SpeechUtility.createUtility(this, "appid=5533725e");
		mIat = SpeechRecognizer.createRecognizer(this, mInitListener);
		mIatDialog = new RecognizerDialog(this, mInitListener);
		mEngineType = SpeechConstant.TYPE_CLOUD;
		mSharedPreferences = getSharedPreferences(IatSettings.PREFER_NAME, Activity.MODE_PRIVATE);
	}
	//提问
//	public void onClickSpeak(View v){
//		Intent intent=new Intent(LaunchFragment.this,LaunchFragment2.class);
//		startActivity(intent);
//	}
	
	@Override
	protected void onResume() {
		super.onResume();
		HttpUtil.get(Resource.url + "/IKnow/QuestionAction/Status",new JsonHttpResponseHandler() {
			public void onSuccess(int statusCode, Header[] headers,String responseBody) {
				questionlist=JSON.parseArray(responseBody, QuestionBean.class);
				questionadapter = new QuestionAdapter(getApplicationContext(),questionlist);
				questionadapter.notifyDataSetChanged();
				questionlistview.setAdapter(questionadapter);
			} 
		});
		
		
		HttpUtil.get("http://115.29.205.108:8080/IKnow/QuestionAction/CheckQuestion?userid="+PreferenceUtils.getPrefString(getApplicationContext(), "userid", ""),new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(int statusCode, JSONArray response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, response);
				if(response.length()!=0){
					unend.setVisibility(View.VISIBLE);
					launch_speak.setEnabled(false);
					try {
						questionid=response.getJSONObject(0).getString("questionid");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else{
					unend.setVisibility(View.GONE);
					launch_speak.setEnabled(true);
				
				}
			}
		});
	}
	
	OnTouchListener radioButtonTouchListener = new OnTouchListener() {
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				launch_text2.setText(null);// �����ʾ����
				questionlistview.setVisibility(View.GONE);
				unend.setVisibility(View.GONE);
				topred.setVisibility(View.VISIBLE);
				dialog.setVisibility(View.VISIBLE);
				Toast.makeText(getApplicationContext(), "请开始说话",1).show();
				mIatResults.clear();
				setParam();
				boolean isShowDialog = mSharedPreferences.getBoolean(getString(R.string.pref_key_iat_show), true);
				if (!isShowDialog) {
					mIatDialog.setListener(recognizerDialogListener);
				} else {
					ret = mIat.startListening(recognizerListener);
					if (ret != ErrorCode.SUCCESS) {
					}
				}
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				mIat.stopListening();
			}

			return false;

		}
	};

	public void setParam() {
		mIat.setParameter(SpeechConstant.PARAMS, null);
		mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
		mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");
		String lag = mSharedPreferences.getString("iat_language_preference", "mandarin");
		if (lag.equals("en_us")) {
			mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
		} else {
			mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
			mIat.setParameter(SpeechConstant.ACCENT, lag);
		}
		mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "4000"));
		mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "1000"));
		mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "1"));
		mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, filePath);
		mIat.setParameter(SpeechConstant.ASR_DWA, mSharedPreferences.getString("iat_dwa_preference", "0"));
	}

	private InitListener mInitListener = new InitListener() {

		@Override
		public void onInit(int code) {
			Log.v("TAG", "SpeechRecognizer init() code = " + code);
			if (code != ErrorCode.SUCCESS) {
			}
		}
	};
	private RecognizerDialogListener recognizerDialogListener = new RecognizerDialogListener() {
		public void onResult(RecognizerResult results, boolean isLast) {
			printResult(results);
		}

		public void onError(SpeechError error) {
		}

	};

	private void printResult(RecognizerResult results) {
		String text = JsonParser.parseIatResult(results.getResultString());

		String sn = null;
		try {
			JSONObject resultJson = new JSONObject(results.getResultString());
			sn = resultJson.optString("sn");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		mIatResults.put(sn, text);

		StringBuffer resultBuffer = new StringBuffer();
		for (String key : mIatResults.keySet()) {
			resultBuffer.append(mIatResults.get(key));
		}

		launch_text2.setText(resultBuffer.toString());
		launch_text2.setSelection(launch_text2.length());
	}

	private RecognizerListener recognizerListener = new RecognizerListener() {

		@Override
		public void onBeginOfSpeech() {
			// showTip("��ʼ˵��");
		}

		@Override
		public void onError(SpeechError error) {

		}

		@Override
		public void onEndOfSpeech() {
			// showTip("����˵��");
		}

		@Override
		public void onResult(RecognizerResult results, boolean isLast) {
			Log.d("TAG", results.getResultString());
			printResult(results);
			if (isLast) {
				printResult(results);
				// TODO ���Ľ��
			}
		}

		@Override
		public void onVolumeChanged(int volume) {
			// showTip("��ǰ����˵����������С��" + volume);
		}

		@Override
		public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
		}
	};

	private void showTip(final String str) {
		mToast.setText(str);
		mToast.show();
	}

	// ���ű�¼�µ���Ƶ
	public byte[] getPCMData() {
		File file = new File(filePath);
		if (file == null) {
			return null;
		}

		FileInputStream inStream;
		try {
			inStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		if (inStream == null) {
			Toast.makeText(this, "No inStream...", 200).show();

		}
		byte[] data_pack = null;
		if (inStream != null) {
			long size = file.length();

			data_pack = new byte[(int) size];
			try {
				inStream.read(data_pack);
			} catch (IOException e) {
				// TODO Auto-generated catch block hander
				e.printStackTrace();
				return null;
			}
		}
		return data_pack;
	}

	public void play() {
		data = getPCMData();

		if (data == null) {
			Toast.makeText(this, "No File...", 200).show();
			return;
		}

		if (mThread == null) {
			mThread = new Thread(new MyThread(data, mHandler));
			mThread.start();
		}
	}

	public void stop() {
		if (data == null) {
			return;
		}

		if (mThread != null) {
			mThread.interrupt();
			mThread = null;
		}
	}

	public void onDestroy() {
		stop();

		super.onDestroy();
	}
	
	private void initbutton() {
		yiyuan = (RadioButton) findViewById(R.id.yiyuan);
		eryuan = (RadioButton) findViewById(R.id.eryuan);
		fiveyuan = (RadioButton) findViewById(R.id.fiveyuan);
		// other=(RadioButton) findViewById(R.id.other);
		// otherchoose=(EditText) findViewById(R.id.otherchoose);
	}
	
	
}