package com.qiadao.chat;

import com.qiadao.tool.HttpUtils2;

import android.test.AndroidTestCase;

public class Test extends AndroidTestCase
{
	public void testSendMsg()
	{
		HttpUtils2.sendMsg("西斜七路堵车�?");
		HttpUtils2.sendMsg("你好");
		HttpUtils2.sendMsg("讲个笑话");
		HttpUtils2.sendMsg("新浪体育");
	}

}
