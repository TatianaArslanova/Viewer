package com.example.ama.viewer.presentation.profile.mvp;

import android.util.Log;

import com.example.ama.viewer.data.api.dto.GithubUserDTO;
import com.example.ama.viewer.data.repo.ApiRepository;
import com.example.ama.viewer.data.repo.DBRepository;
import com.example.ama.viewer.presentation.profile.mvp.base.MainPresenter;
import com.example.ama.viewer.presentation.profile.mvp.base.MainView;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ProfilePresenterImpl extends MvpBasePresenter<MainView> implements MainPresenter {

    private final static String LOGIN = "JakeWharton";

    private final ApiRepository apiRepository;
    private final DBRepository dbRepository;
    private final Scheduler observeOnScheduler;
    private final CompositeDisposable compositeDisposable;
    private Disposable loadDisposable;

    public ProfilePresenterImpl(ApiRepository apiRepository, DBRepository dbRepository, Scheduler observeOnScheduler) {
        this.apiRepository = apiRepository;
        this.dbRepository = dbRepository;
        this.observeOnScheduler = observeOnScheduler;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        if (loadDisposable == null || loadDisposable.isDisposed()) {
            ifViewAttached(view -> {
                loadDisposable = getFromApi()
                        .doOnNext(this::saveToDb)
                        .onErrorResumeNext(getFromDb())
                        .doOnSubscribe(__ -> view.showLoading(pullToRefresh))
                        .observeOn(observeOnScheduler)
                        .subscribe(
                                this::showContent,
                                throwable -> showError(throwable, pullToRefresh));
                compositeDisposable.add(loadDisposable);
            });
        }
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
        return apiRepository.loadData(LOGIN);
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
        throwable.printStackTrace();
        ifViewAttached(view -> view.showError(throwable, pullToRefresh));
    }
}
