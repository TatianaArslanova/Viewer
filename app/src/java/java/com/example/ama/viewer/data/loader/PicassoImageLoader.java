package com.example.ama.viewer.data.loader;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PicassoImageLoader implements ImageLoader<ImageView> {
    private Picasso picasso;

    public PicassoImageLoader(Picasso picasso) {
        this.picasso = picasso;
    }

    @Override
    public void loadImage(String url, ImageView destination) {
        picasso.load(url)
                .noPlaceholder()
                .into(destination);
    }
}
