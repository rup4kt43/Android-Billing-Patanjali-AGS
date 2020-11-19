package com.example.androidbilling.entries.customer.model;

import com.google.gson.annotations.SerializedName;

public class DistrictResultList {


    @SerializedName("StateCode")
    private String statecode;
    @SerializedName("District")
    private String district;
    @SerializedName("State")
    private String state;

    public String getStatecode() {
        return statecode;
    }

    public String getDistrict() {
        return district;
    }

    public String getState() {
        return state;
    }
}
