package com.example.ama.viewer.di.modules;

import com.example.ama.viewer.presentation.profile.mvp.ProfilePresenterImpl;
import com.example.ama.viewer.presentation.profile.mvp.base.MainPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

@Module
public abstract class PresenterModule {

    @Binds
    abstract MainPresenter bindProfilePresenter(ProfilePresenterImpl profilePresenter);

    @Provides
    static Scheduler provideScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    static CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }
}
