package com.example.ama.viewer.data.loader

import android.widget.ImageView
import com.squareup.picasso.Picasso

class PicassoImageLoader(
        private val picasso: Picasso
) : ImageLoader<ImageView> {

    override fun loadImage(url: String, destination: ImageView) {
        picasso.load(url)
                .noPlaceholder()
                .into(destination)
    }
}