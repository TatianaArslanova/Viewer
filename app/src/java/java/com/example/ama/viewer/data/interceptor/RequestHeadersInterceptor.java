package com.example.ama.viewer.data.interceptor;

import android.content.pm.PackageInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Singleton
public class RequestHeadersInterceptor implements Interceptor {
    private static final String UNKNOWN = "UNKNOWN";
    private static final String MEDIA_TYPE = "application/vnd.github.v3+json";

    @Nullable
    private PackageInfo packageInfo;

    @Inject
    public RequestHeadersInterceptor(@Nullable PackageInfo packageInfo) {
        this.packageInfo = packageInfo;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Request updatedRequest = request.newBuilder()
                .addHeader("User-Agent", buildAppInfo())
                .addHeader("Accept", MEDIA_TYPE)
                .build();
        return chain.proceed(updatedRequest);
    }

    private String buildAppInfo() {
        if (packageInfo != null) {
            return packageInfo.applicationInfo.name + " " + packageInfo.versionName;
        }
        return UNKNOWN;
    }
}
