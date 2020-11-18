package com.example.androidbilling.salse_return_component.pojo;

import com.google.gson.annotations.SerializedName;

public class SalesReturnBillLoadResultItem {

	@SerializedName("INDDISCOUNTRATE")
	private int iNDDISCOUNTRATE;

	@SerializedName("TRNMODE")
	private String trnMode;

	@SerializedName("totalinddiscount")
	private double totalIndDiscount;




	@SerializedName("TOTALDISCOUNTINRETURN")
	private double totalDiscountInReturn;

	@SerializedName("ALTINDDISCOUNT")
	private double altIntDiscount;

	@SerializedName("REALQTY")
	private double realQty;

	@SerializedName("BC")
	private String bC;

	@SerializedName("PRATE")
	private double prate;

	@SerializedName("UNIT")
	private String uNIT;

	@SerializedName("WAREHOUSE")
	private String wAREHOUSE;

	@SerializedName("PARAC")
	private String pARAC;

	@SerializedName("MCODE")
	private String mCODE;

	@SerializedName("RATE")
	private double rATE;

	@SerializedName("MFGDATE")
	private String mFGDATE;

	@SerializedName("BILLTO")
	private String bILLTO;

	@SerializedName("MRP")
	private double mRP;

	@SerializedName("GSTRATE")
	private int gSTRATE;

	@SerializedName("STOCK")
	private double sTOCK;

	@SerializedName("INDDISCOUNT")
	private double iNDDISCOUNT;

	@SerializedName("TRNAC")
	private String tRNAC;

	@SerializedName("EXPDATE")
	private String eXPDATE;

	@SerializedName("BILLTOADD")
	private String bILLTOADD;

	@SerializedName("BILLTOMOB")
	private String bILLTOMOB;

	@SerializedName("ITEMDESC")
	private String iTEMDESC;

	@SerializedName("BATCH")
	private String bATCH;


	public double getAltIntDiscount() {
		return altIntDiscount;
	}

	public void setAltIntDiscount(double altIntDiscount) {
		this.altIntDiscount = altIntDiscount;
	}

	public double getRealQty() {
		return realQty;
	}

	public void setRealQty(double realQty) {
		this.realQty = realQty;
	}

	public double getPrate() {
		return prate;
	}

	public void setPrate(double prate) {
		this.prate = prate;
	}

	public int getINDDISCOUNTRATE(){
		return iNDDISCOUNTRATE;
	}

	public String getBC(){
		return bC;
	}

	public String getUNIT(){
		return uNIT;
	}

	public String getWAREHOUSE(){
		return wAREHOUSE;
	}

	public String getPARAC(){
		return pARAC;
	}

	public String getMCODE(){
		return mCODE;
	}

	public double getRATE(){
		return rATE;
	}

	public String getMFGDATE(){
		return mFGDATE;
	}

	public String getBILLTO(){
		return bILLTO;
	}

	public double getMRP(){
		return mRP;
	}

	public int getGSTRATE(){
		return gSTRATE;
	}

	public double getSTOCK(){
		return sTOCK;
	}

	public double getINDDISCOUNT(){
		return iNDDISCOUNT;
	}

	public String getTRNAC(){
		return tRNAC;
	}

	public String getEXPDATE(){
		return eXPDATE;
	}

	public String getBILLTOADD(){
		return bILLTOADD;
	}

	public String getBILLTOMOB(){
		return bILLTOMOB;
	}

	public String getITEMDESC(){
		return iTEMDESC;
	}

	public String getBATCH(){
		return bATCH;
	}


	public void setiNDDISCOUNTRATE(int iNDDISCOUNTRATE) {
		this.iNDDISCOUNTRATE = iNDDISCOUNTRATE;
	}

	public void setbC(String bC) {
		this.bC = bC;
	}

	public void setuNIT(String uNIT) {
		this.uNIT = uNIT;
	}

	public void setwAREHOUSE(String wAREHOUSE) {
		this.wAREHOUSE = wAREHOUSE;
	}

	public void setpARAC(String pARAC) {
		this.pARAC = pARAC;
	}

	public void setmCODE(String mCODE) {
		this.mCODE = mCODE;
	}

	public void setrATE(double rATE) {
		this.rATE = rATE;
	}

	public void setmFGDATE(String mFGDATE) {
		this.mFGDATE = mFGDATE;
	}

	public void setbILLTO(String bILLTO) {
		this.bILLTO = bILLTO;
	}

	public void setmRP(double mRP) {
		this.mRP = mRP;
	}

	public void setgSTRATE(int gSTRATE) {
		this.gSTRATE = gSTRATE;
	}

	public void setsTOCK(double sTOCK) {
		this.sTOCK = sTOCK;
	}

	public void setiNDDISCOUNT(double iNDDISCOUNT) {
		this.iNDDISCOUNT = iNDDISCOUNT;
	}

	public void settRNAC(String tRNAC) {
		this.tRNAC = tRNAC;
	}

	public void seteXPDATE(String eXPDATE) {
		this.eXPDATE = eXPDATE;
	}

	public void setbILLTOADD(String bILLTOADD) {
		this.bILLTOADD = bILLTOADD;
	}

	public void setbILLTOMOB(String bILLTOMOB) {
		this.bILLTOMOB = bILLTOMOB;
	}

	public void setiTEMDESC(String iTEMDESC) {
		this.iTEMDESC = iTEMDESC;
	}

	public void setbATCH(String bATCH) {
		this.bATCH = bATCH;
	}


	public double remainingQuantity;

	public double getRemainingQuantity() {
		return remainingQuantity;
	}

	public void setRemainingQuantity(double remainingQuantity) {
		this.remainingQuantity = remainingQuantity;
	}

	public String getTrnMode() {
		return trnMode;
	}

	public void setTrnMode(String trnMode) {
		this.trnMode = trnMode;
	}


	public double getTotalIndDiscount() {
		return totalIndDiscount;
	}

	public void setTotalIndDiscount(double totalIndDiscount) {
		this.totalIndDiscount = totalIndDiscount;
	}

	public double getTotalDiscountInReturn() {
		return totalDiscountInReturn;
	}

	public void setTotalDiscountInReturn(double totalDiscountInReturn) {
		this.totalDiscountInReturn = totalDiscountInReturn;
	}
}