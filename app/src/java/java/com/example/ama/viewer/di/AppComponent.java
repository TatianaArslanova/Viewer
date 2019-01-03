package com.example.ama.viewer.di;

import com.example.ama.viewer.ViewerApp;
import com.example.ama.viewer.di.modules.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class})
public interface AppComponent extends AndroidInjector<ViewerApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder context(ViewerApp app);

        AppComponent build();
    }
}
