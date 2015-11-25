/*
 * 瀹樼綉鍦扮珯:http://www.mob.com
 * 鎶�鏈敮鎸丵Q: 4006852216
 * 瀹樻柟寰俊:ShareSDK   锛堝鏋滃彂甯冩柊鐗堟湰鐨勮瘽锛屾垜浠皢浼氱涓�鏃堕棿閫氳繃寰俊灏嗙増鏈洿鏂板唴瀹规帹閫佺粰鎮ㄣ�傚鏋滀娇鐢ㄨ繃绋嬩腑鏈変换浣曢棶棰橈紝涔熷彲浠ラ�氳繃寰俊涓庢垜浠彇寰楄仈绯伙紝鎴戜滑灏嗕細鍦�24灏忔椂鍐呯粰浜堝洖澶嶏級
 * 
 * Copyright (c) 2013骞� mob.com. All rights reserved.
 */
package cn.sharesdk.tpl;

//#if def{sdk.debugable}
import com.qiadao.iknow.R;

import android.app.Dialog;
import android.content.Context;

public class CommonDialog {
	
	/**鍔犺浇瀵硅瘽妗�*/
	public static final Dialog ProgressDialog(Context context){
//		int resId = getStyleRes(context, "CommonDialog");
//		if (resId > 0) {
//			final Dialog dialog = new Dialog(context, resId);
//			resId = getLayoutRes(context, "tpl_progress_dialog");
//			if (resId > 0) {
//				dialog.setContentView(resId);
//				return dialog;
//			}
//		}
//		return null;
		
		final Dialog dialog = new Dialog(context, R.style.CommonDialog);
		dialog.setContentView(R.layout.tpl_progress_dialog);
		return dialog;
	}
	
}
