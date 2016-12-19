package com.piotrkostecki.smarttravelpoznan.presentation.presenter;

import android.support.annotation.NonNull;

import com.piotrkostecki.smarttravelpoznan.domain.exception.DefaultErrorBundle;
import com.piotrkostecki.smarttravelpoznan.domain.exception.ErrorBundle;
import com.piotrkostecki.smarttravelpoznan.domain.interactor.DefaultSubscriber;
import com.piotrkostecki.smarttravelpoznan.domain.interactor.UseCase;
import com.piotrkostecki.smarttravelpoznan.domain.model.Direction;
import com.piotrkostecki.smarttravelpoznan.presentation.exception.ErrorMessageFactory;
import com.piotrkostecki.smarttravelpoznan.presentation.mapper.DirectionModelDataMapper;
import com.piotrkostecki.smarttravelpoznan.presentation.model.DirectionModel;
import com.piotrkostecki.smarttravelpoznan.presentation.view.interfaces.MainView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 */
public class MainPresenter implements Presenter {

    private MainView viewMainView;

    private final UseCase getMainUseCase;
    private final DirectionModelDataMapper directionModelDataMapper;

    @Inject
    public MainPresenter(@Named("directions") UseCase getMainUseCase,
                                  DirectionModelDataMapper directionModelDataMapper) {
        this.getMainUseCase = getMainUseCase;
        this.directionModelDataMapper = directionModelDataMapper;
    }

    public void setView(@NonNull MainView view) {
        this.viewMainView = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getMainUseCase.unsubscribe();
        this.viewMainView = null;
    }

    public void onDirectionClick(DirectionModel directionModel) {
        this.viewMainView.viewTimetable();
    }

    public void getDirectionList() {
        this.showViewLoading();
        this.getMainUseCase.execute(new DirectionSubscriber());
    }

    private void showViewLoading() {
        this.viewMainView.showLoading();
    }

    private void hideViewLoading() {
        this.viewMainView.hideLoading();
    }

    private void showViewRetry() {
        this.viewMainView.showRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.viewMainView.context(), errorBundle.getException());
        this.viewMainView.showError(errorMessage);
    }

    private void showDirectionCollectionInView(Collection<Direction> directionCollection) {
        final Collection<DirectionModel> directionModelCollection = this.directionModelDataMapper.transform(directionCollection);
        this.viewMainView.renderDirectionList(directionModelCollection);
    }


    private final class DirectionSubscriber extends DefaultSubscriber<List<Direction>> {

        @Override public void onCompleted() {
            MainPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            MainPresenter.this.hideViewLoading();
            MainPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            MainPresenter.this.showViewRetry();
        }

        @Override public void onNext(List<Direction> directions) {
            MainPresenter.this.showDirectionCollectionInView(directions);
        }
    }
}
