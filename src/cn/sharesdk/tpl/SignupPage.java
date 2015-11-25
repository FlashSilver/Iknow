//package cn.sharesdk.tpl;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.net.URL;
//
//import com.qiadao.iknow.R;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Intent;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.Bitmap.Config;
//import android.graphics.BitmapFactory;
//import android.graphics.drawable.Drawable;
//import android.net.Uri;
//import android.os.Environment;
//import android.os.Handler.Callback;
//import android.os.Message;
//import android.provider.MediaStore;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout.LayoutParams;
//import android.widget.TextView;
//import android.widget.Toast;
//import cn.sharesdk.framework.FakeActivity;
//import cn.sharesdk.framework.Platform;
//import cn.sharesdk.framework.ShareSDK;
//import cn.sharesdk.framework.utils.UIHandler;
//
//public class SignupPage extends FakeActivity implements OnClickListener, Callback {
//
//	/** 淇敼鐢ㄦ埛淇℃伅dialog,濡傜敤鎴蜂俊鎭紝澶囨敞淇℃伅*/
//	private enum ChangeUserType {USER_NAME, USER_NOTE};
//	/**鍔犺浇瀵硅瘽妗�*/
//	private static final int SHOW_PROGRESS_DIALOG = 1;
//	/**鍔犺浇鐢ㄦ埛icon*/
//	private static final int LOAD_USER_ICON = 2;
//	/**Toast 鎻愬崌*/
//	private static final int MSG_SHOW_TOAST = 3;
//	/**鎵撳紑鐩稿唽锛屽苟鎴浘*/
//	private static final int INTENT_ACTION_PICTURE = 0;
//	/**鎵撳紑鐩告満鐓х浉*/
//	private static final int INTENT_ACTION_CAREMA = 1;
//	/**鐓х浉鍚庯紝鎴浘*/
//	private static final int INTENT_ACTION_CROP = 2;
//	/**鍥剧墖鍚嶅瓧*/
//	private static final String PICTURE_NAME = "userIcon.jpg";
//	
//	private OnLoginListener signupListener;
//	private ImageView ivUserIcon;
//	private TextView tvUserName, tvUserGender, tvUserNote, tvEnsure;
//	private Platform platform;
//	
//	private String picturePath;
//	private UserInfo userInfo = new UserInfo();
//	
//	/** 璁剧疆鎺堟潈鍥炶皟锛岀敤浜庡垽鏂槸鍚﹁繘鍏ユ敞鍐� */
//	public void setOnLoginListener(OnLoginListener l) {
//		this.signupListener = l;
//	}
//	
//	@Override
//	public void onCreate() {
//		activity.setContentView(R.layout.tpl_page_signup);
//		activity.findViewById(R.id.ll_back).setOnClickListener(this);
//
//		tvUserName = (TextView) activity.findViewById(R.id.tv_user_name);
//		tvUserNote = (TextView) activity.findViewById(R.id.tv_user_note);
//		tvEnsure = (TextView) activity.findViewById(R.id.tv_ensure);
//		ivUserIcon = (ImageView) activity.findViewById(R.id.iv_user_icon);
//
//		ivUserIcon.setOnClickListener(this);
//		tvEnsure.setOnClickListener(this);
//		activity.findViewById(R.id.rl_icon).setOnClickListener(this);
//		activity.findViewById(R.id.rl_name).setOnClickListener(this);
//		activity.findViewById(R.id.rl_note).setOnClickListener(this);
//		
//		initData();		
//	}
//
//	public void setPlatform(String platName) {
//		platform = ShareSDK.getPlatform(platName);
//	}
//	
//	/**鍒濆鍖栨暟鎹�*/
//	private void initData(){
//		//String gender = "";
//		if(platform != null){
////			gender = platform.getDb().getUserGender();
////			if(gender.equals("m")){
////				//userInfo.setUserGender(UserInfo.Gender.BOY);
////				gender = getContext().getString(R.string.tpl_boy);
////			}else{
////				//userInfo.setUserGender(UserInfo.Gender.GIRL);
////				gender = getContext().getString(R.string.tpl_girl);
////			}
//			
//			userInfo.setUserIcon(platform.getDb().getUserIcon());
//			userInfo.setUserName(platform.getDb().getUserName());
//			userInfo.setUserNote(platform.getDb().getUserId());
//		}
//		
//		tvUserName.setText(userInfo.getUserName());
//		//tvUserGender.setText(gender);
//		tvUserNote.setText("USER ID : " + userInfo.getUserNote());
//		// 鍔犺浇澶村儚
//		if(!TextUtils.isEmpty(userInfo.getUserIcon())){
//			loadIcon();
//		}
//		//鍒濆鍖栫収鐗囦繚瀛樺湴鍧�
//		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
//			String thumPicture = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+activity.getPackageName()+"/download";
//			File pictureParent =new File(thumPicture);	
//			File pictureFile =new File(pictureParent, PICTURE_NAME);	
//			
//			if(!pictureParent.exists()){
//				pictureParent.mkdirs();
//			}
//			try{
//				if (!pictureFile.exists()) {
//					pictureFile.createNewFile();
//				}	
//			}catch (Exception e) {
//				e.printStackTrace();
//			}
//			picturePath = pictureFile.getAbsolutePath();
//			Log.e("picturePath ==>>", picturePath);
//		}else{
//			Log.e("change user icon ==>>", "there is not sdcard!");
//		}
//	}
//
////	@Override
////	public void onClick(View v) {
////		switch (v.getId()) {
////		case R.id.ll_back:
////			this.finish();
////			break;
////		case R.id.tv_ensure:
////			UIHandler.sendEmptyMessage(MSG_SHOW_TOAST, SignupPage.this);
////			break;
////		case R.id.rl_icon:
////			//showChangeIconDialog();
////			getPicture();
////			break;
////		case R.id.rl_name:
////			showChangeInfo(ChangeUserType.USER_NAME);
////			break;
//////		case R.id.rl_gender:
//////			showGerderDialog();
//////			break;
////		case R.id.rl_note:
////			showChangeInfo(ChangeUserType.USER_NOTE);
////			break;
////		case R.id.iv_user_icon:
////			PicViewer pv = new PicViewer();
////			pv.setImagePath(userInfo.getUserIcon());
////			pv.show(activity, null);
////			break;
////		default:
////			break;
////		}		
////	}
//
//	public boolean handleMessage(Message msg) {
//		switch (msg.what) {
//		case SHOW_PROGRESS_DIALOG:
//			break;
//		case LOAD_USER_ICON:
//			ivUserIcon.setImageURI(Uri.parse(picturePath));
//			break;
//
//		default:
//			break;
//		}
//		return false;
//	}
//
//	/**
//	 * 鍔犺浇澶村儚
//	 */
//	public void loadIcon() {
//		final String imageUrl = platform.getDb().getUserIcon();
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					URL picUrl = new URL(imageUrl);
//					Bitmap userIcon = BitmapFactory.decodeStream(picUrl.openStream());
//			        FileOutputStream b = null;  	  
//			        try {  
//			        	b = new FileOutputStream(picturePath);  
//			        	userIcon.compress(Bitmap.CompressFormat.JPEG, 100, b);// 鎶婃暟鎹啓鍏ユ枃浠�  
//			        } catch (FileNotFoundException e) {  
//			            e.printStackTrace();  
//			        } finally {  
//			        	try {  
//			        		b.flush();  
//			                b.close();  
//			            } catch (IOException e) {  
//			                e.printStackTrace();  
//			            }  
//			        } 	            
//			        userInfo.setUserIcon(picturePath);
//
//			        Message msg = new Message();
//					msg.what = LOAD_USER_ICON;
//					UIHandler.sendMessage(msg, SignupPage.this);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	}
//	
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if(requestCode == INTENT_ACTION_PICTURE && resultCode == Activity.RESULT_OK && null != data){
//			Cursor c = activity.getContentResolver().query(data.getData(), null, null, null, null);
//			c.moveToNext();
//			String path = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
//			c.close();
//			System.out.println("onActivityResult == " + path);
//			if(new File(path).exists()){
//				System.out.println("onActivityResult == " + path +" == exist");
//				userInfo.setUserIcon(path);
//				ivUserIcon.setImageBitmap(compressImageFromFile(path));
//				//ivUserIcon.setImageURI(Uri.parse(path));
//				//ivUserIcon.setImageDrawable(Drawable.createFromPath(path));
//			}
//		}else if(requestCode == INTENT_ACTION_CAREMA && resultCode == Activity.RESULT_OK){
//			userInfo.setUserIcon(picturePath);
//			//ivUserIcon.setImageURI(Uri.parse(picturePath));
//			ivUserIcon.setImageDrawable(Drawable.createFromPath(picturePath));
//		}else if(requestCode == INTENT_ACTION_CROP && resultCode == Activity.RESULT_OK && null != data){
//			//ivUserIcon.setImageURI(Uri.parse(picturePath));
//			ivUserIcon.setImageDrawable(Drawable.createFromPath(picturePath));
//		}
//	}
//	
//	/**浠庣浉鍐岃幏鍙栧浘鐗�*/
//	private void getPicture(){
//		Intent intent = new Intent(Intent.ACTION_PICK);
//		intent.setType("image/*");
//		intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//		startActivityForResult(intent, INTENT_ACTION_PICTURE); 
//		//TODO
//	}
//	
////	/**鎵撳紑鐩告満鐓х浉*/
////	private void openCamera(){
////		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(picturePath)));
////		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
////		startActivityForResult(intent, INTENT_ACTION_CAREMA);
////	}
//	
//	/**鍓鏂规硶*/
//	private void openCrop(Uri uri){
//		//TODO 瑁佸壀鏂规硶锛岃嚜宸卞仛
//		Intent intent = new Intent("com.android.camera.action.CROP");
//		   intent.setDataAndType(uri, "image/*");
//		   intent.putExtra("crop", "true");//鍙鍓�
//		   intent.putExtra("aspectX", 1);
//		   intent.putExtra("aspectY", 1);
//		   intent.putExtra("outputX", 100);
//		   intent.putExtra("outputY", 100);
//		   intent.putExtra("scale", true);
//		//   intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//		   intent.putExtra("return-data", true);//鑻ヤ负false鍒欒〃绀轰笉杩斿洖鏁版嵁
//		//   intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//		   intent.putExtra("noFaceDetection", true); 
//		   startActivityForResult(intent, INTENT_ACTION_CROP);
////		startActivityForResult(intent, INTENT_ACTION_CAREMA);
//	}
//	
//	/**   
//	 * gender select dialog,鎬у埆閫夋嫨瀵硅瘽妗�
//	 */
////	private void showGerderDialog(){
////		final Dialog dialog = new Dialog(getContext(), R.style.WhiteDialog);
////		dialog.setContentView(R.layout.tpl_gender_select_dialog);
////		final ImageView ivBoy = (ImageView) dialog.findViewById(R.id.dialog_iv_boy);
////		final ImageView ivGirl = (ImageView) dialog.findViewById(R.id.dialog_iv_girl);
////		if(userInfo.getUserGender() == UserInfo.Gender.BOY){
////			ivGirl.setVisibility(View.GONE);
////			ivBoy.setVisibility(View.VISIBLE);
////		}else{
////			ivBoy.setVisibility(View.GONE);
////			ivGirl.setVisibility(View.VISIBLE);
////		}
////		dialog.findViewById(R.id.rl_boy).setOnClickListener(new OnClickListener() {
////			@Override
////			public void onClick(View v) {
////				ivGirl.setVisibility(View.GONE);
////				ivBoy.setVisibility(View.VISIBLE);
////				tvUserGender.setText(R.string.tpl_boy);
////				userInfo.setUserGender(UserInfo.Gender.BOY);
////				dialog.dismiss();
////			}
////		});
////
////	}
//	
//	/**鏀瑰彉鐢ㄦ埛淇℃伅*/
//	private void showChangeInfo(final ChangeUserType type){
//		String title;
//		String content;
//		String hint;
//		if(type == ChangeUserType.USER_NAME){
//			content = tvUserName.getText().toString();
//			title = getContext().getString(R.string.tpl_change_user_name_title);
//			hint = getContext().getString(R.string.tpl_input_user_name_hint);
//		}else{
//			content = tvUserNote.getText().toString();
//			title = getContext().getString(R.string.tpl_change_user_note_title);
//			hint = getContext().getString(R.string.tpl_input_user_note_hint);
//		}
//
//		View dlgView = View.inflate(activity, R.layout.tpl_change_userinfo_dialog, null);
//		LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//		
//		final Dialog dialog = new Dialog(getContext(), R.style.WhiteDialog);
//		dialog.setContentView(dlgView, layoutParams);
//		final TextView tvTitle = (TextView) dialog.findViewById(R.id.dialog_tv_title);
//		final EditText etInfo = (EditText) dialog.findViewById(R.id.dialog_ev_info);
//		final TextView tvHint = (TextView) dialog.findViewById(R.id.dialog_tv_hint);
//		tvTitle.setText(title);
//		etInfo.setText(content);
//		tvHint.setText(hint);
//		dialog.findViewById(R.id.dialog_btn_save).setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {	
//				String content = etInfo.getText().toString();
//				if(type == ChangeUserType.USER_NAME){
//					tvUserName.setText(content);
//					userInfo.setUserName(content);
//				}else{
//					tvUserNote.setText(content);
//					userInfo.setUserNote(content);
//				}
//				dialog.dismiss();
//			}
//		});
//		dialog.show();
//	}
//	
//	//鍥剧墖鍘嬬缉
//	private Bitmap compressImageFromFile(String srcPath) {
//		BitmapFactory.Options newOpts = new BitmapFactory.Options();
//		newOpts.inJustDecodeBounds = true;//鍙杈�,涓嶈鍐呭
//		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
//
//		newOpts.inJustDecodeBounds = false;
//		int w = newOpts.outWidth;
//		int h = newOpts.outHeight;
//		float hh = 800f;//
//		float ww = 480f;//
//		int be = 1;
//		if (w > h && w > ww) {
//			be = (int) (newOpts.outWidth / ww);
//		} else if (w < h && h > hh) {
//			be = (int) (newOpts.outHeight / hh);
//		}
//		if (be <= 0)
//			be = 1;
//		newOpts.inSampleSize = be;//璁剧疆閲囨牱鐜�
//		
//		newOpts.inPreferredConfig = Config.ARGB_8888;//璇ユā寮忔槸榛樿鐨�,鍙笉璁�
//		newOpts.inPurgeable = true;// 鍚屾椂璁剧疆鎵嶄細鏈夋晥
//		newOpts.inInputShareable = true;//銆傚綋绯荤粺鍐呭瓨涓嶅鏃跺�欏浘鐗囪嚜鍔ㄨ鍥炴敹
//		
//		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
////		return compressBmpFromBmp(bitmap);//鍘熸潵鐨勬柟娉曡皟鐢ㄤ簡杩欎釜鏂规硶浼佸浘杩涜浜屾鍘嬬缉
//									//鍏跺疄鏄棤鏁堢殑,澶у灏界灏濊瘯
//		return bitmap;
//	}
//
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		
//	}
//}
