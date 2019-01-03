package com.example.ama.viewer.presentation.profile.mvp

import android.util.Log
import com.example.ama.viewer.R
import com.example.ama.viewer.data.api.dto.GithubUserDTO
import com.example.ama.viewer.data.repo.ApiRepository
import com.example.ama.viewer.data.repo.DBRepository
import com.example.ama.viewer.presentation.profile.mvp.base.MainPresenter
import com.example.ama.viewer.presentation.profile.mvp.base.MainView
import com.example.ama.viewer.utils.ResUtils
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class ProfilePresenterImpl @Inject constructor(
        private val apiRepository: ApiRepository,
        private val dbRepository: DBRepository,
        private val observeOnScheduler: Scheduler,
        private val compositeDisposable: CompositeDisposable,
        private val utils: ResUtils
) : MvpBasePresenter<MainView>(), MainPresenter {

    companion object {
        const val LOGIN = "JakeWharton"
    }

    private var loadDisposable: Disposable? = null

    override fun loadData(pullToRefresh: Boolean) {
        if (loadDisposable == null || loadDisposable?.isDisposed == true) {
            ifViewAttached { view ->
                val observable = if (loadDisposable == null && !view.hasLoadedData()) loadUser() else updateUser()
                loadDisposable = observable
                        .doOnSubscribe { view.showLoading(pullToRefresh) }
                        .observeOn(observeOnScheduler)
                        .subscribe(this::showContent)
                        { throwable -> showError(handleError(throwable), pullToRefresh) }
                compositeDisposable.add(loadDisposable!!)
            }
        }
    }

    private fun loadUser(): Observable<GithubUserDTO> {
        return getFromDb()
                .onErrorResumeNext(getFromApi())
    }

    private fun updateUser(): Observable<GithubUserDTO> {
        return getFromApi()
                .observeOn(observeOnScheduler)
                .doOnError { throwable -> showLighError(handleError(throwable)) }
                .onErrorResumeNext(getFromDb())
    }

    private fun handleError(throwable: Throwable) =
            when (throwable) {
                is NullPointerException -> Throwable(utils.getString(R.string.no_saved_data_error))
                is HttpException -> Throwable(utils.getString(R.string.server_error_message))
                is SocketTimeoutException -> Throwable(utils.getString(R.string.timeout_error_message))
                is UnknownHostException -> Throwable(utils.getString(R.string.check_connection_error_message))
                else -> Throwable(utils.getString(R.string.unknown_error))
            }

    override fun destroy() {
        compositeDisposable.clear()
        super.destroy()
    }

    private fun getFromDb() = dbRepository.getUserFromDb(LOGIN)

    private fun getFromApi() =
            apiRepository.loadData(LOGIN)
                    .doOnNext(this::saveToDb)

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

    private fun showLighError(throwable: Throwable) {
        ifViewAttached { view -> view.showToastError(throwable.message ?: "") }
    }
}