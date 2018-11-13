package com.example.ama.viewer.presentation.list.mvp;

import android.support.annotation.NonNull;

import com.example.ama.viewer.data.repo.DataRepository;
import com.example.ama.viewer.presentation.list.mvp.base.MainPresenter;
import com.example.ama.viewer.presentation.list.mvp.base.MainView;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;

public class MainListPresenterImpl extends MvpBasePresenter<MainView> implements MainPresenter {

    private final static String ON_ERROR_STRING = "";
    private final DataRepository dataRepository;
    private CompositeDisposable disposable;
    private final Scheduler observeOnScheduler;

    public MainListPresenterImpl(DataRepository dataRepository, Scheduler observeOnScheduler) {
        this.dataRepository = dataRepository;
        this.observeOnScheduler = observeOnScheduler;
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        ifViewAttached(view -> disposable.add(dataRepository.loadData()
                .onErrorReturn(__ -> ON_ERROR_STRING)
                .observeOn(observeOnScheduler)
                .doOnSubscribe(__ -> view.showLoading(pullToRefresh))
                .subscribe(
                        this::showContent,
                        throwable -> showError(throwable, pullToRefresh))));
    }

    @Override
    public void attachView(@NonNull MainView view) {
        if (disposable == null) {
            disposable = new CompositeDisposable();
        }
        super.attachView(view);
    }

    @Override
    public void destroy() {
        disposable.clear();
        super.destroy();
    }

    private void showContent(String content) {
        ifViewAttached(view -> {
            view.appendItemToList(content);
            view.showContent();
        });
    }

    private void showError(Throwable throwable, boolean pullToRefresh) {
        ifViewAttached(view -> view.showError(throwable, pullToRefresh));
    }
}
