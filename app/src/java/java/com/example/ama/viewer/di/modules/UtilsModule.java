package com.example.ama.viewer.di.modules;

import android.content.Context;

import com.example.ama.viewer.utils.ResUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilsModule {

    @Provides
    @Singleton
    ResUtils provideResUtils(Context appContext) {
        return new ResUtils(appContext);
    }
}
