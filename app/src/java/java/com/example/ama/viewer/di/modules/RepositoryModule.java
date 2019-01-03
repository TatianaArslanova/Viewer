package com.example.ama.viewer.di.modules;

import android.widget.ImageView;

import com.example.ama.viewer.data.loader.ImageLoader;
import com.example.ama.viewer.data.loader.PicassoImageLoader;
import com.example.ama.viewer.data.repo.ApiRepository;
import com.example.ama.viewer.data.repo.ApiRepositoryImpl;
import com.example.ama.viewer.data.repo.DBRepository;
import com.example.ama.viewer.data.repo.DBRepositoryImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public interface RepositoryModule {

    @Binds
    @Singleton
    DBRepository bindDbRepository(DBRepositoryImpl dbRepository);

    @Binds
    @Singleton
    ApiRepository bindApiRepository(ApiRepositoryImpl apiRepository);

    @Binds
    @Singleton
    ImageLoader<ImageView> bindImageLoader(PicassoImageLoader imageLoader);
}
