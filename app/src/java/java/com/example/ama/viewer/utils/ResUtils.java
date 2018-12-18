package com.example.ama.viewer.utils;

import com.example.ama.viewer.ViewerApp;

public class ResUtils {

    public static String getString(int resId) {
        return ViewerApp.getInstance().getResources().getString(resId);
    }
}
