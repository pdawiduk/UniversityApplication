package com.example.shogun.universityapplication.webservice.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Shogun on 04.11.2016.
 */

public class Register {
    @SerializedName("login")
    String login;

    @SerializedName("password")
    String password;

    @SerializedName("email")
    String email;

}
