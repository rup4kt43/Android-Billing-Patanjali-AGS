package com.example.androidbilling.bill_load.pojo;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GetBillResult implements Serializable {

	@SerializedName("mwarehouse")
	private String mwarehouse;

	@SerializedName("totalweight")
	private int totalweight;

	@SerializedName("totaldiscount")
	private int totaldiscount;




	@SerializedName("nontaxable")
	private int nontaxable;

	@SerializedName("totamnt")
	private double totamnt;

	@SerializedName("vchrno")
	private String vchrno;

	@SerializedName("billtotel")
	private Object billtotel;

	@SerializedName("editUser")
	private Object editUser;

	@SerializedName("trnac")
	private String trnac;

	@SerializedName("prodList")
	private List<ProdListItem> prodList;

	@SerializedName("deviceId")
	private Object deviceId;

	@SerializedName("billtoadd")
	private String billtoadd;

	@SerializedName("trnmode")
	private String trnmode;

	@SerializedName("division")
	private String division;

	@SerializedName("phiscalId")
	private String phiscalId;

	@SerializedName("mode")
	private Object mode;

	@SerializedName("companyid")
	private String companyid;

	@SerializedName("netamnt")
	private double netamnt;

	@SerializedName("parac")
	private Object parac;

	@SerializedName("trnuser")
	private String trnuser;

	@SerializedName("gstrate")
	private int gstrate;

	@SerializedName("tender")
	private double tender;

	@SerializedName("taxable")
	private int taxable;

	@SerializedName("voucherabbname")
	private Object voucherabbname;

	@SerializedName("tenderList")
	private Object tenderList;

	@SerializedName("netwithoutroundoff")
	private int netwithoutroundoff;

	@SerializedName("terminal")
	private Object terminal;

	@SerializedName("totalinddiscount")
	private double totalinddiscount;

	@SerializedName("vatamnt")
	private double vatamnt;

	@SerializedName("billtomob")
	private String billtomob;

	@SerializedName("refbill")
	private Object refbill;

	@SerializedName("dcamnt")
	private double dcamnt;

	@SerializedName("billto")
	private String billto;

	@SerializedName("guid")
	private Object guid;

	@SerializedName("chalanno")
	private String chalanno;

	@SerializedName("roundOff")
	private int roundOff;

	@SerializedName("status")
	private int status;

	public String getMwarehouse(){
		return mwarehouse;
	}

	public int getTotalweight(){
		return totalweight;
	}

	public int getTotaldiscount(){
		return totaldiscount;
	}

	public int getNontaxable(){
		return nontaxable;
	}

	public double getTotamnt(){
		return totamnt;
	}

	public String getVchrno(){
		return vchrno;
	}

	public Object getBilltotel(){
		return billtotel;
	}

	public Object getEditUser(){
		return editUser;
	}

	public String getTrnac(){
		return trnac;
	}

	public List<ProdListItem> getProdList(){
		return prodList;
	}

	public Object getDeviceId(){
		return deviceId;
	}

	public String getBilltoadd(){
		return billtoadd;
	}

	public String getTrnmode(){
		return trnmode;
	}

	public String getDivision(){
		return division;
	}

	public String getPhiscalId(){
		return phiscalId;
	}

	public Object getMode(){
		return mode;
	}

	public String getCompanyid(){
		return companyid;
	}

	public double getNetamnt(){
		return netamnt;
	}

	public Object getParac(){
		return parac;
	}

	public String getTrnuser(){
		return trnuser;
	}

	public int getGstrate(){
		return gstrate;
	}

	public double getTender(){
		return tender;
	}

	public int getTaxable(){
		return taxable;
	}

	public Object getVoucherabbname(){
		return voucherabbname;
	}

	public Object getTenderList(){
		return tenderList;
	}

	public int getNetwithoutroundoff(){
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

	public String getBilltomob(){
		return billtomob;
	}

	public Object getRefbill(){
		return refbill;
	}

	public double getDcamnt(){
		return dcamnt;
	}

	public String getBillto(){
		return billto;
	}

	public Object getGuid(){
		return guid;
	}

	public String getChalanno(){
		return chalanno;
	}

	public int getRoundOff(){
		return roundOff;
	}

	public int getStatus(){
		return status;
	}

	public void setMwarehouse(String mwarehouse) {
		this.mwarehouse = mwarehouse;
	}

	public void setTotalweight(int totalweight) {
		this.totalweight = totalweight;
	}

	public void setTotaldiscount(int totaldiscount) {
		this.totaldiscount = totaldiscount;
	}

	public void setNontaxable(int nontaxable) {
		this.nontaxable = nontaxable;
	}

	public void setTotamnt(double totamnt) {
		this.totamnt = totamnt;
	}

	public void setVchrno(String vchrno) {
		this.vchrno = vchrno;
	}

	public void setBilltotel(Object billtotel) {
		this.billtotel = billtotel;
	}

	public void setEditUser(Object editUser) {
		this.editUser = editUser;
	}

	public void setTrnac(String trnac) {
		this.trnac = trnac;
	}

	public void setProdList(List<ProdListItem> prodList) {
		this.prodList = prodList;
	}

	public void setDeviceId(Object deviceId) {
		this.deviceId = deviceId;
	}

	public void setBilltoadd(String billtoadd) {
		this.billtoadd = billtoadd;
	}

	public void setTrnmode(String trnmode) {
		this.trnmode = trnmode;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public void setPhiscalId(String phiscalId) {
		this.phiscalId = phiscalId;
	}

	public void setMode(Object mode) {
		this.mode = mode;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public void setNetamnt(double netamnt) {
		this.netamnt = netamnt;
	}

	public void setParac(Object parac) {
		this.parac = parac;
	}

	public void setTrnuser(String trnuser) {
		this.trnuser = trnuser;
	}

	public void setGstrate(int gstrate) {
		this.gstrate = gstrate;
	}

	public void setTender(double tender) {
		this.tender = tender;
	}

	public void setTaxable(int taxable) {
		this.taxable = taxable;
	}

	public void setVoucherabbname(Object voucherabbname) {
		this.voucherabbname = voucherabbname;
	}

	public void setTenderList(Object tenderList) {
		this.tenderList = tenderList;
	}

	public void setNetwithoutroundoff(int netwithoutroundoff) {
		this.netwithoutroundoff = netwithoutroundoff;
	}

	public void setTerminal(Object terminal) {
		this.terminal = terminal;
	}

	public void setTotalinddiscount(double totalinddiscount) {
		this.totalinddiscount = totalinddiscount;
	}

	public void setVatamnt(double vatamnt) {
		this.vatamnt = vatamnt;
	}

	public void setBilltomob(String billtomob) {
		this.billtomob = billtomob;
	}

	public void setRefbill(Object refbill) {
		this.refbill = refbill;
	}

	public void setDcamnt(double dcamnt) {
		this.dcamnt = dcamnt;
	}

	public void setBillto(String billto) {
		this.billto = billto;
	}

	public void setGuid(Object guid) {
		this.guid = guid;
	}

	public void setChalanno(String chalanno) {
		this.chalanno = chalanno;
	}

	public void setRoundOff(int roundOff) {
		this.roundOff = roundOff;
	}

	public void setStatus(int status) {
		this.status = status;
	}



}