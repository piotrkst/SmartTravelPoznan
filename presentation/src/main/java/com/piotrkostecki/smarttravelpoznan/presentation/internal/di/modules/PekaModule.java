package com.piotrkostecki.smarttravelpoznan.presentation.internal.di.modules;

import com.piotrkostecki.smarttravelpoznan.domain.executor.PostExecutionThread;
import com.piotrkostecki.smarttravelpoznan.domain.executor.ThreadExecutor;
import com.piotrkostecki.smarttravelpoznan.domain.interactor.GetBollards;
import com.piotrkostecki.smarttravelpoznan.domain.interactor.GetStops;
import com.piotrkostecki.smarttravelpoznan.domain.interactor.GetTimetable;
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

    public PekaModule() {
    }

    @Provides @PerActivity @Named("stops")
    UseCase provideGetStopNameListUseCase(PekaRepository pekaRepository,
                                          ThreadExecutor threadExecutor,
                                          PostExecutionThread postExecutionThread) {
        return new GetStops(pekaRepository, threadExecutor, postExecutionThread);
    }

    @Provides @PerActivity @Named("timetable")
    UseCase provideGetTimetableUseCase(PekaRepository pekaRepository,
                                       ThreadExecutor threadExecutor,
                                       PostExecutionThread postExecutionThread) {
        return new GetTimetable(pekaRepository, threadExecutor, postExecutionThread);
    }

    @Provides @PerActivity @Named("bollards")
    UseCase provideGetBollardListUseCase(PekaRepository pekaRepository,
                                         ThreadExecutor threadExecutor,
                                         PostExecutionThread postExecutionThread) {
        return new GetBollards(pekaRepository, threadExecutor, postExecutionThread);
    }
}
