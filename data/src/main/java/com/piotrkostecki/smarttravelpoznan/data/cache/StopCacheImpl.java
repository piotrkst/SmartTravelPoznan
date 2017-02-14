package com.piotrkostecki.smarttravelpoznan.data.cache;

import android.content.Context;

import com.piotrkostecki.smarttravelpoznan.data.cache.serializer.JsonSerializer;
import com.piotrkostecki.smarttravelpoznan.data.entity.StopEntity;
import com.piotrkostecki.smarttravelpoznan.data.exception.StopNotFoundException;
import com.piotrkostecki.smarttravelpoznan.domain.executor.ThreadExecutor;

import java.io.File;

import javax.inject.Inject;

import rx.Observable;

/**
 * {@link StopCache} implementation.
 */
public class StopCacheImpl implements StopCache {

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
    * Constructor of the class {@link StopCacheImpl}.
    *
    * @param context A
    * @param directionCacheSerializer {@link JsonSerializer} for object serialization.
    * @param fileManager {@link FileManager} for saving serialized objects to the file system.
    * */
    @Inject
    public StopCacheImpl(Context context, JsonSerializer directionCacheSerializer,
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
    public Observable<StopEntity> get(String stopName) {
        return Observable.create(subscriber -> {
            File directionEntityFile = StopCacheImpl.this.buildFile(stopName);
            String fileContent = StopCacheImpl.this.fileManager.readFileContent(directionEntityFile);
            StopEntity stopEntity = StopCacheImpl.this.serializer.deserialize(fileContent);

            if (stopEntity != null) {
                subscriber.onNext(stopEntity);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new StopNotFoundException());
            }
        });
    }

    @Override
    public void put(StopEntity stopEntity) {
        if (stopEntity != null) {
            File directionEntityFile = this.buildFile(stopEntity.getSymbol());
            if (!isCached(stopEntity.getSymbol())) {
                String jsonString = this.serializer.serialize(stopEntity);
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
