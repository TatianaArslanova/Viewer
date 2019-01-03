package com.example.ama.viewer.utils

import android.content.Context
import javax.inject.Inject

class ResUtils @Inject constructor(val app: Context) {

    fun getString(resId: Int): String =
            app.resources.getString(resId)
}