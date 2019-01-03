package com.example.ama.viewer.data.loader

import android.widget.ImageView
import com.squareup.picasso.Picasso
import javax.inject.Inject

class PicassoImageLoader @Inject constructor(
        private val picasso: Picasso
) : ImageLoader<ImageView> {

    override fun loadImage(url: String, destination: ImageView) {
        picasso.load(url)
                .noPlaceholder()
                .into(destination)
    }
}