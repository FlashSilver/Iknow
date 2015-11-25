package com.qiadao.tool;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

public class JsonUtil {	  
	      /**
	       * 将java类型的对象转换为JSON格式的字符串
	     * @param object java类型的对象
	11      * @return JSON格式的字符串
	12      */
	     public static <T> String serialize(T object) {
	         return JSON.toJSONString(object);
	     }
	
	     /**
	18      * 将JSON格式的字符串转换为java类型的对象或者java数组类型的对象，不包括java集合类型
	19      * @param json JSON格式的字符串
	20      * @param clz java类型或者java数组类型，不包括java集合类型
	21      * @return java类型的对象或者java数组类型的对象，不包括java集合类型的对象
	22      */
	    public static <T> T deserialize(String json, Class<T> clz) {
	         return JSON.parseObject(json, clz);
	     }
	 
	     /**
	28      * 将JSON格式的字符串转换为List<T>类型的对象
	29      * @param json JSON格式的字符串
	30      * @param clz 指定泛型集合里面的T类型
	31      * @return List<T>类型的对象
	32      */
	     public static <T> List<T> deserializeList(String json, Class<T> clz) {
	         return JSON.parseArray(json, clz);
	    }
	 
	     /**
	38      * 将JSON格式的字符串转换成任意Java类型的对象
	39      * @param json JSON格式的字符串
	40      * @param type 任意Java类型
	41      * @return 任意Java类型的对象
	42      */
	     public static <T> T deserializeAny(String json, TypeReference<T> type) {
	         return JSON.parseObject(json, type);
	     }
	
}
