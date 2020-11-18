package com.example.androidbilling.billing_components.billing_preview.pojo.calculation_pojo;

import com.google.gson.annotations.SerializedName;

public class ProdListItem{

	@SerializedName("bc")
	private Object bc;

	@SerializedName("nontaxable")
	private int nontaxable;

	@SerializedName("inddiscountrate")
	private double inddiscountrate;

	@SerializedName("altqtY_IN")
	private int altqtYIN;

	@SerializedName("gst")
	private int gst;

	@SerializedName("discount")
	private double discount;

	@SerializedName("realqtY_IN")
	private int realqtYIN;

	@SerializedName("prate")
	private double prate;

	@SerializedName("originalQty")
	private double originalQty;

	@SerializedName("realQty")
	private double realQty;

	@SerializedName("itemdesc")
	private Object itemdesc;

	@SerializedName("rate")
	private double rate;

	@SerializedName("altrate")
	private double altrate;

	@SerializedName("flatdiscount")
	private int flatdiscount;

	@SerializedName("gstrate")
	private double gstrate;

	@SerializedName("altQty")
	private double altQty;

	@SerializedName("amount")
	private double amount;

	@SerializedName("quantity")
	private double quantity;

	@SerializedName("taxable")
	private double taxable;

	@SerializedName("batch")
	private String batch;

	@SerializedName("vat")
	private double vat;

	@SerializedName("weight")
	private int weight;

	@SerializedName("mrp")
	private double mrp;

	@SerializedName("warehouse")
	private String warehouse;

	@SerializedName("totalinddiscount")
	private double totalinddiscount;

	@SerializedName("altratE2")
	private double altratE2;

	@SerializedName("unit")
	private Object unit;

	@SerializedName("mfgdate")
	private String mfgdate;

	@SerializedName("mcode")
	private String mcode;

	@SerializedName("realrate")
	private double realrate;

	@SerializedName("altinddiscount")
	private double altinddiscount;

	@SerializedName("netamount")
	private double netamount;

	@SerializedName("inddiscount")
	private double inddiscount;

	@SerializedName("expdate")
	private String expdate;

	public Object getBc(){
		return bc;
	}

	public int getNontaxable(){
		return nontaxable;
	}

	public double getInddiscountrate(){
		return inddiscountrate;
	}

	public int getAltqtYIN(){
		return altqtYIN;
	}

	public int getGst(){
		return gst;
	}

	public double getDiscount(){
		return discount;
	}

	public int getRealqtYIN(){
		return realqtYIN;
	}

	public double getPrate(){
		return prate;
	}

	public double getOriginalQty(){
		return originalQty;
	}

	public double getRealQty(){
		return realQty;
	}

	public Object getItemdesc(){
		return itemdesc;
	}

	public double getRate(){
		return rate;
	}

	public double getAltrate(){
		return altrate;
	}

	public int getFlatdiscount(){
		return flatdiscount;
	}

	public double getGstrate(){
		return gstrate;
	}

	public double getAltQty(){
		return altQty;
	}

	public double getAmount(){
		return amount;
	}

	public double getQuantity(){
		return quantity;
	}

	public double getTaxable(){
		return taxable;
	}

	public String getBatch(){
		return batch;
	}

	public double getVat(){
		return vat;
	}

	public int getWeight(){
		return weight;
	}

	public double getMrp(){
		return mrp;
	}

	public String getWarehouse(){
		return warehouse;
	}

	public double getTotalinddiscount(){
		return totalinddiscount;
	}

	public double getAltratE2(){
		return altratE2;
	}

	public Object getUnit(){
		return unit;
	}

	public String getMfgdate(){
		return mfgdate;
	}

	public String getMcode(){
		return mcode;
	}

	public double getRealrate(){
		return realrate;
	}

	public double getAltinddiscount(){
		return altinddiscount;
	}

	public double getNetamount(){
		return netamount;
	}

	public double getInddiscount(){
		return inddiscount;
	}

	public String getExpdate(){
		return expdate;
	}
}