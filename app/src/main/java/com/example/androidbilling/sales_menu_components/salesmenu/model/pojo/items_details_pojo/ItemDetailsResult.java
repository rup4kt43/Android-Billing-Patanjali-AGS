package com.example.androidbilling.sales_menu_components.salesmenu.model.pojo.items_details_pojo;

import java.io.Serializable;
import java.util.List;

import com.example.androidbilling.billing_components.billing_preview.dto.BillingCalculationDTO;
import com.google.gson.annotations.SerializedName;

public class ItemDetailsResult implements Serializable {


	@SerializedName("totalBillingDetails")
	private BillingCalculationDTO billingCalculationDTO;

	@SerializedName("batches")
	private List<BatchesItem> batches;

	@SerializedName("billingType")
	private String billingType;

	public List<BatchesItem> getBatches(){
		return batches;
	}

	public String getBillingType(){
		return billingType;
	}

	public void setBatches(List<BatchesItem> batches) {
		this.batches = batches;
	}

	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}

	public BillingCalculationDTO getBillingCalculationDTO() {
		return billingCalculationDTO;
	}

	public void setBillingCalculationDTO(BillingCalculationDTO billingCalculationDTO) {
		this.billingCalculationDTO = billingCalculationDTO;
	}
}