package com.piotrkostecki.smarttravelpoznan.data.repository.datasource;

import com.piotrkostecki.smarttravelpoznan.data.cache.StopCache;
import com.piotrkostecki.smarttravelpoznan.data.entity.BollardEntity;
import com.piotrkostecki.smarttravelpoznan.data.entity.StopEntity;
import com.piotrkostecki.smarttravelpoznan.data.entity.TimetableEntity;

import java.util.List;

import rx.Observable;

/**
 * {@link PekaDataStore} implementation based on file system data store.
 */
public class DiskPekaDataStore implements PekaDataStore {

    private final StopCache stopCache;

    /**
     * Construct a {@link PekaDataStore} based file system data store.
     *
     * @param stopCache A {@link StopCache} to cache data retrieved from the api.
     */
    DiskPekaDataStore(StopCache stopCache) {
        this.stopCache = stopCache;
    }

    @Override
    public Observable<TimetableEntity> timetableEntityList(String bollardSymbol) {
        // TODO: implement simple cache for storing/retrieving collections of timetables.
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<List<StopEntity>> stopEntityList(String stopName) {
        // TODO: implement simple cache for storing/retrieving collections of stops
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<List<BollardEntity>> bollardEntityList(String stopName) {
        // TODO: implement simple cache for storing/retrieving collections of stops
        throw new UnsupportedOperationException("Operation is not available!!!");
    }
}
