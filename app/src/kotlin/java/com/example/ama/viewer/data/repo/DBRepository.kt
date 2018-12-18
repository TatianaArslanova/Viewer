package com.example.ama.viewer.data.repo

import com.example.ama.viewer.data.api.dto.GithubUserDTO
import io.reactivex.Completable
import io.reactivex.Observable

interface DBRepository {

    fun getUserFromDb(login: String): Observable<GithubUserDTO>

    fun saveUserToDb(user: GithubUserDTO): Completable
}