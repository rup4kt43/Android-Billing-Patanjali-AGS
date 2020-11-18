package com.example.androidbilling.billing_components.billing_preview.pojo.calculation_pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RecalculationResult {

	@SerializedName("mwarehouse")
	private Object mwarehouse;

	@SerializedName("totalweight")
	private int totalweight;

	@SerializedName("totaldiscount")
	private double totaldiscount;

	@SerializedName("nontaxable")
	private int nontaxable;

	@SerializedName("totamnt")
	private double totamnt;

	@SerializedName("vchrno")
	private Object vchrno;

	@SerializedName("billtotel")
	private Object billtotel;

	@SerializedName("editUser")
	private Object editUser;

	@SerializedName("trnac")
	private Object trnac;

	@SerializedName("prodList")
	private List<ProdListItem> prodList;

	@SerializedName("deviceId")
	private Object deviceId;

	@SerializedName("billtoadd")
	private Object billtoadd;

	@SerializedName("trnmode")
	private Object trnmode;

	@SerializedName("division")
	private Object division;

	@SerializedName("phiscalId")
	private Object phiscalId;

	@SerializedName("mode")
	private String mode;

	@SerializedName("companyid")
	private String companyid;

	@SerializedName("netamnt")
	private int netamnt;

	@SerializedName("parac")
	private Object parac;

	@SerializedName("trnuser")
	private Object trnuser;

	@SerializedName("gstrate")
	private int gstrate;

	@SerializedName("tender")
	private double tender;

	@SerializedName("taxable")
	private double taxable;

	@SerializedName("voucherabbname")
	private String voucherabbname;

	@SerializedName("tenderList")
	private Object tenderList;

	@SerializedName("netwithoutroundoff")
	private double netwithoutroundoff;

	@SerializedName("terminal")
	private Object terminal;

	@SerializedName("totalinddiscount")
	private double totalinddiscount;

	@SerializedName("vatamnt")
	private double vatamnt;

	@SerializedName("billtomob")
	private Object billtomob;

	@SerializedName("refbill")
	private Object refbill;

	@SerializedName("dcamnt")
	private double dcamnt;

	@SerializedName("billto")
	private Object billto;

	@SerializedName("guid")
	private Object guid;

	@SerializedName("chalanno")
	private Object chalanno;

	@SerializedName("roundOff")
	private double roundOff;

	@SerializedName("status")
	private int status;

	public Object getMwarehouse(){
		return mwarehouse;
	}

	public int getTotalweight(){
		return totalweight;
	}

	public double getTotaldiscount(){
		return totaldiscount;
	}

	public int getNontaxable(){
		return nontaxable;
	}

	public double getTotamnt(){
		return totamnt;
	}

	public Object getVchrno(){
		return vchrno;
	}

	public Object getBilltotel(){
		return billtotel;
	}

	public Object getEditUser(){
		return editUser;
	}

	public Object getTrnac(){
		return trnac;
	}

	public List<ProdListItem> getProdList(){
		return prodList;
	}

	public Object getDeviceId(){
		return deviceId;
	}

	public Object getBilltoadd(){
		return billtoadd;
	}

	public Object getTrnmode(){
		return trnmode;
	}

	public Object getDivision(){
		return division;
	}

	public Object getPhiscalId(){
		return phiscalId;
	}

	public String getMode(){
		return mode;
	}

	public String getCompanyid(){
		return companyid;
	}

	public int getNetamnt(){
		return netamnt;
	}

	public Object getParac(){
		return parac;
	}

	public Object getTrnuser(){
		return trnuser;
	}

	public int getGstrate(){
		return gstrate;
	}

	public double getTender(){
		return tender;
	}

	public double getTaxable(){
		return taxable;
	}

	public String getVoucherabbname(){
		return voucherabbname;
	}

	public Object getTenderList(){
		return tenderList;
	}

	public double getNetwithoutroundoff(){
		return netwithoutroundoff;
	}

	public Object getTerminal(){
		return terminal;
	}

	public double getTotalinddiscount(){
		return totalinddiscount;
	}

	public double getVatamnt(){
		return vatamnt;
	}

	public Object getBilltomob(){
		return billtomob;
	}

	public Object getRefbill(){
		return refbill;
	}

	public double getDcamnt(){
		return dcamnt;
	}

	public Object getBillto(){
		return billto;
	}

	public Object getGuid(){
		return guid;
	}

	public Object getChalanno(){
		return chalanno;
	}

	public double getRoundOff(){
		return roundOff;
	}

	public int getStatus(){
		return status;
	}
}