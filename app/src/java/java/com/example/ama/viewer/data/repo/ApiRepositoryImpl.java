package com.example.ama.viewer.data.repo;

import com.example.ama.viewer.data.api.GithubApi;
import com.example.ama.viewer.data.api.dto.GithubUserDTO;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ApiRepositoryImpl implements ApiRepository {
    private GithubApi githubApi;

    public ApiRepositoryImpl(GithubApi githubApi) {
        this.githubApi = githubApi;
    }

    @Override
    public Observable<GithubUserDTO> loadData(String login) {
        return githubApi.getUserByUsername(login)
                .subscribeOn(Schedulers.io());
    }
}
