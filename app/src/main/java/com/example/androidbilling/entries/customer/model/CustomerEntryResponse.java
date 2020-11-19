package com.example.androidbilling.entries.customer.model;

import com.google.gson.annotations.SerializedName;

public class CustomerEntryResponse {


    @SerializedName("message")
    private String message;
    @SerializedName("result")
    private String result;
    @SerializedName("code")
    private int code;

    public String getMessage() {
        return message;
    }

    public String getResult() {
        return result;
    }

    public int getCode() {
        return code;
    }
}
