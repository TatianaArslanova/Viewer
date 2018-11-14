package com.example.ama.viewer.data.repo;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class DataRepositoryImpl implements DataRepository {
    private static final String TEXT = "TEXT ";
    private static final int ELEMENTS_TO_TAKE = 5;
    private static final int ERROR_CHANCE = 20;

    private final Random random = new Random();

    @Override
    public Observable<String> loadData() {
        return getSomeStringData()
                .zipWith(getSomeLongData(), (s, aLong) -> aLong + s)
                .doOnNext(__ -> tryToSimulateError())
                .take(ELEMENTS_TO_TAKE);
    }

    private Observable<String> getSomeStringData() {
        return Observable.fromCallable(this::getRandomText)
                .subscribeOn(Schedulers.io())
                .delay(1000, TimeUnit.MILLISECONDS)
                .repeat();
    }

    private Observable<Long> getSomeLongData() {
        return Observable.interval(1000, TimeUnit.MILLISECONDS, Schedulers.io());
    }

    private String getRandomText() {
        return TEXT + random.nextInt();
    }

    private void tryToSimulateError() {
        if (random.nextInt(100) < ERROR_CHANCE) {
            throw new IllegalStateException("Not found");
        }
    }
}
