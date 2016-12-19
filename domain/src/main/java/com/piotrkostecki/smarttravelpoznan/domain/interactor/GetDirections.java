package com.piotrkostecki.smarttravelpoznan.domain.interactor;

import com.piotrkostecki.smarttravelpoznan.domain.executor.PostExecutionThread;
import com.piotrkostecki.smarttravelpoznan.domain.executor.ThreadExecutor;
import com.piotrkostecki.smarttravelpoznan.domain.repository.PekaRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a collection of all {@link com.piotrkostecki.smarttravelpoznan.domain.model.Direction}.
 */
public class GetDirections extends UseCase {

    private final String busStop;
    private PekaRepository pekaRepository;

    @Inject
    public GetDirections(String busStop,
                         PekaRepository pekaRepository,
                         ThreadExecutor threadExecutor,
                         PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.busStop = busStop;
        this.pekaRepository = pekaRepository;
    }

    @Override public Observable buildUseCaseObservable() {
        return this.pekaRepository.directions(busStop);
    }
}
