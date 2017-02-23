package com.piotrkostecki.smarttravelpoznan.data.repository.datasource;

import android.content.Context;
import android.support.annotation.NonNull;

import com.piotrkostecki.smarttravelpoznan.data.cache.StopCache;
import com.piotrkostecki.smarttravelpoznan.data.database.datasource.SearchesDataSource;
import com.piotrkostecki.smarttravelpoznan.data.entity.mapper.PekaEntityJsonMapper;
import com.piotrkostecki.smarttravelpoznan.data.net.RestApi;
import com.piotrkostecki.smarttravelpoznan.data.net.RestApiImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Factory that creates different implementations of {@link PekaDataStore}.
 */
@Singleton
public class PekaDataStoreFactory {

    private final Context context;
    private final StopCache stopCache;
    private final SearchesDataSource searchesDataSource;

    @Inject
    public PekaDataStoreFactory(@NonNull Context context, @NonNull StopCache stopCache, @NonNull SearchesDataSource searchesDataSource) {
        this.context = context.getApplicationContext();
        this.stopCache = stopCache;
        this.searchesDataSource = searchesDataSource;
    }

    /**
     * Create {@link PekaDataStore} from a user id.
     */
    public PekaDataStore create(String stopName) {
        PekaDataStore pekaDataStore;

        if (!this.stopCache.isExpired() && this.stopCache.isCached(stopName)) {
            pekaDataStore = new DiskPekaDataStore(this.stopCache);
        } else {
            pekaDataStore = createCloudDataStore();
        }

        return pekaDataStore;
    }

    /**
     * Create {@link PekaDataStore} to retrieve data from the Cloud.
     */
    public PekaDataStore createCloudDataStore() {
        PekaEntityJsonMapper pekaEntityJsonMapper = new PekaEntityJsonMapper();
        RestApi restApi = new RestApiImpl(this.context, pekaEntityJsonMapper);

        return new CloudPekaDataStore(restApi, this.stopCache, this.searchesDataSource);
    }
}
