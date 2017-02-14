package com.piotrkostecki.smarttravelpoznan.presentation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.piotrkostecki.smarttravelpoznan.domain.exception.DefaultErrorBundle;
import com.piotrkostecki.smarttravelpoznan.domain.exception.ErrorBundle;
import com.piotrkostecki.smarttravelpoznan.domain.interactor.DefaultSubscriber;
import com.piotrkostecki.smarttravelpoznan.domain.interactor.UseCase;
import com.piotrkostecki.smarttravelpoznan.domain.model.Stop;
import com.piotrkostecki.smarttravelpoznan.domain.model.Timetable;
import com.piotrkostecki.smarttravelpoznan.presentation.exception.ErrorMessageFactory;
import com.piotrkostecki.smarttravelpoznan.presentation.internal.di.PerActivity;
import com.piotrkostecki.smarttravelpoznan.presentation.mapper.StopModelDataMapper;
import com.piotrkostecki.smarttravelpoznan.presentation.mapper.TimetableModelDataMapper;
import com.piotrkostecki.smarttravelpoznan.presentation.model.StopModel;
import com.piotrkostecki.smarttravelpoznan.presentation.model.TimetableModel;
import com.piotrkostecki.smarttravelpoznan.presentation.view.interfaces.TimetableListView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
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

    /**
     * Initializes the presenter by start retrieving the user list.
     */
    public void initialize() {
        Log.i("test", "initialize: " + getBollardSymbol());
        this.loadTimetableList(getBollardSymbol());
    }

    private String getBollardSymbol() {
        return this.viewListView.getBollardSymbol();
    }

    /**
     * Loads all users.
     */
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
        String errorMessage = ErrorMessageFactory.create(this.viewListView.context(),
                errorBundle.getException());
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
