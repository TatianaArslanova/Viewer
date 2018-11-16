package com.example.ama.viewer.presentation.list.mvp;

import com.example.ama.viewer.data.model.GithubUser;
import com.example.ama.viewer.data.repo.DataRepository;
import com.example.ama.viewer.presentation.list.mvp.base.MainPresenter;
import com.example.ama.viewer.presentation.list.mvp.base.MainView;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;

public class MainListPresenterImpl extends MvpBasePresenter<MainView> implements MainPresenter {

    private final DataRepository dataRepository;
    private final CompositeDisposable disposable;
    private final Scheduler observeOnScheduler;

    public MainListPresenterImpl(DataRepository dataRepository, Scheduler observeOnScheduler) {
        this.dataRepository = dataRepository;
        this.observeOnScheduler = observeOnScheduler;
        disposable = new CompositeDisposable();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        ifViewAttached(view -> disposable.add(dataRepository.loadData()
                .observeOn(observeOnScheduler)
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

    private void showContent(GithubUser user) {
        ifViewAttached(view -> {
            view.appendItemToList(user.getLogin());
            view.showContent();
        });
    }

    private void showError(Throwable throwable, boolean pullToRefresh) {
        throwable.printStackTrace();
        ifViewAttached(view -> view.showError(throwable, pullToRefresh));
    }
}
