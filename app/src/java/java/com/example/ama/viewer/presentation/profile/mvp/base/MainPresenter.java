package com.example.ama.viewer.presentation.profile.mvp.base;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

public interface MainPresenter extends MvpPresenter<MainView> {
    void loadData(boolean pullToRefresh);
}
