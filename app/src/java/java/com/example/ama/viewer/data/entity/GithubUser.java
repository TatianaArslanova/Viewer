package com.example.ama.viewer.data.entity;

import com.example.ama.viewer.data.api.dto.GithubUserDTO;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class GithubUser extends RealmObject {

    @PrimaryKey
    private String login;
    private String avatar;
    private String name;
    private String company;
    private String blog;
    private String location;
    private String email;
    private String bio;

    public GithubUser() {
    }

    public GithubUser(GithubUserDTO user) {
        login = user.getLogin();
        avatar = user.getAvatar();
        name = user.getName();
        company = user.getCompany();
        blog = user.getBlog();
        location = user.getLocation();
        email = user.getEmail();
        bio = user.getBio();
    }

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

    public void setLogin(String login) {
        this.login = login;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
