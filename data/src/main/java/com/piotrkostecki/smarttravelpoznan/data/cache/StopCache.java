package com.piotrkostecki.smarttravelpoznan.data.cache;

import com.piotrkostecki.smarttravelpoznan.data.entity.StopEntity;

import rx.Observable;

/**
 * An interface representing a direction Cache.
 */
public interface StopCache {
    /**
     * Gets an {@link rx.Observable} which will emit a {@link StopEntity}.
     *
     * @param stopName The user id to retrieve data.
     */
    Observable<StopEntity> get(final String stopName);

    /**
     * Puts and element into the cache.
     *
     * @param stopEntity Element to insert in the cache.
     */
    void put(StopEntity stopEntity);

    /**
     * Checks if an element (User) exists in the cache.
     *
     * @param stopName The id used to look for inside the cache.
     * @return true if the element is cached, otherwise false.
     */
    boolean isCached(final String stopName);

    /**
     * Checks if the cache is expired.
     *
     * @return true, the cache is expired, otherwise false.
     */
    boolean isExpired();

    /**
     * Evict all elements of the cache.
     */
    void evictAll();
}
