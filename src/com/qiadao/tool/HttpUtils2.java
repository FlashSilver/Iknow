package com.qiadao.tool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import com.google.gson.Gson;
import com.qiadao.bean.ChatMessage;
import com.qiadao.bean.ChatMessage.Type;
import com.qiadao.bean.CommonException;
import com.qiadao.bean.Result;



public class HttpUtils2
{
	private static String API_KEY = "534dc342ad15885dffc10d7b5f813451";
	private static String URL = "http://www.tuling123.com/openapi/api";

	/**
	 * 发�?�?��消息，并得到返回的消�?
	 * @param msg
	 * @return
	 */
	public static ChatMessage sendMsg(String msg)
	{
		ChatMessage message = new ChatMessage();
		String url = setParams(msg);
		String res = doGet(url);
		Gson gson = new Gson();
		Result result = gson.fromJson(res, Result.class);
		
		if (result.getCode() > 400000 || result.getText() == null
				|| result.getText().trim().equals(""))
		{
			message.setMsg("该功能等待开�?..");
		}else
		{
			message.setMsg(result.getText());
		}
		message.setType(Type.INPUT);
		message.setDate(new Date());
		
		return message;
	}

	/**
	 * 拼接Url
	 * @param msg
	 * @return
	 */
	private static String setParams(String msg)
	{
		try
		{
			msg = URLEncoder.encode(msg, "UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return URL + "?key=" + API_KEY + "&info=" + msg;
	}

	/**
	 * Get请求，获得返回数�?
	 * @param urlStr
	 * @return
	 */
	private static String doGet(String urlStr)
	{
		URL url = null;
		HttpURLConnection conn = null;
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try
		{
			url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(5 * 1000);
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() == 200)
			{
				is = conn.getInputStream();
				baos = new ByteArrayOutputStream();
				int len = -1;
				byte[] buf = new byte[128];

				while ((len = is.read(buf)) != -1)
				{
					baos.write(buf, 0, len);
				}
				baos.flush();
				return baos.toString();
			} else
			{
				throw new CommonException("服务器连接错误！");
			}

		} catch (Exception e)
		{
			e.printStackTrace();
			throw new CommonException("服务器连接错误！");
		} finally
		{
			try
			{
				if (is != null)
					is.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}

			try
			{
				if (baos != null)
					baos.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			conn.disconnect();
		}

	}

}
