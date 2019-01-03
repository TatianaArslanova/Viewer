package com.example.ama.viewer.data.loader;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PicassoImageLoader implements ImageLoader<ImageView> {
    private Picasso picasso;

    @Inject
    PicassoImageLoader(Picasso picasso) {
        this.picasso = picasso;
    }

    @Override
    public void loadImage(String url, ImageView destination) {
        picasso.load(url)
                .noPlaceholder()
                .into(destination);
    }
}
