package com.piotrkostecki.smarttravelpoznan.data.repository;

import com.piotrkostecki.smarttravelpoznan.data.database.datasource.SearchesDataSource;
import com.piotrkostecki.smarttravelpoznan.data.entity.SearchEntity;
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

@Singleton
public class PekaDataRepository implements PekaRepository {

    private final SearchesDataSource searchesDataSource;
    private final PekaDataStoreFactory pekaDataStoreFactory;
    private final PekaEntityDataMapper pekaEntityDataMapper;

    @Inject
    public PekaDataRepository(SearchesDataSource searchesDataSource,
                              PekaDataStoreFactory dataStoreFactory,
                              PekaEntityDataMapper pekaEntityDataMapper) {
        this.searchesDataSource = searchesDataSource;
        this.pekaDataStoreFactory = dataStoreFactory;
        this.pekaEntityDataMapper = pekaEntityDataMapper;
    }

    @Override
    public Observable<List<Stop>> searches() {
        searchesDataSource.open();
        Observable<List<Stop>> observable = searchesDataSource.getSearches().map(this.pekaEntityDataMapper::transformSearch);
        return observable;
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
        return pekaDataStore.timetableEntity(bollardSymbol).map(this.pekaEntityDataMapper::transformTimetable);
    }
}
