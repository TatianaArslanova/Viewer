package com.example.ama.viewer

import android.app.Application
import com.example.ama.viewer.data.api.GithubApi
import com.example.ama.viewer.data.interceptor.RequestHeadersInterceptor
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
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
        initStetho()
    }

    private fun initApi() {
        githubApi = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubApi::class.java)
    }

    private fun initStetho() {
        val initializer = Stetho.newInitializerBuilder(this)
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build()
        Stetho.initialize(initializer)
    }

    private fun getClient() =
            OkHttpClient.Builder()
                    .addInterceptor(RequestHeadersInterceptor(getPackageInfo()))
                    .addNetworkInterceptor(StethoInterceptor())
                    .build()

    private fun getPackageInfo() =
            packageManager.getPackageInfo(packageName, 0)
}