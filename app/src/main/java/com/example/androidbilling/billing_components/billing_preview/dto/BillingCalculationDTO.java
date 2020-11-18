package com.example.androidbilling.billing_components.billing_preview.dto;

import java.io.Serializable;

public class BillingCalculationDTO implements Serializable {

    double grossAmount;
    double netAmount;
    double totalDiscount;
    double totalGST;

    public BillingCalculationDTO(double grossAmount, double netAmount, double totalDiscount, double totalGST) {
        this.grossAmount = grossAmount;
        this.netAmount = netAmount;
        this.totalDiscount = totalDiscount;
        this.totalGST = totalGST;
    }

    public BillingCalculationDTO( double netAmount, double totalDiscount, double totalGST) {

        this.netAmount = netAmount;
        this.totalDiscount = totalDiscount;
        this.totalGST = totalGST;
    }

    public double getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(double grossAmount) {
        this.grossAmount = grossAmount;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public double getTotalGST() {
        return totalGST;
    }

    public void setTotalGST(double totalGST) {
        this.totalGST = totalGST;
    }
}
