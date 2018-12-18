package com.example.ama.viewer.utils

import com.example.ama.viewer.ViewerApp

object ResUtils {

    fun getString(resId: Int) =
            ViewerApp.instance.resources.getString(resId)
}