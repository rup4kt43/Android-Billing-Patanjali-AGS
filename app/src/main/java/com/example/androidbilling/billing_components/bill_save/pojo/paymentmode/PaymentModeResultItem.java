package com.example.androidbilling.billing_components.bill_save.pojo.paymentmode;

import androidx.annotation.Nullable;

import com.example.androidbilling.utilities.global.GlobalValues;
import com.google.gson.annotations.SerializedName;

public class PaymentModeResultItem {

	@SerializedName("PAYMENTMODENAME")
	private String pAYMENTMODENAME;

	@SerializedName("ACID")
	private String aCID;

	@SerializedName("MODE")
	private String mODE;

	@SerializedName("MODETYPE")
	private Object mODETYPE;

	public String getPAYMENTMODENAME(){
		return pAYMENTMODENAME;
	}

	public String getACID(){
		return aCID;
	}

	public String getMODE(){
		return mODE;
	}

	public Object getMODETYPE(){
		return mODETYPE;
	}


	@Override
	public String toString() {
		return pAYMENTMODENAME;
	}


}