package com.example.ama.viewer.data.repo

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

class DataRepositoryImpl : DataRepository {
    private val text = "TEXT "
    private var wasLoaded = false

    override fun loadData(): Single<String> {
        return Single.fromCallable { getRandomText() }
                .delaySubscription(2000, TimeUnit.MILLISECONDS, Schedulers.io())
    }

    private fun getRandomText(): String {
        if (wasLoaded) {
            wasLoaded = false
            throw IllegalStateException("Not found")
        }
        wasLoaded = true
        return text + Random().nextInt()
    }
}