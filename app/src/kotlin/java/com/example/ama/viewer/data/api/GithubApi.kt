package com.example.ama.viewer.data.api

import com.example.ama.viewer.data.model.GithubUser
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

    @GET("users/{username}")
    fun getUserByUsername(@Path("username") username: String): Observable<GithubUser>
}