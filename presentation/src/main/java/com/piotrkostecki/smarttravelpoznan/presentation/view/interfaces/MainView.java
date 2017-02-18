package com.piotrkostecki.smarttravelpoznan.presentation.view.interfaces;

import com.piotrkostecki.smarttravelpoznan.presentation.model.BollardModel;
import com.piotrkostecki.smarttravelpoznan.presentation.model.StopModel;

import java.util.Collection;

public interface MainView extends LoadDataView {

    void showStops();

    void hideStops();

    void showStopListLoading();

    void hideStopListLoading();

    void renderStopList(Collection<StopModel> directionCollection);

    void renderBollardList(Collection<BollardModel> bollardCollection);

    void fillEditTextWithStopName(String direction);

    void changePromptToHints();

    void changePromptToRecentSearches();

    void navigateToTimetable(BollardModel bollardModel);
}
