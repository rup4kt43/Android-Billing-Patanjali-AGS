package com.example.androidbilling.billing_components.bill_save.dto.trnmode;

import com.google.gson.annotations.SerializedName;

public class PaymentDetails {


    @SerializedName("paymentMode")
    String paymentMode;

    @SerializedName("amount")
    double amount;

    @SerializedName("trnac")
    String trnac;

    @SerializedName("remarks")
    String remarks;

    @SerializedName("returnAmount")
    double returnAmount;

    @SerializedName("name")
    String name;

    @SerializedName("number")           //Card Number
    String number;

    @SerializedName("type")             //Wallet type
    String type;




    @SerializedName("approvalCode")
    String approvalCode;

    @SerializedName("date")
    String date;


    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(double returnAmount) {
        this.returnAmount = returnAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApprovalCode() {
        return approvalCode;
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTrnac() {
        return trnac;
    }

    public void setTrnac(String trnac) {
        this.trnac = trnac;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
