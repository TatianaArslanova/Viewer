package com.example.ama.viewer.data.repo;

import com.example.ama.viewer.data.entity.GithubUser;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface DBRepository {

    Observable<GithubUser> getUserFromDb();

    Completable saveUserToDb(GithubUser user);
}
