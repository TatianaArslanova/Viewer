package com.example.ama.viewer.di.modules

import com.example.ama.viewer.data.interceptor.RequestHeadersInterceptor
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class OkHttpModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(requestHeadersInterceptor: RequestHeadersInterceptor,
                            stethoInterceptor: StethoInterceptor): OkHttpClient =
            OkHttpClient.Builder()
                    .addInterceptor(requestHeadersInterceptor)
                    .addNetworkInterceptor(stethoInterceptor)
                    .build()
}