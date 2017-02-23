package com.piotrkostecki.smarttravelpoznan.domain.interactor;


import com.piotrkostecki.smarttravelpoznan.domain.executor.PostExecutionThread;
import com.piotrkostecki.smarttravelpoznan.domain.executor.ThreadExecutor;
import com.piotrkostecki.smarttravelpoznan.domain.repository.PekaRepository;

import javax.inject.Inject;

import rx.Observable;

public class GetSearches extends UseCase {
    private PekaRepository pekaRepository;

    @Inject
    public GetSearches(PekaRepository pekaRepository,
                    ThreadExecutor threadExecutor,
                    PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.pekaRepository = pekaRepository;
    }

    @Override public Observable buildUseCaseObservable(Object... params) {
        return this.pekaRepository.searches();
    }
}
