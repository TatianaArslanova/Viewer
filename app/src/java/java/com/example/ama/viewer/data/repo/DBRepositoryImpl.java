package com.example.ama.viewer.data.repo;

import com.example.ama.viewer.data.api.dto.GithubUserDTO;
import com.example.ama.viewer.data.entity.GithubUser;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DBRepositoryImpl implements DBRepository {

    private final static String PRIMARY_KEY = "login";
    private RealmConfiguration configuration;
    private Realm realm;

    public DBRepositoryImpl(RealmConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Observable<GithubUserDTO> getUserFromDb(String login) {
        return Observable.fromCallable(() -> realm.where(GithubUser.class).equalTo(PRIMARY_KEY, login).findFirst())
                .doOnSubscribe(disposable -> tryToOpenRealm())
                .doFinally(this::tryToCloseRealm)
                .map(GithubUserDTO::new)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable saveUserToDb(GithubUserDTO user) {
        return Completable.fromAction(() -> save(new GithubUser(user)))
                .doOnSubscribe(disposable -> tryToOpenRealm())
                .doFinally(this::tryToCloseRealm)
                .subscribeOn(Schedulers.io());
    }

    private void save(GithubUser user) {
        realm.beginTransaction();
        realm.insertOrUpdate(user);
        realm.commitTransaction();
    }

    private void tryToOpenRealm() {
        if (realm == null) {
            realm = Realm.getInstance(configuration);
        }
    }

    private void tryToCloseRealm() {
        if (realm != null && !realm.isClosed()) {
            realm.close();
            realm = null;
        }
    }
}
