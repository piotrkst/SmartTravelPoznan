package com.piotrkostecki.smarttravelpoznan.data.repository.datasource;

import android.content.Context;
import android.support.annotation.NonNull;

import com.piotrkostecki.smarttravelpoznan.data.cache.DirectionCache;
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
    private final DirectionCache directionCache;

    @Inject
    public PekaDataStoreFactory(@NonNull Context context, @NonNull DirectionCache directionCache) {
        this.context = context.getApplicationContext();
        this.directionCache = directionCache;
    }

    /**
     * Create {@link PekaDataStore} from a user id.
     */
    public PekaDataStore create(String stopName) {
        PekaDataStore pekaDataStore;

        if (!this.directionCache.isExpired() && this.directionCache.isCached(stopName)) {
            pekaDataStore = new DiskPekaDataStore(this.directionCache);
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

        return new CloudPekaDataStore(restApi, this.directionCache);
    }
}
