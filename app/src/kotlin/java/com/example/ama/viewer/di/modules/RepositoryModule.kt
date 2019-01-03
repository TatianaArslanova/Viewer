package com.example.ama.viewer.di.modules

import android.widget.ImageView
import com.example.ama.viewer.data.loader.ImageLoader
import com.example.ama.viewer.data.loader.PicassoImageLoader
import com.example.ama.viewer.data.repo.ApiRepository
import com.example.ama.viewer.data.repo.ApiRepositoryImpl
import com.example.ama.viewer.data.repo.DBRepository
import com.example.ama.viewer.data.repo.DBRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindApiRepository(apiReoisitory: ApiRepositoryImpl): ApiRepository

    @Binds
    @Singleton
    fun bindDbRepository(dbRepository: DBRepositoryImpl): DBRepository

    @Binds
    @Singleton
    fun bindImageLoader(imageLoader: PicassoImageLoader): ImageLoader<ImageView>
}