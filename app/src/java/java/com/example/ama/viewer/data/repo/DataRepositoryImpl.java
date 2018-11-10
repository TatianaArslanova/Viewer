package com.example.ama.viewer.data.repo;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class DataRepositoryImpl implements DataRepository {
    private static final String TEXT = "TEXT ";
    private boolean wasLoaded = false;

    @Override
    public Single<String> loadData() {
        return Single.fromCallable(this::getRandomText)
                .delaySubscription(2000, TimeUnit.MILLISECONDS, Schedulers.io());
    }

    private String getRandomText() {
        if (wasLoaded) {
            wasLoaded = false;
            throw new IllegalStateException("Not found");
        }
        wasLoaded = true;
        return TEXT + new Random().nextInt();
    }
}
