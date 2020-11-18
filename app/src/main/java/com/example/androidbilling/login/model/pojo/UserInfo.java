package com.example.androidbilling.login.model.pojo;

import com.google.gson.annotations.SerializedName;

public class UserInfo{

	@SerializedName("ROLE")
	private String rOLE;

	@SerializedName("UNAME")
	private String uNAME;

	public UserInfo(String rOLE, String uNAME) {
		this.rOLE = rOLE;
		this.uNAME = uNAME;
	}

	public String getROLE(){
		return rOLE;
	}

	public String getUNAME(){
		return uNAME;
	}
}