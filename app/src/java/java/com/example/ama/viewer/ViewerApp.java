package com.example.ama.viewer;

import android.app.Application;

import com.example.ama.viewer.data.api.GithubApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewerApp extends Application {
    private static ViewerApp instance;
    private GithubApi githubApi;
    private static final String BASE_URL = "https://api.github.com/";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initApi();
    }

    private void initApi() {
        githubApi = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(GithubApi.class);
    }

    public static ViewerApp getInstance() {
        return instance;
    }

    public GithubApi getGithubApi() {
        return githubApi;
    }
}
