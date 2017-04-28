package com.share.service;

import com.google.gson.Gson;
import com.share.domain.JsonObj;

public class JsonHandle {
	Gson gson = new Gson();

	public void deal(String json) {
		try {
			JsonObj jsonObj = gson.fromJson(json, JsonObj.class);
			if (jsonObj.getAct().equals("url")) {
				OpenUrl.open(jsonObj.getCode());
			}
		} catch (Exception e) {
		}
	}
}