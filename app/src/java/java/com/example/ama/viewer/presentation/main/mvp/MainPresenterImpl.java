package com.example.ama.viewer.presentation.main.mvp;

import android.support.annotation.NonNull;

import com.example.ama.viewer.data.repo.DataRepository;
import com.example.ama.viewer.presentation.main.mvp.base.MainPresenter;
import com.example.ama.viewer.presentation.main.mvp.base.MainView;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class MainPresenterImpl extends MvpBasePresenter<MainView> implements MainPresenter {

    private DataRepository repository;
    private CompositeDisposable disposable;

    public MainPresenterImpl(DataRepository dataRepository) {
        repository = dataRepository;
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        ifViewAttached(view -> disposable.add(repository.loadData()
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
