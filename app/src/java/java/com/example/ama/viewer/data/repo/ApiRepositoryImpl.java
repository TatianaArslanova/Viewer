package com.example.ama.viewer.data.repo;

import com.example.ama.viewer.data.api.GithubApi;
import com.example.ama.viewer.data.api.dto.GithubUserDTO;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class ApiRepositoryImpl implements ApiRepository {
    private GithubApi githubApi;

    @Inject
    public ApiRepositoryImpl(GithubApi githubApi) {
        this.githubApi = githubApi;
    }

    @Override
    public Observable<GithubUserDTO> loadData(String login) {
        return githubApi.getUserByUsername(login)
                .subscribeOn(Schedulers.io());
    }
}
