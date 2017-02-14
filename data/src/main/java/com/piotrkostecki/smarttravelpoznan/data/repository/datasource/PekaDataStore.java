package com.piotrkostecki.smarttravelpoznan.data.repository.datasource;

import com.piotrkostecki.smarttravelpoznan.data.entity.BollardEntity;
import com.piotrkostecki.smarttravelpoznan.data.entity.StopEntity;
import com.piotrkostecki.smarttravelpoznan.data.entity.TimetableEntity;

import java.util.List;

import rx.Observable;

/**
 * Interface that represents a data store from where data is retrieved.
 */
public interface PekaDataStore {

    /**
     * Get an {@link rx.Observable} which will emit a List of {@link StopEntity}.
     */
    Observable<List<StopEntity>> stopEntityList(String stopName);

    Observable<TimetableEntity> timetableEntityList(String bollardSymbol);

    Observable<List<BollardEntity>> bollardEntityList(String stopName);
}
