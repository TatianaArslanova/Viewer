package com.example.ama.viewer.presentation.list.mvp

import com.example.ama.viewer.data.model.GithubUser
import com.example.ama.viewer.data.repo.DataRepository
import com.example.ama.viewer.presentation.list.mvp.base.MainPresenter
import com.example.ama.viewer.presentation.list.mvp.base.MainView
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

class MainListPresenterImpl(
        private val repository: DataRepository,
        private val observeOnScheduler: Scheduler
) : MvpBasePresenter<MainView>(), MainPresenter {

    private val compositeDisposable = CompositeDisposable()

    override fun loadData(pullToRefresh: Boolean) {
        ifViewAttached { view ->
            compositeDisposable.add(
                    repository.loadData()
                            .observeOn(observeOnScheduler)
                            .doOnSubscribe { view.showLoading(pullToRefresh) }
                            .subscribe(this::showContent) { throwable -> showError(throwable, pullToRefresh) })
        }
    }

    override fun destroy() {
        compositeDisposable.clear()
        super.destroy()
    }

    private fun showContent(user: GithubUser) {
        ifViewAttached { view ->
            view.setData(user)
            view.showContent()
        }
    }

    private fun showError(throwable: Throwable, pullToRefresh: Boolean) {
        ifViewAttached { view -> view.showError(throwable, pullToRefresh) }
    }
}