package com.juju.sport.common.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import com.google.gson.Gson;

public class JsonUtil {

	public static String getJsonStr(Object obj){
		Gson gson = new Gson();
		return gson.toJson(obj, obj.getClass());
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getObjFromJson(String jsonStr, Class cls){
		Gson gson = new Gson();
		return (T) gson.fromJson(jsonStr, cls);
		
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> getObjFromJsonArray(String arrayStr, Type cls){
		Gson gson = new Gson();
		T[] results = (T[]) gson.fromJson(arrayStr, cls);
		List<T> lists = new ArrayList<T>();
		if(ArrayUtils.isEmpty(results)){
			return lists;
		}
		
		for(T result : results){
			lists.add(result);
		}
		//return gson.fromJson(arrayStr, cls);
		return lists;
	}
}
