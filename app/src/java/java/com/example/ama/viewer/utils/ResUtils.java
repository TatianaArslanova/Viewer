package com.example.ama.viewer.utils;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ResUtils {

    private Context appContext;

    @Inject
    public ResUtils(Context appContext) {
        this.appContext = appContext;
    }

    public String getString(int resId) {
        return appContext.getResources().getString(resId);
    }
}
