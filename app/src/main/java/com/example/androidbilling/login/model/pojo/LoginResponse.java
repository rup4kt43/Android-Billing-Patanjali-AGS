package com.example.androidbilling.login.model.pojo;

import com.google.gson.annotations.SerializedName;

public class LoginResponse{



	@SerializedName("result")
	private Result result;

	@SerializedName("code")
	private int code;

	@SerializedName("message")
	private String message;

	public Result getResult(){
		return result;
	}

	public int getCode(){
		return code;
	}

	public String getMessage(){
		return message;
	}




}