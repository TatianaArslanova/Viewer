package com.example.ama.viewer.presentation.list.mvp

import com.example.ama.viewer.data.repo.DataRepository
import com.example.ama.viewer.presentation.list.mvp.base.MainPresenter
import com.example.ama.viewer.presentation.list.mvp.base.MainView
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class MainListPresenterImpl(private val repository: DataRepository) : MvpBasePresenter<MainView>(), MainPresenter {

    companion object {
        const val ON_ERROR_STRING = ""
    }

    private val compositeDisposable = CompositeDisposable()

    override fun loadData(pullToRefresh: Boolean) {
        ifViewAttached { view ->
            compositeDisposable.add(
                    repository.loadData()
                            .onErrorReturn { ON_ERROR_STRING }
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
            view.appendItemToList(content)
            view.showContent()
        }
    }

    private fun showError(throwable: Throwable, pullToRefresh: Boolean) {
        ifViewAttached { view -> view.showError(throwable, pullToRefresh) }
    }
}