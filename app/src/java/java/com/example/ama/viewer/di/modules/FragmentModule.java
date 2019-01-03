package com.example.ama.viewer.di.modules;

import com.example.ama.viewer.presentation.profile.ProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface FragmentModule {

    @ContributesAndroidInjector
    ProfileFragment bindProfileFragment();
}
