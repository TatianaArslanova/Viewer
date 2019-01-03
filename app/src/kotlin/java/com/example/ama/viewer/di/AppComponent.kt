package com.example.ama.viewer.di

import com.example.ama.viewer.ViewerApp
import com.example.ama.viewer.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class])
interface AppComponent : AndroidInjector<ViewerApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(app: ViewerApp): Builder

        fun build(): AppComponent
    }
}