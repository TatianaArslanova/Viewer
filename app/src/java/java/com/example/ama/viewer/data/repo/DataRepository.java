package com.example.ama.viewer.data.repo;

import io.reactivex.Single;

public interface DataRepository {
    Single<String> loadData();
}
