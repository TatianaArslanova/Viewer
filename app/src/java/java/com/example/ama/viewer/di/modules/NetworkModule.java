package com.example.ama.viewer.di.modules;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.example.ama.viewer.data.api.GithubApi;
import com.example.ama.viewer.data.interceptor.RequestHeadersInterceptor;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    private static final String BASE_URL = "https://api.github.com/";

    @Provides
    @Singleton
    GithubApi provideGithubApi(OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(GithubApi.class);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(RequestHeadersInterceptor requestHeadersInterceptor,
                                     StethoInterceptor stethoInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(requestHeadersInterceptor)
                .addNetworkInterceptor(stethoInterceptor)
                .build();
    }

    @Provides
    @Singleton
    RequestHeadersInterceptor provideRequestHeadersInterceptor(PackageInfo packageInfo) {
        return new RequestHeadersInterceptor(packageInfo);
    }

    @Provides
    @Singleton
    StethoInterceptor provideStethoInterceptor() {
        return new StethoInterceptor();
    }

    @Provides
    @Singleton
    PackageInfo getPackageInfo(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Provides
    @Singleton
    Picasso providePicasso(Context context) {
        return new Picasso.Builder(context).build();
    }
}