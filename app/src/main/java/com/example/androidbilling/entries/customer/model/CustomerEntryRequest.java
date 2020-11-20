package com.example.androidbilling.entries.customer.model;

import com.google.gson.annotations.SerializedName;

public class CustomerEntryRequest {

    @SerializedName("ACID")
    private String acid;
    @SerializedName("PSTYPE")
    private String pstype;
    @SerializedName("PMODE")
    private String pmode;
    @SerializedName("MAILTYPE")
    private String mailtype;
    @SerializedName("PRICELEVEL")
    private String pricelevel;
    @SerializedName("customerID")
    private String customerid;
    @SerializedName("POSTALCODE")
    private String postalcode;
    @SerializedName("CITY")
    private String city;
    @SerializedName("MOBILE")
    private String mobile;
    @SerializedName("DISTRICT")
    private String district;
    @SerializedName("STATE")
    private String state;
    @SerializedName("GSTTYPE")
    private String gsttype;
    @SerializedName("GSTNO")
    private String gstno;
    @SerializedName("VATNO")
    private String vatno;
    @SerializedName("ADDRESS")
    private String address;
    @SerializedName("COMPANYID")
    private String companyid;
    @SerializedName("ACNAME")
    private String acname;
    @SerializedName("MODE")
    private String mode;

    @SerializedName("EMAIL")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAcid() {
        return acid;
    }

    public String getPstype() {
        return pstype;
    }

    public String getPmode() {
        return pmode;
    }

    public String getMailtype() {
        return mailtype;
    }

    public String getPricelevel() {
        return pricelevel;
    }

    public String getCustomerid() {
        return customerid;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public String getCity() {
        return city;
    }

    public String getMobile() {
        return mobile;
    }

    public String getDistrict() {
        return district;
    }

    public String getState() {
        return state;
    }

    public String getGsttype() {
        return gsttype;
    }

    public String getGstno() {
        return gstno;
    }

    public String getVatno() {
        return vatno;
    }

    public String getAddress() {
        return address;
    }

    public String getCompanyid() {
        return companyid;
    }

    public String getAcname() {
        return acname;
    }

    public String getMode() {
        return mode;
    }

    public void setAcid(String acid) {
        this.acid = acid;
    }

    public void setPstype(String pstype) {
        this.pstype = pstype;
    }

    public void setPmode(String pmode) {
        this.pmode = pmode;
    }

    public void setMailtype(String mailtype) {
        this.mailtype = mailtype;
    }

    public void setPricelevel(String pricelevel) {
        this.pricelevel = pricelevel;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setGsttype(String gsttype) {
        this.gsttype = gsttype;
    }

    public void setGstno(String gstno) {
        this.gstno = gstno;
    }

    public void setVatno(String vatno) {
        this.vatno = vatno;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public void setAcname(String acname) {
        this.acname = acname;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
