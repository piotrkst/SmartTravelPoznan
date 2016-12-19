package com.piotrkostecki.smarttravelpoznan.presentation.internal.di.modules;

import android.content.Context;

import com.piotrkostecki.smarttravelpoznan.data.cache.DirectionCache;
import com.piotrkostecki.smarttravelpoznan.data.cache.DirectionCacheImpl;
import com.piotrkostecki.smarttravelpoznan.data.executor.JobExecutor;
import com.piotrkostecki.smarttravelpoznan.data.repository.PekaDataRepository;
import com.piotrkostecki.smarttravelpoznan.domain.executor.PostExecutionThread;
import com.piotrkostecki.smarttravelpoznan.domain.executor.ThreadExecutor;
import com.piotrkostecki.smarttravelpoznan.domain.repository.PekaRepository;
import com.piotrkostecki.smarttravelpoznan.presentation.AndroidApplication;
import com.piotrkostecki.smarttravelpoznan.presentation.UIThread;
import com.piotrkostecki.smarttravelpoznan.presentation.navigation.Navigator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
* Dagger module that provides objects which will live during the application lifecycle.
        */
@Module
public class ApplicationModule {
    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides @Singleton DirectionCache provideDirectionCache(DirectionCacheImpl directionCache) {
        return directionCache;
    }

    @Provides @Singleton
    PekaRepository providePekaRepository(PekaDataRepository pekaDataRepository) {
        return pekaDataRepository;
    }
}
