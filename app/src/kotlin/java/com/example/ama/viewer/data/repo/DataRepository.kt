package com.example.ama.viewer.data.repo

import io.reactivex.Observable

interface DataRepository {
    fun loadData(): Observable<String>
}