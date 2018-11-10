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
        ifViewAttached(view -> {
            view.showLoading(pullToRefresh);
            disposable.add(repository.loadData()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            s -> {
                                view.setData(s);
                                view.showContent();
                            },
                            throwable -> view.showError(throwable, pullToRefresh)));
        });
    }

    @Override
    public void attachView(@NonNull MainView view) {
        disposable = new CompositeDisposable();
        super.attachView(view);
    }

    @Override
    public void destroy() {
        disposable.clear();
        super.destroy();
    }
}
