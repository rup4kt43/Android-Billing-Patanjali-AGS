package com.example.androidbilling.entries.customer.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("result")
    private List<CategoryResultList> categoryResultList;

    @SerializedName("message")
    private String message;

    public int getCode() {
        return code;
    }

    public List<CategoryResultList> getCategoryResultList() {
        return categoryResultList;
    }

    public String getMessage() {
        return message;
    }
}
