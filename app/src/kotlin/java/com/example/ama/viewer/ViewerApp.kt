package com.example.ama.viewer

import android.app.Application
import com.example.ama.viewer.data.api.GithubApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ViewerApp : Application() {

    lateinit var githubApi: GithubApi
        private set

    companion object {
        const val BASE_URL = "https://api.github.com/"
        lateinit var instance: ViewerApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initApi()
    }

    private fun initApi() {
        githubApi = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubApi::class.java)
    }
}