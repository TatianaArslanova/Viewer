package com.example.ama.viewer.data.repo

import com.example.ama.viewer.data.api.GithubApi
import com.example.ama.viewer.data.api.dto.GithubUserDTO
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class ApiRepositoryImpl(
        private val githubApi: GithubApi
) : ApiRepository {

    override fun loadData(login: String): Observable<GithubUserDTO> =
            githubApi.getUserByUsername(login)
                    .subscribeOn(Schedulers.io())

}