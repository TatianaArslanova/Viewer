package com.example.ama.viewer.presentation.main.mvp

import com.example.ama.viewer.data.repo.DataRepository
import com.example.ama.viewer.presentation.main.mvp.base.MainPresenter
import com.example.ama.viewer.presentation.main.mvp.base.MainView
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainPresenterImpl(private val repository: DataRepository) : MvpBasePresenter<MainView>(), MainPresenter {

    private val compositeDisposable = CompositeDisposable()

    override fun loadData(pullToRefresh: Boolean) {
        ifViewAttached { view ->
            run {
                view.showLoading(pullToRefresh)
                compositeDisposable.add(
                        repository.loadData()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe { s: String? ->
                                    run {
                                        view.setData(s)
                                        view.showContent()
                                    }
                                }
                )
            }
        }
    }

    override fun destroy() {
        compositeDisposable.clear()
        super.destroy()
    }
}