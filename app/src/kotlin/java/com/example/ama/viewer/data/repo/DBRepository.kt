package com.example.ama.viewer.data.repo

import com.example.ama.viewer.data.entity.GithubUser
import io.reactivex.Completable
import io.reactivex.Observable

interface DBRepository {

    fun getUserFromDb(): Observable<GithubUser>

    fun saveUserToDb(user: GithubUser) : Completable
}