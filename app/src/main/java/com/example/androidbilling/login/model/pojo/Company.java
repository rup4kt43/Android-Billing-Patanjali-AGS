package com.example.androidbilling.login.model.pojo;

import com.google.gson.annotations.SerializedName;

public class Company {

    @SerializedName("INITIAL")
    private String iNITIAL;

    @SerializedName("COMPANYNAME")
    private String cOMPANYNAME;

    @SerializedName("COMPANYID")
    private String cOMPANYID;

    @SerializedName("PhiscalID")
    private String phiscalID;

    public Company(String iNITIAL, String cOMPANYNAME, String cOMPANYID, String phiscalID) {
        this.iNITIAL = iNITIAL;
        this.cOMPANYNAME = cOMPANYNAME;
        this.cOMPANYID = cOMPANYID;
        this.phiscalID = phiscalID;
    }

    public String getINITIAL() {
        return iNITIAL;
    }

    public String getCOMPANYNAME() {
        return cOMPANYNAME;
    }

    public String getCOMPANYID() {
        return cOMPANYID;
    }

    public String getPhiscalID() {
        return phiscalID;
    }
}