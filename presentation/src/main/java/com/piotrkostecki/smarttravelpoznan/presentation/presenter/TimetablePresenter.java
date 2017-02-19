package com.piotrkostecki.smarttravelpoznan.presentation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.piotrkostecki.smarttravelpoznan.domain.exception.DefaultErrorBundle;
import com.piotrkostecki.smarttravelpoznan.domain.exception.ErrorBundle;
import com.piotrkostecki.smarttravelpoznan.domain.interactor.UseCase;
import com.piotrkostecki.smarttravelpoznan.domain.model.Timetable;
import com.piotrkostecki.smarttravelpoznan.presentation.exception.ErrorMessageFactory;
import com.piotrkostecki.smarttravelpoznan.presentation.mapper.TimetableModelDataMapper;
import com.piotrkostecki.smarttravelpoznan.presentation.model.TimetableModel;
import com.piotrkostecki.smarttravelpoznan.presentation.view.interfaces.TimetableView;

import javax.inject.Inject;
import javax.inject.Named;

public class TimetablePresenter implements Presenter {

    private TimetableView viewTimetableView;

    private final UseCase getTimetableUseCase;
    private final TimetableModelDataMapper timetableModelDataMapper;

    @Inject public TimetablePresenter(@Named("timetable") UseCase getTimetableUseCase,
                                      TimetableModelDataMapper timetableModelDataMapper) {
        this.getTimetableUseCase = getTimetableUseCase;
        this.timetableModelDataMapper = timetableModelDataMapper;
    }

    public void setView(@NonNull TimetableView view) {
        this.viewTimetableView = view;
    }

    @Override public void resume() {}

    @Override public void pause() {}

    @Override public void destroy() {
        this.getTimetableUseCase.unsubscribe();
        this.viewTimetableView = null;
    }

    public void onBackButtonClicked() {
        this.viewTimetableView.navigateBack();
    }

    public void onRefreshButtonClicked() {
        this.initialize();
    }

    public void initialize() {
        this.loadTimetable(getBollardSymbol());
    }

    private String getBollardSymbol() {
        return this.viewTimetableView.getBollardSymbol();
    }

    private void loadTimetable(String bollardSymbol) {
        this.hideViewRetry();
        this.showViewLoading();
        this.getTimetable(bollardSymbol);
    }

    private void showViewLoading() {
        this.viewTimetableView.showLoading();
    }

    private void hideViewLoading() {
        this.viewTimetableView.hideLoading();
    }

    private void showViewRetry() {
        this.viewTimetableView.showRetry();
    }

    private void hideViewRetry() {
        this.viewTimetableView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.viewTimetableView.context(), errorBundle.getException());
        this.viewTimetableView.showError(errorMessage);
    }

    private void showTimetableInView(Timetable timetable) {
        final TimetableModel timetableModel =
                this.timetableModelDataMapper.transform(timetable);
        this.viewTimetableView.renderTimetableList(timetableModel);
    }

    private void getTimetable(String bollardSymbol) {
        this.getTimetableUseCase.execute(new TimetableSubscriber(), bollardSymbol);
    }

    private final class TimetableSubscriber extends rx.Subscriber<Timetable> {

        @Override public void onCompleted() { TimetablePresenter.this.hideViewLoading(); }

        @Override public void onError(Throwable e) {
            TimetablePresenter.this.hideViewLoading();
            TimetablePresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            TimetablePresenter.this.showViewRetry();
        }

        @Override public void onNext(Timetable bollards) {
            TimetablePresenter.this.showTimetableInView(bollards);
        }
    }
}
