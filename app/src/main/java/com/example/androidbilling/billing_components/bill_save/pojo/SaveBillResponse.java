package com.example.androidbilling.billing_components.bill_save.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SaveBillResponse implements Serializable {

	@SerializedName("code")
	private int statusCode;

	@SerializedName("result")
	private String result;

	@SerializedName("result2")
	private String result2;

	@SerializedName("status")
	private String status;

	public String getResult(){
		return result;
	}

	public String getStatus(){
		return status;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getResult2() {
		return result2;
	}

	public void setResult2(String result2) {
		this.result2 = result2;
	}
}