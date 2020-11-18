package com.example.androidbilling.billing_components.bill_save.dto;

import com.example.androidbilling.billing_components.bill_save.dto.trnmode.PaymentDetails;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SaveRequest {

    @SerializedName("REFBILL")
    public String referenceBillNumber;

    @SerializedName("PARAC")
    public String parac;

    @SerializedName("VCHRNO")

    public String vchrno;




    @SerializedName("totalinddiscount")
    public double totalIndDiscount;


    @SerializedName("billtomob")
    public String billToMob;

    @SerializedName("BILLTOMOB")
    public String billToTel;

    @SerializedName("MWAREHOUSE")
    public String mwarehouse;

    @SerializedName("BILLTO")
    public String billTo;

    @SerializedName("TRNAC")
    public String trnac;

    @SerializedName("BILLTOADD")
    public String billToAdd;

    @SerializedName("DeviceId")
    public String deviceId ;

    @SerializedName("GUID")
    public String guid;

    @SerializedName("VOUCHERABBNAME")
    public String voucherAbbName;

    @SerializedName("TenderList")
    public List<PaymentDetails> billTender;

    @SerializedName("Tender")
    public double tender;

    @SerializedName("MODE")
    public String mode = "Sales";

    @SerializedName("TRNMODE")
    public String trnMode;

    @SerializedName("TRNUSER")
    public String trnUser;

    @SerializedName("COMPANYID")
    public String companyId;

    @SerializedName("CHALANNO")
    public String chalanNo;


    public String getTrnac() {
        return trnac;
    }

    public void setTrnac(String trnac) {
        this.trnac = trnac;
    }

    public String getReferenceBillNumber() {
        return referenceBillNumber;
    }

    public void setReferenceBillNumber(String referenceBillNumber) {
        this.referenceBillNumber = referenceBillNumber;
    }

    @SerializedName("prodList")
    public List<ProductDetails> productDetailsList;


    public String getMwarehouse() {
        return mwarehouse;
    }

    public void setMwarehouse(String mwarehouse) {
        this.mwarehouse = mwarehouse;
    }

    public String getBillToTel() {
        return billToTel;
    }

    public void setBillToTel(String billToTel) {
        this.billToTel = billToTel;
    }

    public String getBillTo() {
        return billTo;
    }

    public void setBillTo(String billTo) {
        this.billTo = billTo;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public double getTender() {
        return tender;
    }

    public void setTender(double tender) {
        this.tender = tender;
    }

    public String getTrnMode() {
        return trnMode;
    }

    public void setTrnMode(String trnmode) {
        this.trnMode = trnmode;
    }

    public String getTrnUser() {
        return trnUser;
    }

    public void setTrnUser(String trnUser) {
        this.trnUser = trnUser;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getBillToAdd() {
        return billToAdd;
    }

    public void setBillToAdd(String billToAdd) {
        this.billToAdd = billToAdd;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public List<ProductDetails> getProductDetailsList() {
        return productDetailsList;
    }

    public void setProductDetailsList(List<ProductDetails> productDetailsList) {
        this.productDetailsList = productDetailsList;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }


    public List<PaymentDetails> getBillTender() {
        return billTender;
    }

    public void setBillTender(List<PaymentDetails> billTender) {
        this.billTender = billTender;
    }

    public String getVoucherAbbName() {
        return voucherAbbName;
    }

    public void setVoucherAbbName(String voucherAbbName) {
        this.voucherAbbName = voucherAbbName;
    }


    public String getBillToMob() {
        return billToMob;
    }

    public void setBillToMob(String billToMob) {
        this.billToMob = billToMob;
    }


    public String getVchrno() {
        return vchrno;
    }

    public void setVchrno(String vchrno) {
        this.vchrno = vchrno;
    }


    public String getChalanNo() {
        return chalanNo;
    }

    public void setChalanNo(String chalanNo) {
        this.chalanNo = chalanNo;
    }

    public String getParac() {
        return parac;
    }

    public void setParac(String parac) {
        this.parac = parac;
    }

    public double getTotalIndDiscount() {
        return totalIndDiscount;
    }

    public void setTotalIndDiscount(double totalIndDiscount) {
        this.totalIndDiscount = totalIndDiscount;
    }


}
