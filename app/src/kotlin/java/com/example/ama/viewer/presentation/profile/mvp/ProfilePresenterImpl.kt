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

class ProfilePresenterImpl(
        private val apiRepository: ApiRepository,
        private val dbRepositpry: DBRepository,
        private val observeOnScheduler: Scheduler
) : MvpBasePresenter<MainView>(), MainPresenter {

    private val compositeDisposable = CompositeDisposable()

    override fun loadData(pullToRefresh: Boolean) {
        ifViewAttached { view ->
            compositeDisposable.add(getFromApi()
                    .doOnNext { user -> saveToDb(user) }
                    .onErrorResumeNext(getFromGb())
                    .doOnSubscribe { view.showLoading(pullToRefresh) }
                    .subscribe(this::showContent) { throwable -> showError(throwable, pullToRefresh) })
        }
    }

    override fun destroy() {
        compositeDisposable.clear()
        super.destroy()
    }

    private fun getFromGb() = dbRepositpry.getUserFromDb()
            .observeOn(observeOnScheduler)

    private fun getFromApi() = apiRepository.loadData()
            .observeOn(observeOnScheduler)

    private fun showContent(userDTO: GithubUserDTO) {
        ifViewAttached { view ->
            view.setData(userDTO)
            view.showContent()
        }
    }

    private fun saveToDb(user: GithubUserDTO) {
        compositeDisposable.add(
                dbRepositpry.saveUserToDb(user)
                        .observeOn(observeOnScheduler)
                        .subscribe(
                                { Log.d("USER SAVED TO DB", user.login) },
                                { throwable -> Log.d("USER NOT SAVED TO DB", user.login) })
        )
    }

    private fun showError(throwable: Throwable, pullToRefresh: Boolean) {
        ifViewAttached { view -> view.showError(throwable, pullToRefresh) }
    }
}