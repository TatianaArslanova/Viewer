package com.example.ama.viewer.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.RealmConfiguration;

@Module
public class DbModule {

    @Provides
    @Singleton
    RealmConfiguration provideRealmConfiguration() {
        return new RealmConfiguration.Builder().build();
    }
}
