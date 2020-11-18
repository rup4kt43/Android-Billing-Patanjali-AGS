package com.example.androidbilling.sales_menu_components.salesmenu.model.pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ItemListResult {


	@SerializedName("itemList")
	private List<ItemListItem> itemList;

	@SerializedName("category")
	private List<String> category;

	public void setItemList(List<ItemListItem> itemList) {
		this.itemList = itemList;
	}

	public void setCategory(List<String> category) {
		this.category = category;
	}

	public List<ItemListItem> getItemList(){
		return itemList;
	}

	public List<String> getCategory(){
		return category;
	}
}