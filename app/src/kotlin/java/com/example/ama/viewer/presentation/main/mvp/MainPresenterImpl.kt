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
            run {
                view.showLoading(pullToRefresh)
                compositeDisposable.add(
                        repository.loadData()
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({ s: String? ->
                                    run {
                                        view.setData(s)
                                        view.showContent()
                                    }
                                },
                                        { throwable -> view.showError(throwable, pullToRefresh) }
                                ))
            }
        }
    }

    override fun destroy() {
        compositeDisposable.clear()
        super.destroy()
    }
}