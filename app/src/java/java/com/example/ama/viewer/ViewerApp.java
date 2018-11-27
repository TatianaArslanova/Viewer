package com.example.ama.viewer;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.example.ama.viewer.data.api.GithubApi;
import com.example.ama.viewer.data.interceptor.RequestHeadersInterceptor;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
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
        initStetho();
        initApi();
    }

    private void initStetho() {
        Stetho.Initializer initializer = Stetho.newInitializerBuilder(this)
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build();
        Stetho.initialize(initializer);
    }

    private void initApi() {
        githubApi = new Retrofit.Builder()
                .client(buildClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(GithubApi.class);
    }

    private OkHttpClient buildClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new RequestHeadersInterceptor(getPackageInfo()))
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

    private PackageInfo getPackageInfo() {
        try {
            return getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ViewerApp getInstance() {
        return instance;
    }

    public GithubApi getGithubApi() {
        return githubApi;
    }
}
