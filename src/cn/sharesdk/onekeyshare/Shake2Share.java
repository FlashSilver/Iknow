/*
 * 瀹樼綉鍦扮珯:http://www.mob.com
 * 鎶�湳鏀寔QQ: 4006852216
 * 瀹樻柟寰俊:ShareSDK   锛堝鏋滃彂甯冩柊鐗堟湰鐨勮瘽锛屾垜浠皢浼氱涓�椂闂撮�杩囧井淇″皢鐗堟湰鏇存柊鍐呭鎺ㄩ�缁欐偍銆傚鏋滀娇鐢ㄨ繃绋嬩腑鏈変换浣曢棶棰橈紝涔熷彲浠ラ�杩囧井淇′笌鎴戜滑鍙栧緱鑱旂郴锛屾垜浠皢浼氬湪24灏忔椂鍐呯粰浜堝洖澶嶏級
 *
 * Copyright (c) 2013骞�mob.com. All rights reserved.
 */

package cn.sharesdk.onekeyshare;

import static cn.sharesdk.framework.utils.R.*;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.FloatMath;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import cn.sharesdk.framework.FakeActivity;

/** 鎽囦竴鎽囧惎鍔ㄥ垎浜殑渚嬪瓙 */
public class Shake2Share extends FakeActivity implements SensorEventListener {
	// 妫�祴鐨勬椂闂撮棿闅�	
	private static final int UPDATE_INTERVAL = 100;
	// 鎽囨檭妫�祴闃堝�锛屽喅瀹氫簡瀵规憞鏅冪殑鏁忔劅绋嬪害锛岃秺灏忚秺鏁忔劅
	private static final int SHAKE_THRESHOLD = 1500;

	private OnShakeListener listener;
	private SensorManager mSensorManager;
	private long mLastUpdateTime;
	private float mLastX;
	private float mLastY;
	private float mLastZ;
	private boolean shaken;

	public void setOnShakeListener(OnShakeListener listener) {
		this.listener = listener;
	}

	public void setActivity(Activity activity) {
		super.setActivity(activity);
		int resId = getBitmapRes(activity, "ssdk_oks_shake_to_share_back");
		if (resId > 0) {
			activity.setTheme(android.R.style.Theme_Dialog);
			activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
			Window win = activity.getWindow();
			win.setBackgroundDrawableResource(resId);
		}
	}

	public void onCreate() {
		startSensor();

		int resId = getBitmapRes(activity, "ssdk_oks_yaoyiyao");
		if (resId > 0) {
			ImageView iv = new ImageView(activity);
			iv.setScaleType(ScaleType.CENTER_INSIDE);
			iv.setImageResource(resId);
			activity.setContentView(iv);
		}

		resId = getStringRes(activity, "shake2share");
		if (resId > 0) {
			Toast.makeText(activity, resId, Toast.LENGTH_SHORT).show();
		}
	}

	private void startSensor() {
		mSensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
		if (mSensorManager == null) {
			throw new UnsupportedOperationException();
		}
		Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		if (sensor == null) {
			throw new UnsupportedOperationException();
		}
		boolean success = mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
		if (!success) {
			throw new UnsupportedOperationException();
		}
	}

	public void onDestroy() {
		stopSensor();
	}

	private void stopSensor() {
		if (mSensorManager != null) {
			mSensorManager.unregisterListener(this);
			mSensorManager = null;
		}
	}

	public void onSensorChanged(SensorEvent event) {
		long currentTime = System.currentTimeMillis();
		long diffTime = currentTime - mLastUpdateTime;
		if (diffTime > UPDATE_INTERVAL) {
			if(mLastUpdateTime != 0) {
				float x = event.values[0];
				float y = event.values[1];
				float z = event.values[2];
				float deltaX = x - mLastX;
				float deltaY = y - mLastY;
				float deltaZ = z - mLastZ;
				float delta = FloatMath.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) / diffTime * 10000;
				if (delta > SHAKE_THRESHOLD) {
					if (!shaken) {
						shaken = true;
						finish();
					}

					if (listener != null) {
						listener.onShake();
					}
				}
				mLastX = x;
				mLastY = y;
				mLastZ = z;
			}
			mLastUpdateTime = currentTime;
		}
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	public static interface OnShakeListener {
		public void onShake();
	}

}
