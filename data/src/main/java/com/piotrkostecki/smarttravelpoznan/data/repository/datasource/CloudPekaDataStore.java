package com.piotrkostecki.smarttravelpoznan.data.repository.datasource;

import com.piotrkostecki.smarttravelpoznan.data.cache.StopCache;
import com.piotrkostecki.smarttravelpoznan.data.database.datasource.SearchesDataSource;
import com.piotrkostecki.smarttravelpoznan.data.entity.BollardEntity;
import com.piotrkostecki.smarttravelpoznan.data.entity.StopEntity;
import com.piotrkostecki.smarttravelpoznan.data.entity.TimetableEntity;
import com.piotrkostecki.smarttravelpoznan.data.net.RestApi;

import java.util.List;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * {@link PekaDataStore} implementation based on connections to the api (Cloud).
 */
public class CloudPekaDataStore implements PekaDataStore {

    private final RestApi restApi;
    private final StopCache stopCache;
    private final SearchesDataSource searchesDataSource;

    private final Action1<StopEntity> saveToCacheAction = directionEntity -> {
        if (directionEntity != null) {
            CloudPekaDataStore.this.stopCache.put(directionEntity);
        }
    };

    /**
     * Construct a {@link PekaDataStore} based on connections to the api (Cloud).
     *
     * @param restApi The {@link RestApi} implementation to use.
     * @param stopCache A {@link StopCache} to cache data retrieved from the api.
     */
    CloudPekaDataStore(RestApi restApi, StopCache stopCache, SearchesDataSource searchesDataSource) {
        this.restApi = restApi;
        this.stopCache = stopCache;
        this.searchesDataSource = searchesDataSource;
    }

    @Override
    public Observable<TimetableEntity> timetableEntity(String bollardSymbol) {
        return this.restApi.timetableEntity(bollardSymbol);
    }

    @Override
    public Observable<List<StopEntity>> stopEntityList(String stopName) {
        return this.restApi.stopEntityList(stopName);
    }

    @Override
    public Observable<List<BollardEntity>> bollardEntityList(String stopName) {
        this.searchesDataSource.open();
        Observable<List<BollardEntity>> observable = this.restApi.bollardEntityList(stopName)
                .doOnNext(entityList -> CloudPekaDataStore.this.searchesDataSource.createSearch(stopName));
        return observable;
    }
}
