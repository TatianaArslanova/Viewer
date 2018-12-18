package com.example.ama.viewer.presentation.profile.mvp;

import android.util.Log;

import com.example.ama.viewer.data.entity.GithubUser;
import com.example.ama.viewer.data.api.dto.GithubUserDTO;
import com.example.ama.viewer.data.repo.ApiRepository;
import com.example.ama.viewer.data.repo.DBRepository;
import com.example.ama.viewer.presentation.profile.mvp.base.MainPresenter;
import com.example.ama.viewer.presentation.profile.mvp.base.MainView;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;

public class MainListPresenterImpl extends MvpBasePresenter<MainView> implements MainPresenter {

    private final ApiRepository apiRepository;
    private final DBRepository dbRepository;
    private final CompositeDisposable disposable;
    private final Scheduler observeOnScheduler;

    public MainListPresenterImpl(ApiRepository apiRepository, DBRepository dbRepository, Scheduler observeOnScheduler) {
        this.apiRepository = apiRepository;
        this.dbRepository = dbRepository;
        this.observeOnScheduler = observeOnScheduler;
        disposable = new CompositeDisposable();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        ifViewAttached(view -> disposable.add(getFromApi()
                .doOnNext(this::saveToDb)
                .onErrorResumeNext(getFromDb())
                .doOnSubscribe(__ -> view.showLoading(pullToRefresh))
                .subscribe(
                        this::showContent,
                        throwable -> showError(throwable, pullToRefresh))));
    }

    @Override
    public void destroy() {
        disposable.clear();
        super.destroy();
    }

    private Observable<GithubUserDTO> getFromDb() {
        return dbRepository.getUserFromDb()
                .map(GithubUserDTO::new)
                .observeOn(observeOnScheduler);
    }

    private Observable<GithubUserDTO> getFromApi() {
        return apiRepository.loadData()
                .observeOn(observeOnScheduler);
    }

    private void saveToDb(GithubUserDTO userDTO) {
        disposable.add(
                dbRepository.saveUserToDb(new GithubUser(userDTO))
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
