package com.example.ama.viewer.data.repo;

import io.reactivex.Observable;

public interface DataRepository {
    Observable<String> loadData();
}
