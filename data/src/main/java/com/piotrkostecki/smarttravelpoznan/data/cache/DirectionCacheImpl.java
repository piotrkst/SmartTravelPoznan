package com.piotrkostecki.smarttravelpoznan.data.cache;

import android.content.Context;

import com.piotrkostecki.smarttravelpoznan.data.cache.serializer.JsonSerializer;
import com.piotrkostecki.smarttravelpoznan.data.entity.DirectionEntity;
import com.piotrkostecki.smarttravelpoznan.data.exception.DirectionNotFoundException;
import com.piotrkostecki.smarttravelpoznan.domain.executor.ThreadExecutor;

import java.io.File;

import javax.inject.Inject;

import rx.Observable;

/**
 * {@link DirectionCache} implementation.
 */
public class DirectionCacheImpl implements DirectionCache {

    private static final String SETTINGS_FILE_NAME = "com.piotrkostecki.smarttravelpoznan.SETTINGS";
    private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update";

    private static final String DEFAULT_FILE_NAME = "direction_";
    private static final long EXPIRATION_TIME = 60 * 10 * 1000;

    private final Context context;
    private final File cacheDir;
    private final JsonSerializer serializer;
    private final FileManager fileManager;
    private final ThreadExecutor threadExecutor;

    /**
    * Constructor of the class {@link DirectionCacheImpl}.
    *
    * @param context A
    * @param directionCacheSerializer {@link JsonSerializer} for object serialization.
    * @param fileManager {@link FileManager} for saving serialized objects to the file system.
    * */
    @Inject
    public DirectionCacheImpl(Context context, JsonSerializer directionCacheSerializer,
                              FileManager fileManager, ThreadExecutor executor) {
        if (context == null || directionCacheSerializer == null || fileManager == null || executor == null) {
            throw new IllegalArgumentException("Invalid null parameter");
        }
        this.context = context.getApplicationContext();
        this.cacheDir = this.context.getCacheDir();
        this.serializer = directionCacheSerializer;
        this.fileManager = fileManager;
        this.threadExecutor = executor;
    }

    @Override
    public Observable<DirectionEntity> get(String stopName) {
        return Observable.create(subscriber -> {
            File directionEntityFile = DirectionCacheImpl.this.buildFile(stopName);
            String fileContent = DirectionCacheImpl.this.fileManager.readFileContent(directionEntityFile);
            DirectionEntity directionEntity = DirectionCacheImpl.this.serializer.deserialize(fileContent);

            if (directionEntity != null) {
                subscriber.onNext(directionEntity);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new DirectionNotFoundException());
            }
        });
    }

    @Override
    public void put(DirectionEntity directionEntity) {
        if (directionEntity != null) {
            File directionEntityFile = this.buildFile(directionEntity.getStopName());
            if (!isCached(directionEntity.getStopName())) {
                String jsonString = this.serializer.serialize(directionEntity);
                this.executeAsynchronously(new CacheWriter(this.fileManager, directionEntityFile, jsonString));
                setLastCacheUpdateTimeMillis();
            }
        }
    }

    @Override
    public boolean isCached(String stopName) {
        File directionEntityFile = this.buildFile(stopName);
        return this.fileManager.exists(directionEntityFile);
    }

    @Override
    public boolean isExpired() {
        long currentTime = System.currentTimeMillis();
        long lastUpdateTime = this.getLastCacheUpdateTimeMillis();

        boolean expired = ((currentTime - lastUpdateTime) > EXPIRATION_TIME);

        if (expired) {
            this.evictAll();
        }

        return expired;
    }

    @Override
    public void evictAll() {
        this.executeAsynchronously(new CacheEvictor(this.fileManager, this.cacheDir));
    }

    /**
     * Build a file, used to be inserted in the disk cache.
     *
     * @param stopName The id user to build the file.
     * @return A valid file.
     */
    private File buildFile(String stopName) {
        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append(this.cacheDir.getPath());
        fileNameBuilder.append(File.separator);
        fileNameBuilder.append(DEFAULT_FILE_NAME);
        fileNameBuilder.append(stopName);

        return new File(fileNameBuilder.toString());
    }

    /**
     * Set in millis, the last time the cache was accessed.
     */
    private void setLastCacheUpdateTimeMillis() {
        long currentMillis = System.currentTimeMillis();
        this.fileManager.writeToPreferences(this.context, SETTINGS_FILE_NAME,
                SETTINGS_KEY_LAST_CACHE_UPDATE, currentMillis);
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private long getLastCacheUpdateTimeMillis() {
        return this.fileManager.getFromPreferences(this.context, SETTINGS_FILE_NAME,
                SETTINGS_KEY_LAST_CACHE_UPDATE);
    }

    /**
     * Executes a {@link Runnable} in another Thread.
     *
     * @param runnable {@link Runnable} to execute
     */
    private void executeAsynchronously(Runnable runnable) {
        this.threadExecutor.execute(runnable);
    }

    /**
     * {@link Runnable} class for writing to disk.
     */
    private static class CacheWriter implements Runnable {
        private final FileManager fileManager;
        private final File fileToWrite;
        private final String fileContent;

        CacheWriter(FileManager fileManager, File fileToWrite, String fileContent) {
            this.fileManager = fileManager;
            this.fileToWrite = fileToWrite;
            this.fileContent = fileContent;
        }

        @Override public void run() {
            this.fileManager.writeToFile(fileToWrite, fileContent);
        }
    }

    /**
     * {@link Runnable} class for evicting all the cached files
     */
    private static class CacheEvictor implements Runnable {
        private final FileManager fileManager;
        private final File cacheDir;

        CacheEvictor(FileManager fileManager, File cacheDir) {
            this.fileManager = fileManager;
            this.cacheDir = cacheDir;
        }

        @Override public void run() {
            this.fileManager.clearDirectory(this.cacheDir);
        }
    }
}
