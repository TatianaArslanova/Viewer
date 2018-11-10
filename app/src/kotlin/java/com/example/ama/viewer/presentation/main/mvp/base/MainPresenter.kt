package com.example.ama.viewer.presentation.main.mvp.base

import com.hannesdorfmann.mosby3.mvp.MvpPresenter

interface MainPresenter : MvpPresenter<MainView> {
    fun loadData(pullToRefresh: Boolean)
}