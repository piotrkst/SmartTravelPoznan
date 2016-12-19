package com.piotrkostecki.smarttravelpoznan.data.repository.datasource;

import com.piotrkostecki.smarttravelpoznan.data.cache.DirectionCache;
import com.piotrkostecki.smarttravelpoznan.data.entity.DirectionEntity;
import com.piotrkostecki.smarttravelpoznan.data.entity.TimetableEntity;
import com.piotrkostecki.smarttravelpoznan.data.net.RestApi;
import com.piotrkostecki.smarttravelpoznan.domain.model.Direction;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * {@link PekaDataStore} implementation based on connections to the api (Cloud).
 */
public class CloudPekaDataStore implements PekaDataStore {

    private final RestApi restApi;
    private final DirectionCache directionCache;

    private final Action1<DirectionEntity> saveToCacheAction = directionEntity -> {
        if (directionEntity != null) {
            CloudPekaDataStore.this.directionCache.put(directionEntity);
        }
    };

    /**
     * Construct a {@link PekaDataStore} based on connections to the api (Cloud).
     *
     * @param restApi The {@link RestApi} implementation to use.
     * @param directionCache A {@link DirectionCache} to cache data retrieved from the api.
     */
    CloudPekaDataStore(RestApi restApi, DirectionCache directionCache) {
        this.restApi = restApi;
        this.directionCache = directionCache;
    }

    @Override
    public Observable<List<TimetableEntity>> timetableEntityList() {
        return this.restApi.timetableEntityList();
    }

    @Override
    public Observable<List<DirectionEntity>> directionEntityList(String stopName) {
        return restApi.directionEntityList(stopName);
    }
}
