package com.example.androidbilling.billing_components.bill_save.pojo.paymentmode;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PaymentModeResponse{

	@SerializedName("result")
	private List<PaymentModeResultItem> result;

	@SerializedName("code")
	private int code;

	@SerializedName("message")
	private String message;

	public List<PaymentModeResultItem> getResult(){
		return result;
	}

	public int getCode(){
		return code;
	}

	public String getMessage(){
		return message;
	}
}