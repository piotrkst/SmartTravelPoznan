package com.piotrkostecki.smarttravelpoznan.data.repository;

import com.piotrkostecki.smarttravelpoznan.data.entity.mapper.PekaEntityDataMapper;
import com.piotrkostecki.smarttravelpoznan.data.repository.datasource.PekaDataStore;
import com.piotrkostecki.smarttravelpoznan.data.repository.datasource.PekaDataStoreFactory;
import com.piotrkostecki.smarttravelpoznan.domain.model.Bollard;
import com.piotrkostecki.smarttravelpoznan.domain.model.Stop;
import com.piotrkostecki.smarttravelpoznan.domain.model.Timetable;
import com.piotrkostecki.smarttravelpoznan.domain.repository.PekaRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * {@link PekaRepository} for retrieving user data.
 */
@Singleton
public class PekaDataRepository implements PekaRepository {

    private final PekaDataStoreFactory pekaDataStoreFactory;
    private final PekaEntityDataMapper pekaEntityDataMapper;

    /**
            * Constructs a {@link PekaRepository}.
            *
            * @param dataStoreFactory A factory to construct different data source implementations.
    * @param pekaEntityDataMapper {@link PekaEntityDataMapper}.
            */
    @Inject
    public PekaDataRepository(PekaDataStoreFactory dataStoreFactory,
                              PekaEntityDataMapper pekaEntityDataMapper) {
        this.pekaDataStoreFactory = dataStoreFactory;
        this.pekaEntityDataMapper = pekaEntityDataMapper;
    }

    @Override
    public Observable<List<Stop>> stops(String stopName) {
        final PekaDataStore pekaDataStore = this.pekaDataStoreFactory.create(stopName);
        return pekaDataStore.stopEntityList(stopName).map(this.pekaEntityDataMapper::transformStop);
    }

    @Override
    public Observable<List<Bollard>> bollards(String stopName) {
        final PekaDataStore pekaDataStore = this.pekaDataStoreFactory.create(stopName);
        return pekaDataStore.bollardEntityList(stopName).map(this.pekaEntityDataMapper::transformBollard);
    }

    @Override
    public Observable<Timetable> timetables(String bollardSymbol) {
        final PekaDataStore pekaDataStore = this.pekaDataStoreFactory.createCloudDataStore();
        return pekaDataStore.timetableEntityList(bollardSymbol).map(this.pekaEntityDataMapper::transform);
    }
}
