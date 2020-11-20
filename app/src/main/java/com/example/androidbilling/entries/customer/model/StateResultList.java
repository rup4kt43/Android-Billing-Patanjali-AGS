package com.example.androidbilling.entries.customer.model;

import com.google.gson.annotations.SerializedName;

public class StateResultList {

    @SerializedName("InputStateCode")
    private String inputstatecode;
    @SerializedName("STAMP")
    private String stamp;
    @SerializedName("STATUS")
    private int status;
    @SerializedName("StateName")
    private String statename;
    @SerializedName("StateCode")
    private String statecode;

    @SerializedName("CreatedBy")
    private String createdBy;

    @SerializedName("UpdatedBy")
    private String updatedBy;

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public String getInputstatecode() {
        return inputstatecode;
    }

    public String getStamp() {
        return stamp;
    }

    public int getStatus() {
        return status;
    }

    public String getStatename() {
        return statename;
    }

    public String getStatecode() {
        return statecode;
    }
}
