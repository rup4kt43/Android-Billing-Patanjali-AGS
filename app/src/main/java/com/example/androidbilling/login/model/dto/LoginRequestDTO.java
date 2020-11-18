package com.example.androidbilling.login.model.dto;

import com.google.gson.annotations.SerializedName;

public class LoginRequestDTO {

    @SerializedName("username")
    String username;

    @SerializedName("password")
    String password;

    @SerializedName("companyId")
    String companyId;





    public LoginRequestDTO(String username, String password, String companyId) {
        this.username = username;
        this.password = password;
        this.companyId = companyId;

    }


}
