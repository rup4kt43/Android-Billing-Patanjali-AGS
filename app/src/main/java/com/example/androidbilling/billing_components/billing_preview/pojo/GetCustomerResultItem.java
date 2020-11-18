package com.example.androidbilling.billing_components.billing_preview.pojo;

import com.google.gson.annotations.SerializedName;

public class GetCustomerResultItem {

	@SerializedName("ACNAME")
	private String aCNAME;

	@SerializedName("ACID")
	private String aCID;

	@SerializedName("ORG_TYPE")
	private String oRGTYPE;

	@SerializedName("ADDRESS")
	private String aDDRESS;

	@SerializedName("MOBILE")
	private String mOBILE;

	public String getACNAME(){
		return aCNAME;
	}

	public String getACID(){
		return aCID;
	}

	public String getORGTYPE(){
		return oRGTYPE;
	}

	public String getADDRESS(){
		return aDDRESS;
	}

	public String getMOBILE(){
		return mOBILE;
	}

	@Override
	public String toString() {
		return aCNAME;
	}
}