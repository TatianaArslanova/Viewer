package com.example.ama.viewer.di.modules

import android.content.Context
import com.example.ama.viewer.ViewerApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [
    UtilsModule::class,
    FragmentModule::class,
    PresenterModule::class,
    RepositoryModule::class,
    NetworkModule::class,
    DbModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: ViewerApp): Context = app
}