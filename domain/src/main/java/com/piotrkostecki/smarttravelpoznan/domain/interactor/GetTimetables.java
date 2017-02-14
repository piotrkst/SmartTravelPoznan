package com.piotrkostecki.smarttravelpoznan.domain.interactor;

import com.piotrkostecki.smarttravelpoznan.domain.executor.PostExecutionThread;
import com.piotrkostecki.smarttravelpoznan.domain.executor.ThreadExecutor;
import com.piotrkostecki.smarttravelpoznan.domain.repository.PekaRepository;

import rx.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a collection of all {@link com.piotrkostecki.smarttravelpoznan.domain.model.Timetable}.
 */
public class GetTimetables extends UseCase {

    private PekaRepository pekaRepository;

    public GetTimetables(ThreadExecutor threadExecutor,
                         PostExecutionThread postExecutionThread,
                         PekaRepository pekaRepository) {
        super(threadExecutor, postExecutionThread);
        this.pekaRepository = pekaRepository;
    }

    @Override protected Observable buildUseCaseObservable(Object... params) {
        return this.pekaRepository.timetables((String) params[0]);
    }
}
