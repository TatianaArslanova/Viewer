package com.example.ama.viewer.data.repo;

import com.example.ama.viewer.ViewerApp;
import com.example.ama.viewer.data.api.GithubApi;
import com.example.ama.viewer.data.model.GithubUser;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class DataRepositoryImpl implements DataRepository {
    private final static String USERNAME = "JakeWharton";
    private GithubApi githubApi = ViewerApp.getInstance().getGithubApi();

    @Override
    public Observable<GithubUser> loadData() {
        return githubApi.getUserByUsername(USERNAME)
                .subscribeOn(Schedulers.io());
    }
}
