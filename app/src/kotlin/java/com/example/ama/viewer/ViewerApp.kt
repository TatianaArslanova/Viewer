package com.example.ama.viewer

import com.example.ama.viewer.di.DaggerAppComponent
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.realm.Realm

class ViewerApp : DaggerApplication() {

    companion object {
        lateinit var instance: ViewerApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initStetho()
        initRealm()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerAppComponent.builder()
                    .context(this)
                    .build()

    private fun initStetho() {
        val initializer = Stetho.newInitializerBuilder(this)
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build()
        Stetho.initialize(initializer)
    }

    private fun initRealm() {
        Realm.init(this)
    }
}