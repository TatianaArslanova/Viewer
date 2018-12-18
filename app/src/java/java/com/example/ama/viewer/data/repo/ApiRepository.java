package com.example.ama.viewer.data.repo;

import com.example.ama.viewer.data.api.dto.GithubUserDTO;

import io.reactivex.Observable;

public interface ApiRepository {
    Observable<GithubUserDTO> loadData(String login);
}
