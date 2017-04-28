package com.share.domain;

public class JsonObj {
	private String act;
	private String code;
	public String getAct() {
		return act;
	}
	public void setAct(String act) {
		this.act = act;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public JsonObj(String act, String code) {
		super();
		this.act = act;
		this.code = code;
	}
	public JsonObj() {
		super();
	}
	
}
