package com.example.androidbilling.login.model.pojo;

import com.google.gson.annotations.SerializedName;

public class Result{

	@SerializedName("userInfo")
	private UserInfo userInfo;

	@SerializedName("company")
	private Company company;

	public UserInfo getUserInfo(){
		return userInfo;
	}

	public Company getCompany(){
		return company;
	}
}