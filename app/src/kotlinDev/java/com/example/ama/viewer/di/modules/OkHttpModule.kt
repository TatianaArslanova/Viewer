package com.example.ama.viewer.di.modules

import android.util.Log
import com.example.ama.viewer.data.interceptor.RequestHeadersInterceptor
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
class OkHttpModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(requestHeadersInterceptor: RequestHeadersInterceptor,
                            stethoInterceptor: StethoInterceptor,
                            loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
            OkHttpClient.Builder()
                    .addInterceptor(requestHeadersInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .addNetworkInterceptor(stethoInterceptor)
                    .build()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
            HttpLoggingInterceptor { Log.d("LoggingInterceptor", it) }.apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
}