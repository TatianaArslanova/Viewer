package com.example.ama.viewer.data.repo

import com.example.ama.viewer.data.api.GithubApi
import com.example.ama.viewer.data.model.GithubUser
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class DataRepositoryImpl(
        private val githubApi: GithubApi
) : DataRepository {

    companion object {
        const val USERNAME = "JakeWharton"
    }

    override fun loadData(): Observable<GithubUser> =
            githubApi.getUserByUsername(USERNAME)
                    .subscribeOn(Schedulers.io())

}