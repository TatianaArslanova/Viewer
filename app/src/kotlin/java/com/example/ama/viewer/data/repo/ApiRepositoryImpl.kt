package com.example.ama.viewer.data.repo

import com.example.ama.viewer.data.api.GithubApi
import com.example.ama.viewer.data.api.dto.GithubUserDTO
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class ApiRepositoryImpl(
        private val githubApi: GithubApi
) : ApiRepository {

    companion object {
        const val USERNAME = "JakeWharton"
    }

    override fun loadData(): Observable<GithubUserDTO> =
            githubApi.getUserByUsername(USERNAME)
                    .subscribeOn(Schedulers.io())

}