package com.example.ama.viewer.data.loader

import android.widget.ImageView

interface ImageLoader<T> {
    fun loadImage(url: String, destination: ImageView)
}