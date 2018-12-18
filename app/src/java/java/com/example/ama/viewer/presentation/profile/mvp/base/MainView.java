package com.example.ama.viewer.presentation.profile.mvp.base;

import com.example.ama.viewer.data.api.dto.GithubUserDTO;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

public interface MainView extends MvpLceView<GithubUserDTO> {
    boolean hasLoadedData();
}
