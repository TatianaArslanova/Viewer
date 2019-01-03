package com.example.ama.viewer.di.modules

import android.content.Context
import android.content.pm.PackageInfo
import com.example.ama.viewer.data.api.GithubApi
import com.example.ama.viewer.data.interceptor.RequestHeadersInterceptor
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

    @Provides
    @Singleton
    fun provideGitHubApi(cilent: OkHttpClient): GithubApi =
            Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(cilent)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(GithubApi::class.java)

    @Provides
    @Singleton
    fun provideOkHttpClient(requestHeadersInterceptor: RequestHeadersInterceptor,
                            stethoInterceptor: StethoInterceptor): OkHttpClient =
            OkHttpClient.Builder()
                    .addInterceptor(requestHeadersInterceptor)
                    .addNetworkInterceptor(stethoInterceptor)
                    .build()

    @Provides
    @Singleton
    fun provideRequestHeadersInterceptor(packageInfo: PackageInfo): RequestHeadersInterceptor =
            RequestHeadersInterceptor(packageInfo)


    @Provides
    @Singleton
    fun provideStethoInterceptor(): StethoInterceptor =
            StethoInterceptor()

    @Provides
    @Singleton
    fun packageInfo(context: Context): PackageInfo =
            context.packageManager.getPackageInfo(context.packageName, 0)

    @Provides
    @Singleton
    fun providePicasso(context: Context): Picasso =
            Picasso.Builder(context).build()
}