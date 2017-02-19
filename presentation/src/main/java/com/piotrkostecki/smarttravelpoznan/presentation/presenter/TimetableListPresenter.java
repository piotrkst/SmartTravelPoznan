package com.piotrkostecki.smarttravelpoznan.presentation.presenter;

import android.support.annotation.NonNull;

import com.piotrkostecki.smarttravelpoznan.domain.exception.DefaultErrorBundle;
import com.piotrkostecki.smarttravelpoznan.domain.exception.ErrorBundle;
import com.piotrkostecki.smarttravelpoznan.domain.interactor.DefaultSubscriber;
import com.piotrkostecki.smarttravelpoznan.domain.interactor.UseCase;
import com.piotrkostecki.smarttravelpoznan.domain.model.Timetable;
import com.piotrkostecki.smarttravelpoznan.presentation.exception.ErrorMessageFactory;
import com.piotrkostecki.smarttravelpoznan.presentation.internal.di.PerActivity;
import com.piotrkostecki.smarttravelpoznan.presentation.mapper.TimetableModelDataMapper;
import com.piotrkostecki.smarttravelpoznan.presentation.model.TimetableModel;
import com.piotrkostecki.smarttravelpoznan.presentation.view.interfaces.TimetableListView;

import javax.inject.Inject;
import javax.inject.Named;

@PerActivity
public class TimetableListPresenter implements Presenter {

    private TimetableListView viewListView;

    private final UseCase getTimetableListUseCase;
    private final TimetableModelDataMapper timetableModelDataMapper;

    @Inject public TimetableListPresenter(@Named("timetables") UseCase getTimetableListUseCase,
                                          TimetableModelDataMapper timetableModelDataMapper) {
        this.getTimetableListUseCase = getTimetableListUseCase;
        this.timetableModelDataMapper = timetableModelDataMapper;
    }

    public void setView(@NonNull TimetableListView view) {
        this.viewListView = view;
    }

    @Override public void resume() {}

    @Override public void pause() {}

    @Override public void destroy() {
        this.getTimetableListUseCase.unsubscribe();
        this.viewListView = null;
    }

    public void onBackButtonClicked() {
        this.viewListView.navigateBack();
    }

    public void onRefreshButtonClicked() {
        this.initialize();
    }

    public void initialize() {
        this.loadTimetableList(getBollardSymbol());
    }

    private String getBollardSymbol() {
        return this.viewListView.getBollardSymbol();
    }

    private void loadTimetableList(String bollardSymbol) {
        this.hideViewRetry();
        this.showViewLoading();
        this.getTimetableList(bollardSymbol);
    }

    private void showViewLoading() {
        this.viewListView.showLoading();
    }

    private void hideViewLoading() {
        this.viewListView.hideLoading();
    }

    private void showViewRetry() {
        this.viewListView.showRetry();
    }

    private void hideViewRetry() {
        this.viewListView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.viewListView.context(), errorBundle.getException());
        this.viewListView.showError(errorMessage);
    }

    private void showTimetableInView(Timetable timetable) {
        final TimetableModel timetableModel =
                this.timetableModelDataMapper.transform(timetable);
        this.viewListView.renderTimetableList(timetableModel);
    }

    private void getTimetableList(String bollardSymbol) {
        this.getTimetableListUseCase.execute(new TimetableListSubscriber(), bollardSymbol);
    }

    private final class TimetableListSubscriber extends DefaultSubscriber<Timetable> {

        @Override public void onCompleted() { TimetableListPresenter.this.hideViewLoading(); }

        @Override public void onError(Throwable e) {
            TimetableListPresenter.this.hideViewLoading();
            TimetableListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            TimetableListPresenter.this.showViewRetry();
        }

        @Override public void onNext(Timetable bollards) {
            TimetableListPresenter.this.showTimetableInView(bollards);
        }
    }
}
