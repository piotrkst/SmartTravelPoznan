package com.piotrkostecki.smarttravelpoznan.presentation.internal.di.modules;

import com.piotrkostecki.smarttravelpoznan.domain.executor.PostExecutionThread;
import com.piotrkostecki.smarttravelpoznan.domain.executor.ThreadExecutor;
import com.piotrkostecki.smarttravelpoznan.domain.interactor.GetDirections;
import com.piotrkostecki.smarttravelpoznan.domain.interactor.GetTimetables;
import com.piotrkostecki.smarttravelpoznan.domain.interactor.UseCase;
import com.piotrkostecki.smarttravelpoznan.domain.repository.PekaRepository;
import com.piotrkostecki.smarttravelpoznan.presentation.internal.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides peka related collaborators.
 */
@Module
public class PekaModule {

    private String stopName = "";

    public PekaModule() {
    }

    public PekaModule(String stopName){
        this.stopName = stopName;
    }

    @Provides @PerActivity @Named("directions")
    UseCase provideGetDirectionUseCase(PekaRepository pekaRepository,
                                       ThreadExecutor threadExecutor,
                                       PostExecutionThread postExecutionThread) {
        return new GetDirections(stopName, pekaRepository, threadExecutor, postExecutionThread);
    }

    @Provides @PerActivity @Named("timetables")
    UseCase provideGetTimetableUseCase(PekaRepository pekaRepository,
                                       ThreadExecutor threadExecutor,
                                       PostExecutionThread postExecutionThread) {
        return new GetTimetables(threadExecutor, postExecutionThread, pekaRepository);
    }
}
