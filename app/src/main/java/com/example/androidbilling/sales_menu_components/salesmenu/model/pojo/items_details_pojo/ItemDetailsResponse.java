package com.example.androidbilling.sales_menu_components.salesmenu.model.pojo.items_details_pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ItemDetailsResponse implements Serializable {

	@SerializedName("result")
	private ItemDetailsResult itemDetailsResult;

	@SerializedName("code")
	private String code;

	@SerializedName("message")
	private String message;

	public ItemDetailsResult getItemDetailsResult(){
		return itemDetailsResult;
	}

	public String getCode(){
		return code;
	}

	public String getMessage(){
		return message;
	}
}