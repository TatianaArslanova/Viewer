package com.example.ama.viewer.presentation.profile.mvp

import android.util.Log
import com.example.ama.viewer.data.api.dto.GithubUserDTO
import com.example.ama.viewer.data.repo.ApiRepository
import com.example.ama.viewer.data.repo.DBRepository
import com.example.ama.viewer.presentation.profile.mvp.base.MainPresenter
import com.example.ama.viewer.presentation.profile.mvp.base.MainView
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class ProfilePresenterImpl(
        private val apiRepository: ApiRepository,
        private val dbRepository: DBRepository,
        private val observeOnScheduler: Scheduler
) : MvpBasePresenter<MainView>(), MainPresenter {

    companion object {
        const val LOGIN = "JakeWharton"
    }

    private val compositeDisposable = CompositeDisposable()
    private var loadDisposable: Disposable? = null

    override fun loadData(pullToRefresh: Boolean) {
        if (loadDisposable == null || loadDisposable?.isDisposed == true) {
            ifViewAttached { view ->
                loadDisposable = getFromApi()
                        .doOnNext { user -> saveToDb(user) }
                        .onErrorResumeNext(getFromGb())
                        .doOnSubscribe { view.showLoading(pullToRefresh) }
                        .observeOn(observeOnScheduler)
                        .subscribe(this::showContent) { throwable -> showError(throwable, pullToRefresh) }
                compositeDisposable.add(loadDisposable!!)
            }
        }
    }

    override fun destroy() {
        compositeDisposable.clear()
        super.destroy()
    }

    private fun getFromGb() = dbRepository.getUserFromDb(LOGIN)

    private fun getFromApi() = apiRepository.loadData(LOGIN)

    private fun showContent(userDTO: GithubUserDTO) {
        ifViewAttached { view ->
            view.setData(userDTO)
            view.showContent()
        }
    }

    private fun saveToDb(user: GithubUserDTO) {
        compositeDisposable.add(
                dbRepository.saveUserToDb(user)
                        .observeOn(observeOnScheduler)
                        .subscribe(
                                { Log.d("USER SAVED TO DB", user.login) },
                                { throwable -> Log.d("USER NOT SAVED TO DB", user.login) })
        )
    }

    private fun showError(throwable: Throwable, pullToRefresh: Boolean) {
        ifViewAttached { view ->
            val showToastError = if (view.hasLoadedData()) pullToRefresh else false
            view.showError(throwable, showToastError)
        }
    }
}