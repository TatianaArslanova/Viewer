package com.example.ama.viewer.data.repo

import io.reactivex.Single
import java.util.concurrent.TimeUnit

class DataRepositoryImpl : DataRepository {
    override fun loadData(): Single<String> {
        return Single.just("Text")
                .delay(2000, TimeUnit.MILLISECONDS)
    }
}