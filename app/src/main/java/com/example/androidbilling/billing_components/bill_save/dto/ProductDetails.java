package com.example.androidbilling.billing_components.bill_save.dto;

import com.google.gson.annotations.SerializedName;

public class ProductDetails {

    @SerializedName("MCODE")
    public String mcode;

    @SerializedName("GSTRATE")
    public double gstRate;

    @SerializedName("totalinddiscount")
    public double totalIndDiscount;

    @SerializedName("OriginalQty")
    public double originalQty;

    @SerializedName("ALTINDDISCOUNT")
    public double altIndDiscount;

    @SerializedName("BATCH")
    public String batch;

    @SerializedName("MFGDATE")
    public String mfgDate;

    @SerializedName("EXPDATE")
    public String expDate;

    @SerializedName("PRATE")
    public double pRate;

    @SerializedName("Quantity")
    public double quantity;

    @SerializedName("RATE")
    public double rate;

    @SerializedName("MRP")
    public double mrp;

    @SerializedName("INDDISCOUNT")
    public double indDiscount;

    @SerializedName("INDDISCOUNTRATE")
    public double indDiscountRate;

    @SerializedName("WAREHOUSE")
    public String warehouse;


    public String getMcode() {
        return mcode;
    }

    public void setMcode(String mcode) {
        this.mcode = mcode;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getMfgDate() {
        return mfgDate;
    }

    public void setMfgDate(String mfgDate) {
        this.mfgDate = mfgDate;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public double getpRate() {
        return pRate;
    }

    public void setpRate(double pRate) {
        this.pRate = pRate;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getMrp() {
        return mrp;
    }

    public void setMrp(double mrp) {
        this.mrp = mrp;
    }

    public double getIndDiscount() {
        return indDiscount;
    }

    public void setIndDiscount(double indDiscount) {
        this.indDiscount = indDiscount;
    }

    public double getIndDiscountRate() {
        return indDiscountRate;
    }

    public void setIndDiscountRate(double indDiscountRate) {
        this.indDiscountRate = indDiscountRate;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public double getAltIndDiscount() {
        return altIndDiscount;
    }

    public void setAltIndDiscount(double altIndDiscount) {
        this.altIndDiscount = altIndDiscount;
    }

    public double getTotalIndDiscount() {
        return totalIndDiscount;
    }

    public void setTotalIndDiscount(double totalIndDiscount) {
        this.totalIndDiscount = totalIndDiscount;
    }

    public double getOriginalQty() {
        return originalQty;
    }

    public void setOriginalQty(double originalQty) {
        this.originalQty = originalQty;
    }

    public double getGstRate() {
        return gstRate;
    }

    public void setGstRate(double gstRate) {
        this.gstRate = gstRate;
    }
}
