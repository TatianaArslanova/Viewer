package com.example.ama.viewer.di.modules

import android.content.Context
import com.example.ama.viewer.utils.ResUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilsModule {

    @Provides
    @Singleton
    fun provideResUtils(app: Context): ResUtils = ResUtils(app)
}