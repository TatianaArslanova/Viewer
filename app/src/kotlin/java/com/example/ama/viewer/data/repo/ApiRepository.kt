package com.example.ama.viewer.data.repo

import com.example.ama.viewer.data.api.dto.GithubUserDTO
import io.reactivex.Observable

interface ApiRepository {
    fun loadData(login: String): Observable<GithubUserDTO>
}