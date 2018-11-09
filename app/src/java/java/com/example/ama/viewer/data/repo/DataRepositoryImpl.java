package com.example.ama.viewer.data.repo;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;

public class DataRepositoryImpl implements DataRepository {

    @Override
    public Single<String> loadData() {
        return Single.just("TEMP TEXT")
                .delay(5000, TimeUnit.MILLISECONDS);
    }
}
