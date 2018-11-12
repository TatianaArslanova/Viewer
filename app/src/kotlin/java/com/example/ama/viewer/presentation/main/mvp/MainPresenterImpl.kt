package com.example.ama.viewer.presentation.main.mvp

import com.example.ama.viewer.data.repo.DataRepository
import com.example.ama.viewer.presentation.main.mvp.base.MainPresenter
import com.example.ama.viewer.presentation.main.mvp.base.MainView
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class MainPresenterImpl(private val repository: DataRepository) : MvpBasePresenter<MainView>(), MainPresenter {

    private val compositeDisposable = CompositeDisposable()

    override fun loadData(pullToRefresh: Boolean) {
        ifViewAttached { view ->
            compositeDisposable.add(
                    repository.loadData()
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnSubscribe { view.showLoading(pullToRefresh) }
                            .subscribe(this::showContent)
                            { throwable -> showError(throwable, pullToRefresh) })
        }
    }

    override fun destroy() {
        compositeDisposable.clear()
        super.destroy()
    }

    private fun showContent(content: String) {
        ifViewAttached { view ->
            view.setData(content)
            view.showContent()
        }
    }

    private fun showError(throwable: Throwable, pullToRefresh: Boolean) {
        ifViewAttached { view -> view.showError(throwable, pullToRefresh) }
    }
}