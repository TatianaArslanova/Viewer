package com.example.ama.viewer.data.api;

import com.example.ama.viewer.data.api.dto.GithubUserDTO;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubApi {

    @GET("users/{username}")
    Observable<GithubUserDTO> getUserByUsername(@Path("username") String username);
}
