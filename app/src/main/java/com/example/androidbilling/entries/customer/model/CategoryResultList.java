package com.example.androidbilling.entries.customer.model;

import com.google.gson.annotations.SerializedName;

public class CategoryResultList {


    @SerializedName("LEVEL")
    private int level;
    @SerializedName("UpdatedBy")
    private String updatedby;
    @SerializedName("CreateBy")
    private String createby;
    @SerializedName("STAMP")
    private String stamp;
    @SerializedName("Status")
    private int status;
    @SerializedName("OrgTypeName")
    private String orgtypename;
    @SerializedName("OrgTypeCode")
    private String orgtypecode;

    public int getLevel() {
        return level;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public String getCreateby() {
        return createby;
    }

    public String getStamp() {
        return stamp;
    }

    public int getStatus() {
        return status;
    }

    public String getOrgtypename() {
        return orgtypename;
    }

    public String getOrgtypecode() {
        return orgtypecode;
    }
}
