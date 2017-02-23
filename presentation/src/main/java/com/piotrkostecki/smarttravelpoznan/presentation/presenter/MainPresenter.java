package com.piotrkostecki.smarttravelpoznan.presentation.presenter;

import android.support.annotation.NonNull;

import com.piotrkostecki.smarttravelpoznan.domain.exception.DefaultErrorBundle;
import com.piotrkostecki.smarttravelpoznan.domain.exception.ErrorBundle;
import com.piotrkostecki.smarttravelpoznan.domain.interactor.UseCase;
import com.piotrkostecki.smarttravelpoznan.domain.model.Bollard;
import com.piotrkostecki.smarttravelpoznan.domain.model.Stop;
import com.piotrkostecki.smarttravelpoznan.presentation.exception.ErrorMessageFactory;
import com.piotrkostecki.smarttravelpoznan.presentation.mapper.BollardModelDataMapper;
import com.piotrkostecki.smarttravelpoznan.presentation.mapper.StopModelDataMapper;
import com.piotrkostecki.smarttravelpoznan.presentation.model.BollardModel;
import com.piotrkostecki.smarttravelpoznan.presentation.model.StopModel;
import com.piotrkostecki.smarttravelpoznan.presentation.view.interfaces.MainView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

public class MainPresenter implements Presenter {

    private MainView viewMainView;

    private List<Stop> recentStops;
    private final UseCase getStopNameListUseCase;
    private final UseCase getSearchListUseCase;
    private final UseCase getBollardListUseCase;
    private final StopModelDataMapper stopModelDataMapper;
    private final BollardModelDataMapper bollardModelDataMapper;

    @Inject
    public MainPresenter(@Named("stops") UseCase getStopNameListUseCase,
                         @Named("bollards") UseCase getBollardListUseCase,
                         @Named("searches") UseCase getSearchListUseCase,
                         StopModelDataMapper stopModelDataMapper,
                         BollardModelDataMapper bollardModelDataMapper) {
        this.getStopNameListUseCase = getStopNameListUseCase;
        this.getSearchListUseCase = getSearchListUseCase;
        this.getBollardListUseCase = getBollardListUseCase;
        this.stopModelDataMapper = stopModelDataMapper;
        this.bollardModelDataMapper = bollardModelDataMapper;
    }

    public void setView(@NonNull MainView view) {
        this.viewMainView = view;
    }

    @Override
    public void resume() {
        this.getSearchListUseCase.execute(new SearchSubscriber());
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getStopNameListUseCase.unsubscribe();
        this.getBollardListUseCase.unsubscribe();
        this.getSearchListUseCase.unsubscribe();
        this.viewMainView = null;
    }

    public void initialize() {
        this.getSearchListUseCase.execute(new SearchSubscriber());
    }

    public void clearText() {
        this.viewMainView.eraseText();
    }

    public void scrollLayout() {
        this.viewMainView.scrollLayout();
    }

    public void centerLayout() {
        this.viewMainView.centerLayout();
    }

    public void layoutChanged() {
        this.viewMainView.handleLayoutChange();
    }

    public void onBollardClicked(BollardModel bollardModel) {
        this.viewMainView.navigateToTimetable(bollardModel);
    }

    public void onSearchClick(final String stopName) {
        this.showViewLoading();
        this.getBollardListUseCase.execute(new BollardSubscriber(), verifyStopName(stopName));
    }

    public void onDirectionClick(StopModel stopModel) {
        this.viewMainView.fillTextWithStopName(stopModel.getName());
    }

    public void onStopNameChanged(final String stopName) {
        /** Classic approach **/
        if (stopName.length() >= 1) {
            this.viewMainView.showEraseButton();
            if (stopName.length() >= 3) {
                this.viewMainView.changePromptToHints();
                this.showDirectionLoading();
                this.getStopNameListUseCase.execute(new StopSubscriber(), stopName);
            }
        } else {
            this.viewMainView.hideEraseButton();
            this.viewMainView.changePromptToRecentSearches();
            this.showStopCollectionInView(recentStops);
        }

        /** Java 8 - Lambda Expression Approach, mom pls **/
//        Observable.just(stopName)
//                .filter(stopNameText -> stopNameText.length() >= 3)
//                .subscribe(approved -> this.getStopNameListUseCase.execute(new StopSubscriber(), stopName));

        /** Reactive, without Java 8 **/
//        Observable.just(stopName)
//                .filter(new Func1<String, Boolean>() {
//                    @Override
//                    public Boolean call(String s) {
//                        return (s.length() >= 3);
//                    }
//                })
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        getStopNameListUseCase.execute(new StopSubscriber(), stopName);
//                    }
//                });
    }

    private void showDirectionLoading() {
        this.viewMainView.showStopListLoading();
    }

    private void hideDirectionLoading() {
        this.viewMainView.hideStopListLoading();
    }

    private void showViewLoading() {
        this.viewMainView.showLoading();
    }

    private void hideViewLoading() {
        this.viewMainView.hideLoading();
    }

    private String verifyStopName(String stopName) {
        for (int i = stopName.length() - 1; i >= 0; --i) {
            if (stopName.charAt(i) == ' ') {
                continue;
            } else {
                stopName = stopName.substring(0, (i + 1));
                break;
            }
        }
        return stopName;
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.viewMainView.context(), errorBundle.getException());
        this.viewMainView.showError(errorMessage);
    }

    private void showStopCollectionInView(Collection<Stop> stopCollection) {
        final Collection<StopModel> stopModelCollection = this.stopModelDataMapper.transform(stopCollection);
        this.viewMainView.renderStopList(stopModelCollection);
    }

    private void showBollardCollectionInView(Collection<Bollard> bollardCollection) {
        final Collection<BollardModel> bollardModelCollection = this.bollardModelDataMapper.transform(bollardCollection);
        this.viewMainView.renderBollardList(bollardModelCollection);
    }

    private final class SearchSubscriber extends rx.Subscriber<List<Stop>> {

        @Override public void onCompleted() {
            MainPresenter.this.hideDirectionLoading();
        }

        @Override public void onError(Throwable e) {
            MainPresenter.this.hideDirectionLoading();
            MainPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
        }

        @Override public void onNext(List<Stop> bollards) {
            recentStops = bollards;
            MainPresenter.this.showStopCollectionInView(recentStops);
        }
    }

    private final class StopSubscriber extends rx.Subscriber<List<Stop>> {

        @Override public void onCompleted() {
            MainPresenter.this.hideDirectionLoading();
        }

        @Override public void onError(Throwable e) {
            MainPresenter.this.hideDirectionLoading();
            MainPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
        }

        @Override public void onNext(List<Stop> stops) {
            MainPresenter.this.showStopCollectionInView(stops);
        }
    }

    private final class BollardSubscriber extends rx.Subscriber<List<Bollard>> {

        @Override public void onCompleted() { MainPresenter.this.hideViewLoading(); }

        @Override public void onError(Throwable e) {
            MainPresenter.this.hideViewLoading();
            MainPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
        }

        @Override public void onNext(List<Bollard> bollards) {
            MainPresenter.this.showBollardCollectionInView(bollards);
        }
    }
}
