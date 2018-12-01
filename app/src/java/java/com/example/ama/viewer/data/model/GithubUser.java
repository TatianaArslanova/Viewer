package com.example.ama.viewer.data.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class GithubUser {
    private final static String NOT_SPECIFIED = "not specified";

    private String login;

    @Nullable
    @SerializedName("avatar_url")
    private String avatar;

    private String name;
    private String company;
    private String blog;
    private String location;
    private String email;
    private String bio;

    public String getLogin() {
        return login == null ? NOT_SPECIFIED : login;
    }

    @Nullable
    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name == null ? NOT_SPECIFIED : name;
    }

    public String getCompany() {
        return company == null ? NOT_SPECIFIED : company;
    }

    public String getBlog() {
        return blog == null ? NOT_SPECIFIED : blog;
    }

    public String getLocation() {
        return location == null ? NOT_SPECIFIED : location;
    }

    public String getEmail() {
        return email == null ? NOT_SPECIFIED : email;
    }

    public String getBio() {
        return bio == null ? NOT_SPECIFIED : bio;
    }
}
