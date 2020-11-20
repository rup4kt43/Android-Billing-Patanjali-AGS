package com.example.androidbilling.entries.customer.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DistrictResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("result")
    private List<DistrictResultList> districtResultList;

    @SerializedName("message")
    private String message;

    public int getCode() {
        return code;
    }

    public List<DistrictResultList> getDistrictResultList() {
        return districtResultList;
    }

    public String getMessage() {
        return message;
    }
}
