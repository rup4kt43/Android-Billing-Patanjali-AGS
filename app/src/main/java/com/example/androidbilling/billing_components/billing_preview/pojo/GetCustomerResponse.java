package com.example.androidbilling.billing_components.billing_preview.pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GetCustomerResponse{

	@SerializedName("result")
	private List<GetCustomerResultItem> result;

	@SerializedName("code")
	private int code;

	@SerializedName("message")
	private String message;

	public List<GetCustomerResultItem> getResult(){
		return result;
	}

	public int getCode(){
		return code;
	}

	public String getMessage(){
		return message;
	}
}