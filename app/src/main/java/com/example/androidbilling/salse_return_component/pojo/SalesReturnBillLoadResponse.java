package com.example.androidbilling.salse_return_component.pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SalesReturnBillLoadResponse{

	@SerializedName("result")
	private List<SalesReturnBillLoadResultItem> result;

	@SerializedName("code")
	private int code;

	@SerializedName("message")
	private String message;

	public List<SalesReturnBillLoadResultItem> getResult(){
		return result;
	}

	public int getCode(){
		return code;
	}

	public String getMessage(){
		return message;
	}
}