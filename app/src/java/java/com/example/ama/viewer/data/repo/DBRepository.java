package com.example.ama.viewer.data.repo;

import com.example.ama.viewer.data.api.dto.GithubUserDTO;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface DBRepository {

    Observable<GithubUserDTO> getUserFromDb(String login);

    Completable saveUserToDb(GithubUserDTO user);
}
