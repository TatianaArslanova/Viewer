package com.example.ama.viewer.data.api;

import com.example.ama.viewer.data.model.GithubUser;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubApi {

    @GET("users/{username}")
    Observable<GithubUser> getUserByUsername(@Path("username") String username);
}
