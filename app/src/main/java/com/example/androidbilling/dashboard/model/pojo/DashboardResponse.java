package com.example.androidbilling.dashboard.model.pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DashboardResponse{

	@SerializedName("result")
	private List<DashboardResultItem> result;

	@SerializedName("code")
	private int code;

	@SerializedName("message")
	private String message;

	public List<DashboardResultItem> getResult(){
		return result;
	}

	public int getCode(){
		return code;
	}

	public String getMessage(){
		return message;
	}
}