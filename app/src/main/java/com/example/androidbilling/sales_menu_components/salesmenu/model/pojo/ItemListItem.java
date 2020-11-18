package com.example.androidbilling.sales_menu_components.salesmenu.model.pojo;

import com.google.gson.annotations.SerializedName;

public class ItemListItem{

	@SerializedName("VERTICAL")
	public String vERTICAL;

	@SerializedName("Quantity")
	public String quantity;

	@SerializedName("MCODE")
	public String mCODE;

	@SerializedName("DIVISIONS")
	public String dIVISIONS;

	@SerializedName("BARCODE")
	public String bARCODE;

	@SerializedName("DESCA")
	public String dESCA;

	@SerializedName("BRAND")
	public String bRAND;


	public void setvERTICAL(String vERTICAL) {
		this.vERTICAL = vERTICAL;
	}

	public void setmCODE(String mCODE) {
		this.mCODE = mCODE;
	}

	public void setdIVISIONS(String dIVISIONS) {
		this.dIVISIONS = dIVISIONS;
	}

	public void setbARCODE(String bARCODE) {
		this.bARCODE = bARCODE;
	}

	public void setdESCA(String dESCA) {
		this.dESCA = dESCA;
	}

	public void setbRAND(String bRAND) {
		this.bRAND = bRAND;
	}

	public String getVERTICAL(){
		return vERTICAL;
	}

	public String getMCODE(){
		return mCODE;
	}

	public String getDIVISIONS(){
		return dIVISIONS;
	}

	public String getBARCODE(){
		return bARCODE;
	}

	public String getDESCA(){
		return dESCA;
	}

	public String getBRAND(){
		return bRAND;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
}