package com.example.androidbilling.sales_menu_components.salesmenu.model.pojo.items_details_pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BatchesItem implements Serializable {


	@SerializedName("IndDiscount")
	private double individualDiscount=0.00;

	@SerializedName("IndDiscountRate")
	private double individualDiscountRate=0.00;

	@SerializedName("Quantity")
	private int quantity=1;

	@SerializedName("GstRate")
	private double gstRate;

	@SerializedName("AltIndDiscount")
	private double altIndDiscount;

	@SerializedName("FlatDiscount")
	private double flatDiscount= 0.00;

	@SerializedName("UNIT")
	private String unit;

	@SerializedName("SRATE")
	private double sRate;

	@SerializedName("WAREHOUSE")
	private String warehouse;

	@SerializedName("MCODE")
	private String mcode;

	@SerializedName("MFGDATE")
	private String mfgDate;

	@SerializedName("EXPIRY")
	private String expiry;

	@SerializedName("GST")
	private double gst;

	@SerializedName("MRP")
	private double mrp;

	@SerializedName("MCAT")
	private String mcat;

	@SerializedName("STOCK")
	private int stock;

	@SerializedName("DIVISION")
	private String division;

	@SerializedName("BCODE")
	private String bcode;

	@SerializedName("DESCA")
	private String desca;

	@SerializedName("HOLDINGSTOCK")
	private int holdingstock;

	@SerializedName("COSTPRICE")
	private double costprice;

	@SerializedName("BATCH")
	private String batch;

	@SerializedName("ID")
	private String id;

	@SerializedName("STAMP")
	private String stamp;

	@SerializedName("PRATE")
	private double prate;





	public double getIndividualDiscount() {
		return individualDiscount;
	}

	public void setIndividualDiscount(double individualDiscount) {
		this.individualDiscount = individualDiscount;
	}

	public double getIndividualDiscountRate() {
		return individualDiscountRate;
	}

	public void setIndividualDiscountRate(double individualDiscountRate) {
		this.individualDiscountRate = individualDiscountRate;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getsRate() {
		return sRate;
	}

	public void setsRate(double sRate) {
		this.sRate = sRate;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public String getMcode() {
		return mcode;
	}

	public void setMcode(String mcode) {
		this.mcode = mcode;
	}

	public String getMfgDate() {
		return mfgDate;
	}

	public void setMfgDate(String mfgDate) {
		this.mfgDate = mfgDate;
	}

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public double getGst() {
		return gst;
	}

	public void setGst(double gst) {
		this.gst = gst;
	}

	public double getMrp() {
		return mrp;
	}

	public void setMrp(double mrp) {
		this.mrp = mrp;
	}

	public String getMcat() {
		return mcat;
	}

	public void setMcat(String mcat) {
		this.mcat = mcat;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getBcode() {
		return bcode;
	}

	public void setBcode(String bcode) {
		this.bcode = bcode;
	}

	public String getDesca() {
		return desca;
	}

	public void setDesca(String desca) {
		this.desca = desca;
	}

	public int getHoldingstock() {
		return holdingstock;
	}

	public void setHoldingstock(int holdingstock) {
		this.holdingstock = holdingstock;
	}

	public double getCostprice() {
		return costprice;
	}

	public void setCostprice(double costprice) {
		this.costprice = costprice;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStamp() {
		return stamp;
	}

	public void setStamp(String stamp) {
		this.stamp = stamp;
	}


	public double getPrate() {
		return prate;
	}

	public void setPrate(double prate) {
		this.prate = prate;
	}

	public double getFlatDiscount() {
		return flatDiscount;
	}

	public void setFlatDiscount(double flatDiscount) {
		this.flatDiscount = flatDiscount;
	}

	public double getAltIndDiscount() {
		return altIndDiscount;
	}

	public void setAltIndDiscount(double altIndDiscount) {
		this.altIndDiscount = altIndDiscount;
	}

	public double getGstRate() {
		return gstRate;
	}

	public void setGstRate(double gstRate) {
		this.gstRate = gstRate;
	}
}