package com.qiadao.secondfragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.bool;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;

import com.alibaba.fastjson.JSON;
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
import com.ikonw.bean.ConversBean;
import com.ikonw.bean.QuestionBean;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiadao.adapter.AnswerAdapter;
import com.qiadao.adapter.AnswerAdapter.ViewHolder;
import com.qiadao.firstfragment.LaunchFragment2;
import com.qiadao.iknow.R;
import com.qiadao.tool.ACache;
import com.qiadao.tool.FileUtils;
import com.qiadao.tool.HttpUtil;
import com.qiadao.tool.IatSettings;
import com.qiadao.tool.JsonParser;
import com.qiadao.tool.PreferenceUtils;
import com.qiadao.tool.Resource;
import com.qiadao.tool.RoundImageView;
import com.qiadao.tool.Utils;
import com.tencent.utils.HttpUtils;

import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView;


public class AnswerFragment extends Activity {
	private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
	private String mEngineType = SpeechConstant.TYPE_CLOUD;
	private SpeechRecognizer mIat;
	private SharedPreferences mSharedPreferences;
	private RecognizerDialog mIatDialog;
	int ret = 0; // ������÷���
	private String filename = System.currentTimeMillis() + ".pcm";
	private String filePath = FileUtils.fileUtils.getStorageDirectory() + filename;
	private Dialog dialog;
	File voice = new File(filePath);

	private TextView name;
	private TextView questioncontent;
	private TextView time;
	private TextView price;
	private RoundImageView launcherhead;
	private ListView answerlist;
	private AnswerAdapter answeradapter;
	private Button play;
	public String questionid;
	private QuestionBean question;
	private MediaPlayer mps = new MediaPlayer();
	private ProgressBar animation;
	private String userid;
	public Button xuanze;
	private Button huida;
	private Boolean i=false;
	private StringBuffer resultBuffer;
	private PopupWindow pop;
	private Button sure;
	private Button retrive;
	private TextView answercontent;
	private RelativeLayout confirm;
	public static AnswerFragment answerfragment;
	int e=1;
	private LinearLayout jiangli;
	private Button cancel;
	private Button honor;
	private String sb=null;
	public HashMap<Integer,Boolean> hash=new HashMap<Integer, Boolean>();
	public List<Integer> userplace=new ArrayList<Integer>();
	private List<ConversBean> qlist=new ArrayList<ConversBean>();
	private TextView share;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.answer);
	
		name = (TextView) findViewById(R.id.name);
		questioncontent = (TextView) findViewById(R.id.questioncontent);
		time = (TextView) findViewById(R.id.time);
		price = (TextView) findViewById(R.id.price);
		answerlist = (ListView) findViewById(R.id.answerlist);
		launcherhead = (RoundImageView) findViewById(R.id.launcherhead);
		play = (Button) findViewById(R.id.play);
		share= (TextView) findViewById(R.id.share);
		animation = (ProgressBar) findViewById(R.id.animation);
		xuanze = (Button) findViewById(R.id.xuanze);
		huida = (Button) findViewById(R.id.huida);
		AnswerAdapter.questionid=getIntent().getStringExtra("id");
		questionid = getIntent().getStringExtra("id");
		Log.v("1111111111111", questionid);
		jiangli=(LinearLayout) findViewById(R.id.jiangli);
		cancel=(Button) findViewById(R.id.cancel);
		honor=(Button) findViewById(R.id.honor);
//		sure = (Button) findViewById(R.id.sure);
//		retrive = (Button)findViewById(R.id.retrive);	
//		answercontent=(TextView)findViewById(R.id.answercontent);
//		confirm=(RelativeLayout) findViewById(R.id.confirm);
		share.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showShare();
			}
		});
		
		
		huida.setOnTouchListener(radioButtonTouchListener);

		SpeechUtility.createUtility(this, "appid=5533725e");
		mIat = SpeechRecognizer.createRecognizer(this, mInitListener);
		mIatDialog = new RecognizerDialog(this, mInitListener);
		mEngineType = SpeechConstant.TYPE_CLOUD;
		mSharedPreferences = getSharedPreferences(IatSettings.PREFER_NAME, Activity.MODE_PRIVATE);
		HttpUtil.get(Resource.url + "/IKnow/QuestionAction/" + questionid + "/ShowJSON", new JsonHttpResponseHandler() {
			public void onSuccess(int statusCode, Header[] headers, String responseBody) {
				animation.setVisibility(View.GONE);
				question = JSON.parseObject(responseBody, QuestionBean.class);
				name.setText(question.getPresenter().getUsername());
				questioncontent.setText(question.getQuestiontitle());
				price.setText(question.getPrice() + "");
				time.setText(Utils.timeAccount(question.getCreatetime()));
				answeradapter = new AnswerAdapter(getApplicationContext(), question.getConversations());
				ImageLoader.getInstance().displayImage(question.getPresenter().getAvatar(), launcherhead);
				playMedia();
				answerlist.setAdapter(answeradapter);
				if (PreferenceUtils.getPrefString(getApplicationContext(), "userid", "").equals(question.getPresenter().getUserid())) {
					xuanze.setVisibility(View.VISIBLE);
				} else {
					huida.setVisibility(View.VISIBLE);
					AnswerAdapter.a=true;
				}
			}
		});
	
//		 answerlist.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {  
//	            @Override  
//	            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  
//	                    long arg3) {  
//	           
//	                // 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤  
//	            	ViewHolder holder = (ViewHolder) arg1.getTag();  
//	                // 改变CheckBox的状态  
//	                holder.chooseperson.toggle(); 
//	                // 将CheckBox的选中状况记录下来  
//	                List<Integer> intlist=userplace;
//	                if(userplace.size()==0){
//	                	userplace.add(arg2);
//	                }else{
//	                	for (int idx=0; idx < userplace.size(); idx++){
//	                		int tmpItem = userplace.get(idx);
//		                	if(tmpItem==arg2){
//		                		intlist.remove(idx);
//		                	}else{
//		                		intlist.add(intlist.size()-1,arg2);
//		                	}
//		                }
//	                	userplace=intlist;
//	                }
//	                
//	                Toast.makeText(getApplicationContext(), arg2+"", 1).show();
//	                AnswerAdapter.getIsSelected().put(arg2, holder.chooseperson.isChecked());   
//	                hash = AnswerAdapter.getIsSelected();
//	                  
//	            }  
//	        });  
		
		play.setOnClickListener(new android.view.View.OnClickListener() {
			public void onClick(View v) {
				if (!mps.isPlaying()) {
					mps.start();
					play.setBackgroundResource(R.drawable.yuyin_up);
				} else {
					mps.pause();
					play.setBackgroundResource(R.drawable.yuyin_down);
				}
			}
		});

	}
	public void xuanze(View target){
		AnswerAdapter.a=false;
		answeradapter.notifyDataSetChanged();
		jiangli.setVisibility(View.VISIBLE);
	}
	public void cancel(View target){
		jiangli.setVisibility(View.GONE);
		AnswerAdapter.a=true;
		answeradapter.notifyDataSetChanged();
	}
	public void onClickComeBack(View target) {
		finish();
	}
	
	public void honor(View target){
		String userids="";
		for (ConversBean bean : answeradapter.getList()) {
			if(bean.getIscheck()){
				userids+=bean.getAnswerer().getUserid()+",";
				Log.v("1111111111111", bean.getAnswerer().getUserid());
			}
		}
		if(userids.length()==0){
			AlertDialog.Builder builder = new AlertDialog.Builder(
					AnswerFragment.this);
			builder.setMessage("至少选择一人");
			builder.show(); 
		}else{
		userids=userids.substring(0, userids.length()-1);
		Log.v("userids", userids);
		
		RequestParams param=new RequestParams();
		param.put("userids",userids);
		param.put("questionid", question.getQuestionid());
		HttpUtil.post(Resource.url+"/IKnow/QuestionUserAction/",param, new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				Toast.makeText(getApplicationContext(),"悬赏成功！", 1).show();
			}
			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, e, errorResponse);
				Toast.makeText(getApplicationContext(), "fail",1).show();
			}
		});		
		
		AnswerFragment.this.finish();
		}
	}
	
	

	public void playMedia() {
		try {
			mps.setDataSource(question.getQuestionURL() + "");
			mps.prepare();
			mps.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	OnTouchListener radioButtonTouchListener = new OnTouchListener() {
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				setParam();
				Toast.makeText(getApplicationContext(), "请开始说话",1).show();
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
			//Toast.makeText(getApplicationContext(), results.getResultString(), 0).show();
			printResult(results);
			if (isLast) {
				dialog();
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

		  resultBuffer = new StringBuffer();
		for (String key : mIatResults.keySet()) {
			resultBuffer.append(mIatResults.get(key));
		}
		
		
//		confirm.setVisibility(View.VISIBLE);
//		//Toast.makeText(getApplicationContext(), resultBuffer.toString(), 1).show();
//		answercontent.setText(resultBuffer.toString());
//		
//		sure.setOnClickListener(new android.view.View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				RequestParams param = new RequestParams();
//				param.put("answertitle", resultBuffer.toString());
//				param.put("answererid", PreferenceUtils.getPrefString(getApplicationContext(), "userid", ""));
//				param.put("questionid", questionid);
//				try {
//					param.put("voice", voice);
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				HttpUtil.post(Resource.url + "/IKnow/ConversationAction", param, new JsonHttpResponseHandler() {
//
//					@Override
//					public void onSuccess(int statusCode, JSONObject response) {
//						// TODO Auto-generated method stub
//						super.onSuccess(statusCode, response);
//					}
//
//					@Override
//					public void onFailure(int statusCode, Throwable e, JSONObject errorResponse) {
//						// TODO Auto-generated method stub
//						super.onFailure(statusCode, e, errorResponse);
//						Toast.makeText(getApplicationContext(), "fail".toString(), 1).show();
//					}
//				});
//				confirm.setVisibility(View.GONE);
//			}
//		});
//		
//		retrive.setOnClickListener(new android.view.View.OnClickListener() {
//			
//			public void onClick(View v) {
//				confirm.setVisibility(View.GONE);
//			}
//		});
		}

	protected void dialog() {
		AlertDialog.Builder builder = new Builder(AnswerFragment.this);
		builder.setMessage("没有结果");
		if(resultBuffer!= null){
			builder.setMessage(resultBuffer.toString());
		}
		builder.setTitle("确认发送？");
		
		builder.setPositiveButton("确认",new OnClickListener() {
			
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				RequestParams param = new RequestParams();
				param.put("answertitle", resultBuffer.toString());
				param.put("answererid", PreferenceUtils.getPrefString(getApplicationContext(), "userid", ""));
				param.put("questionid", questionid);
				try {
					param.put("voice", voice);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				HttpUtil.post(Resource.url + "/IKnow/ConversationAction", param, new JsonHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, JSONObject response) {
						// TODO Auto-generated method stub
						super.onSuccess(statusCode, response);
						
						//========refrash
						HttpUtil.get(Resource.url + "/IKnow/QuestionAction/" + questionid + "/ShowJSON", new JsonHttpResponseHandler() {
							public void onSuccess(int statusCode, Header[] headers, String responseBody) {
								animation.setVisibility(View.GONE);
								question = JSON.parseObject(responseBody, QuestionBean.class);
								name.setText(question.getPresenter().getUsername());
								questioncontent.setText(question.getQuestiontitle());
								price.setText(question.getPrice() + "");
								time.setText(Utils.timeAccount(question.getCreatetime()));
								answeradapter = new AnswerAdapter(getApplicationContext(), question.getConversations());
								ImageLoader.getInstance().displayImage(question.getPresenter().getAvatar(), launcherhead);
								playMedia();
								answerlist.setAdapter(answeradapter);
								if (PreferenceUtils.getPrefString(getApplicationContext(), "userid", "").equals(question.getPresenter().getUserid())) {
									xuanze.setVisibility(View.VISIBLE);
								} else {
									huida.setVisibility(View.VISIBLE);
								}
							}
						});
						//==============
					}

					@Override
					public void onFailure(int statusCode, Throwable e, JSONObject errorResponse) {
						// TODO Auto-generated method stub
						super.onFailure(statusCode, e, errorResponse);
						Toast.makeText(getApplicationContext(), "fail".toString(), 1).show();
					}
				});
				dialog.dismiss();
				
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();		
	}
	
	private void initPopupWindow() {
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.confirm_answer, null);
		sure = (Button) view.findViewById(R.id.sure);
		retrive = (Button) view.findViewById(R.id.retrive);	
		answercontent=(TextView) view.findViewById(R.id.answercontent);
		pop = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, false);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setOutsideTouchable(true);
		pop.setFocusable(true);
	}
	
	private void showShare() {
		ACache cache=ACache.get(getApplicationContext());		
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		oks.disableSSOWhenAuthorize();
		oks.setTitle("["+question.getPrice()+"元抢答]:"+"Knowledge is money");
		oks.setTitleUrl("http://sharesdk.cn");
		oks.setText(question.getPresenter().getUsername()+":"+question.getQuestiontitle());
		oks.setImagePath(FileUtils.fileUtils.getStorageDirectory()+"logo.JPEG");// 确锟斤拷SDcard锟斤拷锟斤拷锟斤拷诖锟斤拷锟酵计�
		oks.setUrl(Resource.url+"/IKnow/QuestionAction/"+question.getQuestionid()+"/Show");
		oks.setComment("你的评论");
		oks.setSite(getString(R.string.app_name));
		oks.setSiteUrl(Resource.url+"/IKnow/QuestionAction/"+question.getQuestionid()+"/Show");
		oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
			public void onShare(Platform platform, ShareParams paramsToShare) {
				// 改写twitter分享内容中的text字段，否则会超长，
				// 因为twitter会将图片地址当作文本的一部分去计算长度
				if ("WechatMoments".equals(platform.getName())) {
					paramsToShare.setText("");
					paramsToShare.setUrl(Resource.url+"/IKnow/QuestionAction/"+question.getQuestionid()+"/Show");
				}
			}
		});
		oks.show(this);
	}
}