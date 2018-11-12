package com.example.ama.viewer.presentation.list.mvp.base;

import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

import java.util.List;

public interface MainView extends MvpLceView<List<String>> {
    void appendItemToList(String item);
}
