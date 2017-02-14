package com.piotrkostecki.smarttravelpoznan.domain.interactor;

import com.piotrkostecki.smarttravelpoznan.domain.executor.PostExecutionThread;
import com.piotrkostecki.smarttravelpoznan.domain.executor.ThreadExecutor;
import com.piotrkostecki.smarttravelpoznan.domain.model.Stop;
import com.piotrkostecki.smarttravelpoznan.domain.repository.PekaRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a collection of all {@link Stop}.
 */
public class GetStops extends UseCase {

    private PekaRepository pekaRepository;

    @Inject
    public GetStops(PekaRepository pekaRepository,
                    ThreadExecutor threadExecutor,
                    PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.pekaRepository = pekaRepository;
    }

    @Override public Observable buildUseCaseObservable(Object... params) {
        return this.pekaRepository.stops((String) params[0]);
    }
}
