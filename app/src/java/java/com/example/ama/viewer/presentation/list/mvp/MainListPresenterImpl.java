package com.example.ama.viewer.presentation.list.mvp;

import android.support.annotation.NonNull;

import com.example.ama.viewer.data.repo.DataRepository;
import com.example.ama.viewer.presentation.list.mvp.base.MainPresenter;
import com.example.ama.viewer.presentation.list.mvp.base.MainView;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class MainListPresenterImpl extends MvpBasePresenter<MainView> implements MainPresenter {

    private final static String ON_ERROR_STRING = "";
    private DataRepository repository;
    private CompositeDisposable disposable;

    public MainListPresenterImpl(DataRepository dataRepository) {
        repository = dataRepository;
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        ifViewAttached(view -> disposable.add(repository.loadData()
                .onErrorReturn(__ -> ON_ERROR_STRING)
                .observeOn(AndroidSchedulers.mainThread())
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
            view.setData(content);
            view.showContent();
        });
    }

    private void showError(Throwable throwable, boolean pullToRefresh) {
        ifViewAttached(view -> view.showError(throwable, pullToRefresh));
    }
}
