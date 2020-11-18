package com.example.androidbilling.dashboard.model.pojo;

import com.google.gson.annotations.SerializedName;

public class DashboardResultItem {

	@SerializedName("TOTALAMOUNT")
	private double tOTALAMOUNT;

	@SerializedName("BILLCOUNT")
	private int bILLCOUNT;

	@SerializedName("VOUCHERNAME")
	private String vOUCHERNAME;

	public double getTOTALAMOUNT(){
		return tOTALAMOUNT;
	}

	public int getBILLCOUNT(){
		return bILLCOUNT;
	}

	public String getVOUCHERNAME(){
		return vOUCHERNAME;
	}


	public void settOTALAMOUNT(double tOTALAMOUNT) {
		this.tOTALAMOUNT = tOTALAMOUNT;
	}

	public void setbILLCOUNT(int bILLCOUNT) {
		this.bILLCOUNT = bILLCOUNT;
	}

	public void setvOUCHERNAME(String vOUCHERNAME) {
		this.vOUCHERNAME = vOUCHERNAME;
	}
}