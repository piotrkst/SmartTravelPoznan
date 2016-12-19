package com.piotrkostecki.smarttravelpoznan.data.repository.datasource;

import com.piotrkostecki.smarttravelpoznan.data.entity.DirectionEntity;
import com.piotrkostecki.smarttravelpoznan.data.entity.TimetableEntity;

import java.util.List;

import rx.Observable;

/**
 * Interface that represents a data store from where data is retrieved.
 */
public interface PekaDataStore {

    /**
     * Get an {@link rx.Observable} which will emit a List of {@link DirectionEntity}.
     */
    Observable<List<DirectionEntity>> directionEntityList(String stopName);

    /**
     * Get an {@link rx.Observable} which will emit a {@link TimetableEntity} by its id.
     */
    Observable<List<TimetableEntity>> timetableEntityList();
}
