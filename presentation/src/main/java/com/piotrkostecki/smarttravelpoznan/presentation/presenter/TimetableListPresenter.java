package com.piotrkostecki.smarttravelpoznan.presentation.presenter;

import android.support.annotation.NonNull;

import com.piotrkostecki.smarttravelpoznan.domain.exception.ErrorBundle;
import com.piotrkostecki.smarttravelpoznan.domain.interactor.DefaultSubscriber;
import com.piotrkostecki.smarttravelpoznan.domain.interactor.UseCase;
import com.piotrkostecki.smarttravelpoznan.domain.model.Direction;
import com.piotrkostecki.smarttravelpoznan.presentation.exception.ErrorMessageFactory;
import com.piotrkostecki.smarttravelpoznan.presentation.internal.di.PerActivity;
import com.piotrkostecki.smarttravelpoznan.presentation.mapper.DirectionModelDataMapper;
import com.piotrkostecki.smarttravelpoznan.presentation.model.DirectionModel;
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
    private final DirectionModelDataMapper directionModelDataMapper;

    @Inject public TimetableListPresenter(@Named("timetables") UseCase getTimetableListUseCase,
                                          DirectionModelDataMapper directionModelDataMapper) {
        this.getTimetableListUseCase = getTimetableListUseCase;
        this.directionModelDataMapper = directionModelDataMapper;
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
        this.loadTimetableList();
    }

    /**
     * Loads all users.
     */
    private void loadTimetableList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getTimetableList();
    }

    public void onTimetableClicked(DirectionModel directionModel) {
        this.viewListView.viewTimetable(directionModel);
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

    private void showTimetableCollectionInView(Collection<Direction> directionCollection) {
        final Collection<DirectionModel> directionModelCollection =
                this.directionModelDataMapper.transform(directionCollection);
        this.viewListView.renderTimetableList(directionModelCollection);
    }

    private void getTimetableList() {
        this.getTimetableListUseCase.execute(new TimetableListSubscriber());
    }

    private final class TimetableListSubscriber extends DefaultSubscriber<List<Direction>> {

    }
}
