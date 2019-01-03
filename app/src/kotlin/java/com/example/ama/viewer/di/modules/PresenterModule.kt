package com.example.ama.viewer.di.modules

import com.example.ama.viewer.presentation.profile.mvp.ProfilePresenterImpl
import com.example.ama.viewer.presentation.profile.mvp.base.MainPresenter
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

@Module
abstract class PresenterModule {

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideScheduler(): Scheduler = AndroidSchedulers.mainThread()

        @JvmStatic
        @Provides
        fun provideCpmpositeDisposable(): CompositeDisposable = CompositeDisposable()
    }

    @Binds
    abstract fun bindProfilePresenter(profilePresenter: ProfilePresenterImpl): MainPresenter
}