package com.qiadao.tool;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import junit.framework.Assert;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class QQLonginUtil {

	private static final String TAG = "SDK_Sample.Util";
	private static Dialog mProgressDialog;
	private static Toast mToast;

	/*
	 * Convert byte[] to hex
	 * string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串�??
	 * 
	 * @param src byte[] data
	 * 
	 * @return hex string
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * Convert hex string to byte[]
	 * 
	 * @param hexString
	 *            the hex string
	 * @return byte[]
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * Convert char to byte
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/*
	 * 16进制数字字符�?
	 */
	private static String hexString = "0123456789ABCDEF";

	/*
	 * 将字符串编码�?16进制数字,适用于所有字符（包括中文�?
	 */
	public static String toHexString(String str) {
		// 根据默认编码获取字节数组
		byte[] bytes = str.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解�?2�?16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	// 转换十六进制编码为字符串
	public static String hexToString(String s) {
		if ("0x".equals(s.substring(0, 2))) {
			s = s.substring(2);
		}
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(
						s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "utf-8");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}

	public static byte[] bmpToByteArray(final Bitmap bmp,
			final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}
		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static byte[] getHtmlByteArray(final String url) {
		URL htmlUrl = null;
		InputStream inStream = null;
		try {
			htmlUrl = new URL(url);
			URLConnection connection = htmlUrl.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				inStream = httpConnection.getInputStream();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] data = inputStreamToByte(inStream);
		return data;
	}

	public static byte[] inputStreamToByte(InputStream is) {
		try {
			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			int ch;
			while ((ch = is.read()) != -1) {
				bytestream.write(ch);
			}
			byte imgdata[] = bytestream.toByteArray();
			bytestream.close();
			return imgdata;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] readFromFile(String fileName, int offset, int len) {
		if (fileName == null) {
			return null;
		}

		File file = new File(fileName);
		if (!file.exists()) {
			Log.i(TAG, "readFromFile: file not found");
			return null;
		}
		if (len == -1) {
			len = (int) file.length();
		}
		Log.d(TAG, "readFromFile : offset = " + offset + " len = " + len
				+ " offset + len = " + (offset + len));
		if (offset < 0) {
			Log.e(TAG, "readFromFile invalid offset:" + offset);
			return null;
		}
		if (len <= 0) {
			Log.e(TAG, "readFromFile invalid len:" + len);
			return null;
		}
		if (offset + len > (int) file.length()) {
			Log.e(TAG, "readFromFile invalid file len:" + file.length());
			return null;
		}
		byte[] b = null;
		try {
			RandomAccessFile in = new RandomAccessFile(fileName, "r");
			b = new byte[len];
			in.seek(offset);
			in.readFully(b);
			in.close();
		} catch (Exception e) {
			Log.e(TAG, "readFromFile : errMsg = " + e.getMessage());
			e.printStackTrace();
		}
		return b;
	}

	public static int computeSampleSize(BitmapFactory.Options options,

	int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,maxNumOfPixels);
		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}
		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}
		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	/**
	 * 以最省内存的方式读取图片
	 */
	public static Bitmap readBitmap(final String path) {
		try {
			FileInputStream stream = new FileInputStream(new File(path + "test.jpg"));
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inSampleSize = 8;
			opts.inPurgeable = true;
			opts.inInputShareable = true;
			Bitmap bitmap = BitmapFactory.decodeStream(stream, null, opts);
			return bitmap;
		} catch (Exception e) {
			return null;
		}
	}

	private static final int MAX_DECODE_PICTURE_SIZE = 1920 * 1440;

	public static Bitmap extractThumbNail(final String path, final int height,final int width, final boolean crop) {
		Assert.assertTrue(path != null && !path.equals("") && height > 0 && width > 0);
		BitmapFactory.Options options = new BitmapFactory.Options();
		try {
			options.inJustDecodeBounds = true;
			Bitmap tmp = BitmapFactory.decodeFile(path, options);
			if (tmp != null) {
				tmp.recycle();
				tmp = null;
			}
			Log.d(TAG, "extractThumbNail: round=" + width + "x" + height + ", crop=" + crop);
			final double beY = options.outHeight * 1.0 / height;
			final double beX = options.outWidth * 1.0 / width;
			Log.d(TAG, "extractThumbNail: extract beX = " + beX + ", beY = " + beY);
			options.inSampleSize = (int) (crop ? (beY > beX ? beX : beY) : (beY < beX ? beX : beY));
			if (options.inSampleSize <= 1) {
				options.inSampleSize = 1;
			}

			// NOTE: out of memory error
			while (options.outHeight * options.outWidth / options.inSampleSize > MAX_DECODE_PICTURE_SIZE) {
				options.inSampleSize++;
			}
			int newHeight = height;
			int newWidth = width;
			if (crop) {
				if (beY > beX) {
					newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
				} else {
					newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
				}
			} else {
				if (beY < beX) {
					newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
				} else {
					newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
				}
			}
			options.inJustDecodeBounds = false;
			Log.i(TAG, "bitmap required size=" + newWidth + "x" + newHeight + ", orig=" + options.outWidth + "x" + options.outHeight+ ", sample=" + options.inSampleSize);
			Bitmap bm = BitmapFactory.decodeFile(path, options);
			if (bm == null) {
				Log.e(TAG, "bitmap decode failed");
				return null;
			}
			Log.i(TAG,"bitmap decoded size=" + bm.getWidth() + "x"+ bm.getHeight());
			final Bitmap scale = Bitmap.createScaledBitmap(bm, newWidth,newHeight, true);
			if (scale != null) {
				bm.recycle();
				bm = scale;
			}

			if (crop) {
				final Bitmap cropped = Bitmap.createBitmap(bm,(bm.getWidth() - width) >> 1,(bm.getHeight() - height) >> 1, width, height);
				if (cropped == null) {
					return bm;
				}
				bm.recycle();
				bm = cropped;
				Log.i(TAG,"bitmap croped size=" + bm.getWidth() + "x"+ bm.getHeight());
			}
			return bm;
		} catch (final OutOfMemoryError e) {
			Log.e(TAG, "decode bitmap failed: " + e.getMessage());
			options = null;
		}
		return null;
	}

	public static final void showResultDialog(Context context, String msg,String title) {
		if (msg == null)
			return;
		String rmsg = msg.replace(",", "\n");
		Log.d("Util", rmsg);
		new AlertDialog.Builder(context).setTitle(title).setMessage(rmsg).setNegativeButton("知道�?", null).create().show();
	}

	public static final void showProgressDialog(Context context, String title,
			String message) {
		dismissDialog();
		if (TextUtils.isEmpty(title)) {
			title = "请稍�?";
		}
		if (TextUtils.isEmpty(message)) {
			message = "正在加载...";
		}
		mProgressDialog = ProgressDialog.show(context, title, message);
	}

	public static final void dismissDialog() {
		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
	}

	/**
	 * 打印消息并且用Toast显示消息
	 * 
	 * @param activity
	 * @param message
	 * @param logLevel
	 *            填d, w, e分别代表debug, warn, error; 默认是debug
	 */
	public static final void toastMessage(final Activity activity,final String message, String logLevel) {
		if ("w".equals(logLevel)) {
			Log.w("sdkDemo", message);
		} else if ("e".equals(logLevel)) {
			Log.e("sdkDemo", message);
		} else {
			Log.d("sdkDemo", message);
		}
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (mToast != null) {
					mToast.cancel();
					mToast = null;
				}
				mToast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
				mToast.show();
			}
		});
	}

	/**
	 * 打印消息并且用Toast显示消息
	 * 
	 * @param activity
	 * @param message
	 * @param logLevel
	 *            填d, w, e分别代表debug, warn, error; 默认是debug
	 */
	public static final void toastMessage(final Activity activity,final String message) {
		toastMessage(activity, message, null);
	}

	/**
	 * 根据�?个网络连�?(String)获取bitmap图像
	 * 
	 * @param imageUri
	 * @return
	 * @throws MalformedURLException
	 */
	public static Bitmap getbitmap(String imageUri) {
		Log.v(TAG, "getbitmap:" + imageUri);
		// 显示网络上的图片
		Bitmap bitmap = null;
		try {
			URL myFileUrl = new URL(imageUri);
			HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
			Log.v(TAG, "image download finished." + imageUri);
		} catch (IOException e) {
			e.printStackTrace();
			Log.v(TAG, "getbitmap bmp fail---");
			return null;
		}
		return bitmap;
	}
}
