package com.example.androidbilling.bill_load.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetBillResponse implements Serializable {

	@SerializedName("result")
	private GetBillResult getBillResult;

	@SerializedName("code")
	private int code;

	@SerializedName("message")
	private String message;

	public GetBillResult getGetBillResult(){
		return getBillResult;
	}

	public int getCode(){
		return code;
	}

	public String getMessage(){
		return message;
	}
}