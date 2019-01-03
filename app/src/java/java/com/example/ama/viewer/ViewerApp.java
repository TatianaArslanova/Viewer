package com.example.ama.viewer;

import com.example.ama.viewer.di.DaggerAppComponent;
import com.facebook.stetho.Stetho;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import io.realm.Realm;

public class ViewerApp extends DaggerApplication {
    private static ViewerApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initStetho();
        initRealm();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder()
                .context(this)
                .build();
    }

    private void initStetho() {
        Stetho.Initializer initializer = Stetho.newInitializerBuilder(this)
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build();
        Stetho.initialize(initializer);
    }

    private void initRealm() {
        Realm.init(this);
    }

    public static ViewerApp getInstance() {
        return instance;
    }
}
