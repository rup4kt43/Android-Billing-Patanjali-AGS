package com.example.androidbilling.billing_components.bill_save.calculation_part;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.androidbilling.BR;

public class DataModel extends BaseObservable {

    private String cashTender ="";
    private String cashAmount = "0.00";
    private double netAmount;
    private String walletAmount = "";
    private String cardAmount = "";
    private String cashReturn = "0.00";
    private String samriddhiAmount = "";

    private String balanceAmount;


    public DataModel(double netAmount) {
        this.netAmount = netAmount;
        setBalanceAmount(String.valueOf(netAmount));

    }

    @Bindable
    public String getSamriddhiAmount() {
        return samriddhiAmount;
    }

    public void setSamriddhiAmount(String samriddhiAmount) {
        this.samriddhiAmount = samriddhiAmount;
        calculateBalance();
        notifyPropertyChanged(BR.samriddhiAmount);
    }

    @Bindable
    public String getCardAmount() {
        return cardAmount;
    }


    public void setCardAmount(String cardAmount) {
        this.cardAmount = cardAmount;
        calculateBalance();
        notifyPropertyChanged(BR.cardAmount);
    }

    @Bindable
    public String getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(String walletAmount) {
        this.walletAmount = walletAmount;
        calculateBalance();
        notifyPropertyChanged(BR.walletAmount);
    }

    @Bindable
    public String getCashTender() {
        return cashTender;
    }


    public void setCashTender(String cashTender) {
        this.cashTender = cashTender;


        notifyPropertyChanged(BR.cashTender);
    }


    @Bindable
    public String getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(String cashAmount) {


        this.cashAmount = cashAmount;
        notifyPropertyChanged(BR.cashAmount);
    }


    @Bindable
    public String getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(String balanceAmount) {
        this.balanceAmount = balanceAmount;
        notifyPropertyChanged(BR.balanceAmount);
    }

    @Bindable
    public String getCashReturn() {
        return cashReturn;
    }

    public void setCashReturn(String cashReturn) {
        this.cashReturn = cashReturn;
        notifyPropertyChanged(BR.cashReturn);
    }


    public void CashCalculationFucntion() {
        double amount_tender, amount_balance;
        if (!cashTender.isEmpty()) {
            amount_tender = Double.parseDouble(cashTender);
            amount_balance = Double.parseDouble(balanceAmount);

            if (amount_tender > amount_balance) {
                setCashAmount(String.valueOf(amount_balance));
                setCashReturn(String.valueOf(amount_tender - amount_balance));
                setBalanceAmount(String.valueOf(Double.parseDouble(cashAmount) - amount_balance));
            } else {
                setCashAmount(String.valueOf(amount_tender));
                setCashReturn("0");
                setBalanceAmount(String.valueOf(Double.parseDouble(balanceAmount) - Double.parseDouble(cashAmount)));
            }

        }
    }

    public void calculateBalance() {
        double cash_amount, wallet_amount,card_amount,samridhi_amount;
        if (cashAmount.isEmpty()) {
            cash_amount = 0.00;
        } else {
            cash_amount = Double.parseDouble(cashAmount);
        }

        if(walletAmount.isEmpty()){
            wallet_amount=0.00;
        }else{
            wallet_amount = Double.parseDouble(walletAmount);
        }

        if(cardAmount.isEmpty()){
            card_amount = 0.00;
        }else{
            card_amount = Double.parseDouble(cardAmount);
        }

        if(samriddhiAmount.isEmpty()){
            samridhi_amount = 0.00;
        }else{
            samridhi_amount = Double.parseDouble(samriddhiAmount);
        }




        balanceAmount = String.valueOf(netAmount - cash_amount-wallet_amount-card_amount-samridhi_amount);
        setBalanceAmount(balanceAmount);
    }


}