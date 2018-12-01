package com.example.ama.viewer.data.interceptor

import android.content.pm.PackageInfo
import okhttp3.Interceptor
import okhttp3.Response

class RequestHeadersInterceptor(
        private val packageInfo: PackageInfo?
) : Interceptor {

    companion object {
        const val UNKNOWN = "UNKNOWN"
        const val MEDIA_TYPE = "application/vnd.github.v3+json"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val updatedRequest = request.newBuilder()
                .addHeader("User-Agent", buildIAppInfo() ?: UNKNOWN)
                .addHeader("Accept", MEDIA_TYPE)
                .build()
        return chain.proceed(updatedRequest)
    }

    private fun buildIAppInfo(): String? =
            "${packageInfo?.applicationInfo?.name} ${packageInfo?.versionName}"
}