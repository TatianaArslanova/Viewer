package com.example.ama.viewer.data.loader;

public interface ImageLoader<T> {

    void loadImage(String url, T destination);
}
