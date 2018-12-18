package com.example.ama.viewer.data.repo;

import com.example.ama.viewer.data.api.GithubApi;
import com.example.ama.viewer.data.api.dto.GithubUserDTO;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ApiRepositoryImpl implements ApiRepository {
    private final static String USERNAME = "JakeWharton";
    private GithubApi githubApi;

    public ApiRepositoryImpl(GithubApi githubApi) {
        this.githubApi = githubApi;
    }

    @Override
    public Observable<GithubUserDTO> loadData() {
        return githubApi.getUserByUsername(USERNAME)
                .subscribeOn(Schedulers.io());
    }
}
