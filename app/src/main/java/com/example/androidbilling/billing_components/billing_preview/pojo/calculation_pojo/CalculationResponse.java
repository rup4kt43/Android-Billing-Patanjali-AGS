package com.example.androidbilling.billing_components.billing_preview.pojo.calculation_pojo;

import com.google.gson.annotations.SerializedName;

public class CalculationResponse{

	@SerializedName("result")
	private RecalculationResult recalculationResult;

	@SerializedName("code")
	private int code;

	@SerializedName("message")
	private String message;

	public RecalculationResult getRecalculationResult(){
		return recalculationResult;
	}

	public int getCode(){
		return code;
	}

	public String getMessage(){
		return message;
	}
}