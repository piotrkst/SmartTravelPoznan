package com.piotrkostecki.smarttravelpoznan.data.repository.datasource;

import com.piotrkostecki.smarttravelpoznan.data.cache.DirectionCache;
import com.piotrkostecki.smarttravelpoznan.data.entity.DirectionEntity;
import com.piotrkostecki.smarttravelpoznan.data.entity.TimetableEntity;

import java.util.List;

import rx.Observable;

/**
 * {@link PekaDataStore} implementation based on file system data store.
 */
public class DiskPekaDataStore implements PekaDataStore {

    private final DirectionCache directionCache;

    /**
     * Construct a {@link PekaDataStore} based file system data store.
     *
     * @param directionCache A {@link DirectionCache} to cache data retrieved from the api.
     */
    DiskPekaDataStore(DirectionCache directionCache) {
        this.directionCache = directionCache;
    }

    @Override
    public Observable<List<TimetableEntity>> timetableEntityList() {
        // TODO: implement simple cache for storing/retrieving collections of timetables.
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<List<DirectionEntity>> directionEntityList(String busStop) {
        // TODO: implement simple cache for storing/retrieving collections of directions
        throw new UnsupportedOperationException("Operation is not available!!!");
    }
}
