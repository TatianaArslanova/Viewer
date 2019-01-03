package com.example.ama.viewer.di.modules

import com.example.ama.viewer.presentation.profile.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentModule {

    @ContributesAndroidInjector
    fun bindProfileFragment(): ProfileFragment
}