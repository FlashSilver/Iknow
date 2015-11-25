package com.qiadao.tool;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

public class JsonUtil {	  
	      /**
	       * ��java���͵Ķ���ת��ΪJSON��ʽ���ַ���
	     * @param object java���͵Ķ���
	11      * @return JSON��ʽ���ַ���
	12      */
	     public static <T> String serialize(T object) {
	         return JSON.toJSONString(object);
	     }
	
	     /**
	18      * ��JSON��ʽ���ַ���ת��Ϊjava���͵Ķ������java�������͵Ķ��󣬲�����java��������
	19      * @param json JSON��ʽ���ַ���
	20      * @param clz java���ͻ���java�������ͣ�������java��������
	21      * @return java���͵Ķ������java�������͵Ķ��󣬲�����java�������͵Ķ���
	22      */
	    public static <T> T deserialize(String json, Class<T> clz) {
	         return JSON.parseObject(json, clz);
	     }
	 
	     /**
	28      * ��JSON��ʽ���ַ���ת��ΪList<T>���͵Ķ���
	29      * @param json JSON��ʽ���ַ���
	30      * @param clz ָ�����ͼ��������T����
	31      * @return List<T>���͵Ķ���
	32      */
	     public static <T> List<T> deserializeList(String json, Class<T> clz) {
	         return JSON.parseArray(json, clz);
	    }
	 
	     /**
	38      * ��JSON��ʽ���ַ���ת��������Java���͵Ķ���
	39      * @param json JSON��ʽ���ַ���
	40      * @param type ����Java����
	41      * @return ����Java���͵Ķ���
	42      */
	     public static <T> T deserializeAny(String json, TypeReference<T> type) {
	         return JSON.parseObject(json, type);
	     }
	
}
