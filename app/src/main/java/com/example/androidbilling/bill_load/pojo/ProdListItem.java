package com.example.androidbilling.bill_load.pojo;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProdListItem implements Serializable {

	@SerializedName("bc")
	private String bc;

	@SerializedName("totalinddiscount")
	private double totalIntDiscount;

	@SerializedName("OriginalQty")
	private double originalQty;

	@SerializedName("discount")
	private double discount;


	@SerializedName("netamount")
	private double netAmount;

	@SerializedName("gst")
	private double gst;

	@SerializedName("trnMode")
	private String trnMode;

	@SerializedName("gstrate")
	private double gstrate;

	@Nullable
	@SerializedName("PARAC")
	private String parAc;

	@SerializedName("quantity")
	private double quantity;

	@SerializedName("batch")
	private String batch;

	@SerializedName("inddiscountrate")
	private int inddiscountrate;

	@SerializedName("mrp")
	private double mrp;

	@SerializedName("warehouse")
	private String warehouse;

	@SerializedName("prate")
	private double prate;

	@SerializedName("itemdesc")
	private String itemdesc;

	@SerializedName("unit")
	private String unit;

	@SerializedName("mfgdate")
	private String mfgdate;

	@SerializedName("mcode")
	private String mcode;

	@SerializedName("rate")
	private double rate;

	@SerializedName("flatdiscount")
	private double flatdiscount;

	@SerializedName("inddiscount")
	private double inddiscount;

	@SerializedName("expdate")
	private String expdate;

	@SerializedName("altinddiscount")
	private double altIndDiscount;

	@SerializedName("realqty")
	private int realQty;

	public double getAltIndDiscount() {
		return altIndDiscount;
	}

	public void setAltIndDiscount(double altIndDiscount) {
		this.altIndDiscount = altIndDiscount;
	}

	public int getRealQty() {
		return realQty;
	}

	public void setRealQty(int realQty) {
		this.realQty = realQty;
	}

	public String getBc(){
		return bc;
	}

	public double getQuantity(){
		return quantity;
	}

	public String getBatch(){
		return batch;
	}

	public int getInddiscountrate(){
		return inddiscountrate;
	}

	public double getMrp(){
		return mrp;
	}

	public String getWarehouse(){
		return warehouse;
	}

	public double getPrate(){
		return prate;
	}

	public String getItemdesc(){
		return itemdesc;
	}

	public String getUnit(){
		return unit;
	}

	public String getMfgdate(){
		return mfgdate;
	}

	public String getMcode(){
		return mcode;
	}

	public double getRate(){
		return rate;
	}

	public double getFlatdiscount(){
		return flatdiscount;
	}

	public double getInddiscount(){
		return inddiscount;
	}

	public String getExpdate(){
		return expdate;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}


	public void setBc(String bc) {
		this.bc = bc;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public void setInddiscountrate(int inddiscountrate) {
		this.inddiscountrate = inddiscountrate;
	}

	public void setMrp(double mrp) {
		this.mrp = mrp;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public void setPrate(double prate) {
		this.prate = prate;
	}

	public void setItemdesc(String itemdesc) {
		this.itemdesc = itemdesc;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setMfgdate(String mfgdate) {
		this.mfgdate = mfgdate;
	}

	public void setMcode(String mcode) {
		this.mcode = mcode;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public void setFlatdiscount(double flatdiscount) {
		this.flatdiscount = flatdiscount;
	}

	public void setInddiscount(double inddiscount) {
		this.inddiscount = inddiscount;
	}

	public void setExpdate(String expdate) {
		this.expdate = expdate;
	}



	//THIS PART IS FOR BILL LOAD FOR SALES RETURN

	public String billTo;
	public String billToAdd;
	public String billToMob;
	public String trnac;
	public double mainQuantity;


	public double getMainQuantity() {
		return mainQuantity;
	}

	public void setMainQuantity(double mainQuantity) {
		this.mainQuantity = mainQuantity;
	}

	public String getBillTo() {
		return billTo;
	}

	public void setBillTo(String billTo) {
		this.billTo = billTo;
	}

	public String getBillToAdd() {
		return billToAdd;
	}

	public void setBillToAdd(String billToAdd) {
		this.billToAdd = billToAdd;
	}

	public String getBillToMob() {
		return billToMob;
	}

	public void setBillToMob(String billToMob) {
		this.billToMob = billToMob;
	}

	public String getTrnac() {
		return trnac;
	}

	public void setTrnac(String trnac) {
		this.trnac = trnac;
	}


	public double getGst() {
		return gst;
	}

	public void setGst(double gst) {
		this.gst = gst;
	}


	public double getGstrate() {
		return gstrate;
	}

	public void setGstrate(double gstrate) {
		this.gstrate = gstrate;
	}

	public String getTrnMode() {
		return trnMode;
	}

	public void setTrnMode(String trnMode) {
		this.trnMode = trnMode;
	}

	public String getParAc() {
		return parAc;
	}

	public void setParAc(String parAc) {
		this.parAc = parAc;
	}


	public double getTotalIntDiscount() {
		return totalIntDiscount;
	}

	public void setTotalIntDiscount(double totalIntDiscount) {
		this.totalIntDiscount = totalIntDiscount;
	}

	public double getOriginalQty() {
		return originalQty;
	}

	public void setOriginalQty(double originalQty) {
		this.originalQty = originalQty;
	}


	public double getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}


	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}
}