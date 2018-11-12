package com.example.ama.viewer.presentation.list.mvp.base

import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView

interface MainView : MvpLceView<List<String>> {
    fun appendItemToList(item: String)
}