package com.example.ama.viewer.di.modules;

import com.example.ama.viewer.data.interceptor.RequestHeadersInterceptor;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class OkHttpModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(RequestHeadersInterceptor requestHeadersInterceptor,
                                     StethoInterceptor stethoInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(requestHeadersInterceptor)
                .addNetworkInterceptor(stethoInterceptor)
                .build();
    }
}
