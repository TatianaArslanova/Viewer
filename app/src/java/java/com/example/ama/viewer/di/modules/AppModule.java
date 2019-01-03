package com.example.ama.viewer.di.modules;

import android.content.Context;

import com.example.ama.viewer.ViewerApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {
        UtilsModule.class,
        FragmentModule.class,
        PresenterModule.class,
        DbModule.class,
        NetworkModule.class,
        RepositoryModule.class})
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(ViewerApp app) {
        return app;
    }
}
