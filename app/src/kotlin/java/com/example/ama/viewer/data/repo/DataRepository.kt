package com.example.ama.viewer.data.repo

import com.example.ama.viewer.data.model.GithubUser
import io.reactivex.Observable

interface DataRepository {
    fun loadData(): Observable<GithubUser>
}