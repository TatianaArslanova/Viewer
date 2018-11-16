package com.example.ama.viewer.data.model;

import com.google.gson.annotations.SerializedName;

public class GithubUser {

    private String login;
    @SerializedName("avatar_url")
    private String avatar;
    private String name;
    private String company;
    private String blog;
    private String location;
    private String email;
    private String bio;

    public String getLogin() {
        return login;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public String getBlog() {
        return blog;
    }

    public String getLocation() {
        return location;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }
}
