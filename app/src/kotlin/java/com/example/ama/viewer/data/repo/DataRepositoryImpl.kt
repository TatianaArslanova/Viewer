package com.example.ama.viewer.data.repo

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

class DataRepositoryImpl : DataRepository {

    private val random = Random()

    companion object {
        const val TEXT = "TEXT "
        const val TAKE_ELEMENTS = 5L
        const val ERROR_CHANCE = 20
    }

    override fun loadData(): Observable<String> =
            getSomeStringData()
                    .zipWith(getSomeLongData(),
                            BiFunction<String, Long, String> { string, long -> long.toString() + string })
                    .doOnNext { tryToSimulateError() }
                    .take(TAKE_ELEMENTS)

    private fun getRandomText(): String {
        return TEXT + random.nextInt()
    }

    private fun getSomeStringData() =
            Observable.fromCallable(this::getRandomText)
                    .subscribeOn(Schedulers.io())
                    .delay(2000, TimeUnit.MILLISECONDS)
                    .repeat()

    private fun getSomeLongData() =
            Observable.interval(1000, TimeUnit.MILLISECONDS, Schedulers.io())

    private fun tryToSimulateError() {
        if (random.nextInt(100) < ERROR_CHANCE) {
            throw IllegalStateException("Not found")
        }
    }
}