package com.example.androidbilling.sales_menu_components.salesmenu.model.pojo;

import com.google.gson.annotations.SerializedName;

public class ItemListResponse{


	@SerializedName("result")
	private ItemListResult itemListResult;

	@SerializedName("code")
	private int code;

	@SerializedName("message")
	private String message;

	public ItemListResult getItemListResult(){
		return itemListResult;
	}

	public int getCode(){
		return code;
	}

	public String getMessage(){
		return message;
	}
}