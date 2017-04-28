package com.share.util;

import com.google.gson.Gson;
import com.share.domain.JsonObj;

public class JsonHandle {
	private static Gson gson = new Gson();
	private static JsonObj jsonObj = null;
	
	public static JsonObj handle(String jsonString) {
		try {
			jsonObj = gson.fromJson(jsonString, JsonObj.class);
		} catch (Exception e) {
			
		}
		return jsonObj;
	}
}
