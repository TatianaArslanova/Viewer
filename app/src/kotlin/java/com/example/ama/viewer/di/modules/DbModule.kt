package com.example.ama.viewer.di.modules

import dagger.Module
import dagger.Provides
import io.realm.RealmConfiguration
import javax.inject.Singleton

@Module
class DbModule {

    @Provides
    @Singleton
    fun provideRealmConfiguration(): RealmConfiguration =
            RealmConfiguration.Builder().build()
}