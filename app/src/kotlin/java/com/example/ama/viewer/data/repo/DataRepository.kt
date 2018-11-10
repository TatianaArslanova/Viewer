package com.example.ama.viewer.data.repo

import io.reactivex.Single

interface DataRepository {
    fun loadData(): Single<String>
}