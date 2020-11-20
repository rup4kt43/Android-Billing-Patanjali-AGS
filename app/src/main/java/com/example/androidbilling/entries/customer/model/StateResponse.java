package com.example.androidbilling.entries.customer.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StateResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("result")
    private List<StateResultList> stateResultList;

    @SerializedName("message")
    private String message;

    public int getCode() {
        return code;
    }

    public List<StateResultList> getStateResultList() {
        return stateResultList;
    }

    public String getMessage() {
        return message;
    }
}
