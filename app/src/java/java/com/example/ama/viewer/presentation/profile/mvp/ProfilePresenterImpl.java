package com.example.ama.viewer.presentation.profile.mvp;

import android.util.Log;

import com.example.ama.viewer.R;
import com.example.ama.viewer.data.api.dto.GithubUserDTO;
import com.example.ama.viewer.data.repo.ApiRepository;
import com.example.ama.viewer.data.repo.DBRepository;
import com.example.ama.viewer.presentation.profile.mvp.base.MainPresenter;
import com.example.ama.viewer.presentation.profile.mvp.base.MainView;
import com.example.ama.viewer.utils.ResUtils;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public class ProfilePresenterImpl extends MvpBasePresenter<MainView> implements MainPresenter {

    private final static String LOGIN = "JakeWharton";

    private final ApiRepository apiRepository;
    private final DBRepository dbRepository;
    private final Scheduler observeOnScheduler;
    private final CompositeDisposable compositeDisposable;
    private final ResUtils utils;
    private Disposable loadDisposable;

    @Inject
    public ProfilePresenterImpl(ApiRepository apiRepository, DBRepository dbRepository, Scheduler observeOnScheduler, CompositeDisposable compositeDisposable, ResUtils utils) {
        this.apiRepository = apiRepository;
        this.dbRepository = dbRepository;
        this.observeOnScheduler = observeOnScheduler;
        this.compositeDisposable = compositeDisposable;
        this.utils = utils;
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        if (loadDisposable == null || loadDisposable.isDisposed()) {
            ifViewAttached(view -> {
                Observable<GithubUserDTO> observable;
                if (loadDisposable == null && !view.hasLoadedData()) {
                    observable = loadUser();
                } else {
                    observable = updateUser();
                }
                loadDisposable = observable
                        .doOnSubscribe(__ -> view.showLoading(pullToRefresh))
                        .observeOn(observeOnScheduler)
                        .subscribe(
                                this::showContent,
                                throwable -> showError(handleError(throwable), pullToRefresh));
                compositeDisposable.add(loadDisposable);
            });
        }
    }

    private Observable<GithubUserDTO> loadUser() {
        return getFromDb()
                .onErrorResumeNext(getFromApi());
    }

    private Observable<GithubUserDTO> updateUser() {
        return getFromApi()
                .observeOn(observeOnScheduler)
                .doOnError(throwable -> showLightError(handleError(throwable)))
                .onErrorResumeNext(getFromDb());
    }

    private Throwable handleError(Throwable throwable) {
        if (throwable instanceof NullPointerException) {
            return new Throwable(utils.getString(R.string.no_saved_data_error));
        } else if (throwable instanceof HttpException) {
            return new Throwable(utils.getString(R.string.server_error_message));
        } else if (throwable instanceof SocketTimeoutException) {
            return new Throwable(utils.getString(R.string.timeout_error_message));
        } else if (throwable instanceof UnknownHostException) {
            return new Throwable(utils.getString(R.string.check_connection_error_message));
        } else return new Throwable(utils.getString(R.string.unknown_error));
    }

    @Override
    public void destroy() {
        compositeDisposable.clear();
        super.destroy();
    }

    private Observable<GithubUserDTO> getFromDb() {
        return dbRepository.getUserFromDb(LOGIN);
    }

    private Observable<GithubUserDTO> getFromApi() {
        return apiRepository.loadData(LOGIN)
                .doOnNext(this::saveToDb);
    }

    private void saveToDb(GithubUserDTO userDTO) {
        compositeDisposable.add(
                dbRepository.saveUserToDb(userDTO)
                        .observeOn(observeOnScheduler)
                        .subscribe(
                                () -> Log.d("USER SAVED TO DB", userDTO.getLogin()),
                                throwable -> Log.d("USER NOT SAVED TO DB", userDTO.getLogin()))
        );
    }

    private void showContent(GithubUserDTO user) {
        ifViewAttached(view -> {
            view.setData(user);
            view.showContent();
        });
    }

    private void showError(Throwable throwable, boolean pullToRefresh) {
        ifViewAttached(view -> {
            if (view.hasLoadedData()) {
                view.showError(throwable, pullToRefresh);
            } else {
                view.showToastError(throwable.getMessage());
            }
        });
    }

    private void showLightError(Throwable throwable) {
        ifViewAttached(view -> view.showToastError(throwable.getMessage()));
    }
}
